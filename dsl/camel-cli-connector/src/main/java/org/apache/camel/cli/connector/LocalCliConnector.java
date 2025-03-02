/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.camel.cli.connector;

import java.io.File;
import java.io.FileInputStream;
import java.lang.management.ClassLoadingMXBean;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.RuntimeMXBean;
import java.lang.management.ThreadMXBean;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

import org.apache.camel.CamelContext;
import org.apache.camel.CamelContextAware;
import org.apache.camel.Exchange;
import org.apache.camel.ExtendedCamelContext;
import org.apache.camel.Route;
import org.apache.camel.api.management.ManagedCamelContext;
import org.apache.camel.console.DevConsole;
import org.apache.camel.spi.CliConnector;
import org.apache.camel.spi.CliConnectorFactory;
import org.apache.camel.spi.ContextReloadStrategy;
import org.apache.camel.support.DefaultContextReloadStrategy;
import org.apache.camel.support.PatternHelper;
import org.apache.camel.support.service.ServiceHelper;
import org.apache.camel.support.service.ServiceSupport;
import org.apache.camel.util.FileUtil;
import org.apache.camel.util.IOHelper;
import org.apache.camel.util.concurrent.ThreadHelper;
import org.apache.camel.util.json.JsonObject;
import org.apache.camel.util.json.Jsoner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * CLI Connector for local management of Camel integrations from the Camel CLI.
 */
public class LocalCliConnector extends ServiceSupport implements CliConnector, CamelContextAware {

    private static final Logger LOG = LoggerFactory.getLogger(LocalCliConnector.class);

    private final CliConnectorFactory cliConnectorFactory;
    private CamelContext camelContext;
    private int delay = 2000;
    private String platform;
    private String platformVersion;
    private String mainClass;
    private final AtomicBoolean terminating = new AtomicBoolean();
    private ScheduledExecutorService executor;
    private volatile ExecutorService terminateExecutor;
    private File lockFile;
    private File statusFile;
    private File actionFile;
    private File outputFile;

    public LocalCliConnector(CliConnectorFactory cliConnectorFactory) {
        this.cliConnectorFactory = cliConnectorFactory;
    }

    @Override
    public CamelContext getCamelContext() {
        return camelContext;
    }

    @Override
    public void setCamelContext(CamelContext camelContext) {
        this.camelContext = camelContext;
    }

    @Override
    protected void doStart() throws Exception {
        terminating.set(false);

        // what platform are we running
        mainClass = cliConnectorFactory.getRuntimeStartClass();
        if (mainClass == null) {
            mainClass = camelContext.getGlobalOption("CamelMainClass");
        }
        platform = cliConnectorFactory.getRuntime();
        if (platform == null) {
            // use camel context name to guess platform if not specified
            String sn = camelContext.getClass().getSimpleName().toLowerCase(Locale.ROOT);
            if (sn.contains("boot")) {
                platform = "Spring Boot";
            } else if (sn.contains("spring")) {
                platform = "Spring";
            } else if (sn.contains("quarkus")) {
                platform = "Quarkus";
            } else if (sn.contains("osgi")) {
                platform = "Karaf";
            } else if (sn.contains("cdi")) {
                platform = "CDI";
            } else if (camelContext.getName().equals("CamelJBang")) {
                platform = "JBang";
            } else {
                platform = "Camel";
            }
        }
        platformVersion = cliConnectorFactory.getRuntimeVersion();

        // create thread from JDK so it is not managed by Camel because we want the pool to be independent when
        // camel is being stopped which otherwise can lead to stopping the thread pool while the task is running
        executor = Executors.newSingleThreadScheduledExecutor(r -> {
            String threadName = ThreadHelper.resolveThreadName(null, "LocalCliConnector");
            return new Thread(r, threadName);
        });

        lockFile = createLockFile(getPid());
        if (lockFile != null) {
            statusFile = createLockFile(lockFile.getName() + "-status.json");
            actionFile = createLockFile(lockFile.getName() + "-action.json");
            outputFile = createLockFile(lockFile.getName() + "-output.json");
            executor.scheduleWithFixedDelay(this::task, 0, delay, TimeUnit.MILLISECONDS);
            LOG.info("Camel CLI enabled (local)");
        } else {
            LOG.warn("Cannot create PID file: {}. This integration cannot be managed by Camel CLI.", getPid());
        }
    }

    @Override
    public void sigterm() {
        // we are terminating
        terminating.set(true);

        // spawn a thread that terminates, so we can keep this thread to update status
        terminateExecutor = Executors.newSingleThreadExecutor(r -> {
            String threadName = ThreadHelper.resolveThreadName(null, "Terminate JVM task");
            return new Thread(r, threadName);
        });
        terminateExecutor.submit(new Runnable() {
            @Override
            public void run() {
                LOG.info("Camel CLI terminating JVM");
                try {
                    camelContext.stop();
                } finally {
                    ServiceHelper.stopAndShutdownService(this);
                }
            }
        });
    }

    protected void task() {
        if (!lockFile.exists() && terminating.compareAndSet(false, true)) {
            // if the lock file is deleted then trigger termination
            sigterm();
            return;
        }
        if (!statusFile.exists()) {
            return;
        }

        actionTask();
        statusTask();
    }

    protected void actionTask() {
        try {
            JsonObject root = loadAction();
            if (root == null || root.isEmpty()) {
                return;
            }

            if (LOG.isDebugEnabled()) {
                LOG.debug("Action: {}", root);
            }

            String action = root.getString("action");
            if ("route".equals(action)) {
                // id is a pattern
                String[] patterns = root.getString("id").split(",");
                // find matching IDs
                List<String> ids = camelContext.getRoutes()
                        .stream().map(Route::getRouteId)
                        .filter(routeId -> {
                            for (String p : patterns) {
                                if (PatternHelper.matchPattern(routeId, p)) {
                                    return true;
                                }
                            }
                            return false;
                        })
                        .collect(Collectors.toList());
                for (String id : ids) {
                    try {
                        String command = root.getString("command");
                        if ("start".equals(command)) {
                            if ("*".equals(id)) {
                                camelContext.getRouteController().startAllRoutes();
                            } else {
                                camelContext.getRouteController().startRoute(id);
                            }
                        } else if ("stop".equals(command)) {
                            if ("*".equals(id)) {
                                camelContext.getRouteController().stopAllRoutes();
                            } else {
                                camelContext.getRouteController().stopRoute(id);
                            }
                        } else if ("suspend".equals(command)) {
                            camelContext.getRouteController().suspendRoute(id);
                        } else if ("resume".equals(command)) {
                            camelContext.getRouteController().resumeRoute(id);
                        }
                    } catch (Exception e) {
                        // ignore
                    }
                }
            } else if ("gc".equals(action)) {
                System.gc();
            } else if ("reload".equals(action)) {
                ContextReloadStrategy reloader = camelContext.hasService(ContextReloadStrategy.class);
                if (reloader == null) {
                    reloader = new DefaultContextReloadStrategy();
                    camelContext.addService(reloader);
                }
                reloader.onReload("Camel CLI");
            } else if ("reset-stats".equals(action)) {
                ManagedCamelContext mcc = camelContext.getExtension(ManagedCamelContext.class);
                if (mcc != null) {
                    mcc.getManagedCamelContext().reset(true);
                }
            } else if ("thread-dump".equals(action)) {
                DevConsole dc = camelContext.adapt(ExtendedCamelContext.class)
                        .getDevConsoleResolver().resolveDevConsole("thread");
                if (dc != null) {
                    JsonObject json = (JsonObject) dc.call(DevConsole.MediaType.JSON, Map.of("stackTrace", "true"));
                    LOG.trace("Updating output file: {}", outputFile);
                    IOHelper.writeText(json.toJson(), outputFile);
                }
            } else if ("top-processors".equals(action)) {
                DevConsole dc = camelContext.adapt(ExtendedCamelContext.class)
                        .getDevConsoleResolver().resolveDevConsole("top");
                if (dc != null) {
                    JsonObject json = (JsonObject) dc.call(DevConsole.MediaType.JSON, Map.of(Exchange.HTTP_PATH, "/*"));
                    LOG.trace("Updating output file: {}", outputFile);
                    IOHelper.writeText(json.toJson(), outputFile);
                }
            }

            // action done so delete file
            FileUtil.deleteFile(actionFile);

        } catch (Throwable e) {
            // ignore
            LOG.debug(
                    "Error executing action file: " + actionFile + " due to: " + e.getMessage()
                      + ". This exception is ignored.",
                    e);
        }
    }

    JsonObject loadAction() {
        try {
            if (actionFile != null && actionFile.exists()) {
                FileInputStream fis = new FileInputStream(actionFile);
                String text = IOHelper.loadText(fis);
                IOHelper.close(fis);
                if (!text.isEmpty()) {
                    return (JsonObject) Jsoner.deserialize(text);
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
            // ignore
        }
        return null;
    }

    protected void statusTask() {
        try {
            // even during termination then collect status as we want to see status changes during stopping
            JsonObject root = new JsonObject();

            // what runtime are in use
            JsonObject rc = new JsonObject();
            String dir = new File(".").getAbsolutePath();
            dir = FileUtil.onlyPath(dir);
            rc.put("pid", ProcessHandle.current().pid());
            rc.put("directory", dir);
            ProcessHandle.current().info().user().ifPresent(u -> rc.put("user", u));
            rc.put("platform", platform);
            if (platformVersion != null) {
                rc.put("platformVersion", platformVersion);
            }
            if (mainClass != null) {
                rc.put("mainClass", mainClass);
            }
            RuntimeMXBean mb = ManagementFactory.getRuntimeMXBean();
            if (mb != null) {
                rc.put("javaVersion", mb.getVmVersion());
            }
            root.put("runtime", rc);

            // collect details via console
            DevConsole dc = camelContext.adapt(ExtendedCamelContext.class)
                    .getDevConsoleResolver().resolveDevConsole("context");
            DevConsole dc2 = camelContext.adapt(ExtendedCamelContext.class)
                    .getDevConsoleResolver().resolveDevConsole("route");
            if (dc != null && dc2 != null) {
                JsonObject json = (JsonObject) dc.call(DevConsole.MediaType.JSON);
                JsonObject json2 = (JsonObject) dc2.call(DevConsole.MediaType.JSON, Map.of("processors", "true"));
                if (json != null && json2 != null) {
                    root.put("context", json);
                    root.put("routes", json2.get("routes"));
                }
            }
            DevConsole dc3 = camelContext.adapt(ExtendedCamelContext.class)
                    .getDevConsoleResolver().resolveDevConsole("health");
            if (dc3 != null) {
                // include full details in health checks
                JsonObject json = (JsonObject) dc3.call(DevConsole.MediaType.JSON, Map.of("exposureLevel", "full"));
                root.put("healthChecks", json);
            }
            // various details
            JsonObject mem = collectMemory();
            if (mem != null) {
                root.put("memory", mem);
            }
            JsonObject cl = collectClassLoading();
            if (cl != null) {
                root.put("classLoading", cl);
            }
            JsonObject threads = collectThreads();
            if (threads != null) {
                root.put("threads", threads);
            }
            JsonObject gc = collectGC();
            if (gc != null) {
                root.put("gc", gc);
            }
            JsonObject vaults = collectVaults();
            if (!vaults.isEmpty()) {
                root.put("vaults", vaults);
            }
            LOG.trace("Updating status file: {}", statusFile);
            IOHelper.writeText(root.toJson(), statusFile);
        } catch (Throwable e) {
            // ignore
            LOG.trace(
                    "Error updating status file: " + statusFile + " due to: " + e.getMessage() + ". This exception is ignored.",
                    e);
        }
    }

    private JsonObject collectMemory() {
        MemoryMXBean mb = ManagementFactory.getMemoryMXBean();
        if (mb != null) {
            JsonObject root = new JsonObject();
            root.put("heapMemoryUsed", mb.getHeapMemoryUsage().getUsed());
            root.put("heapMemoryCommitted", mb.getHeapMemoryUsage().getCommitted());
            root.put("heapMemoryMax", mb.getHeapMemoryUsage().getMax());
            root.put("nonHeapMemoryUsed", mb.getNonHeapMemoryUsage().getUsed());
            root.put("nonHeapMemoryCommitted", mb.getNonHeapMemoryUsage().getCommitted());
            return root;
        }
        return null;
    }

    private JsonObject collectClassLoading() {
        ClassLoadingMXBean cb = ManagementFactory.getClassLoadingMXBean();
        if (cb != null) {
            JsonObject root = new JsonObject();
            root.put("loadedClassCount", cb.getLoadedClassCount());
            root.put("unloadedClassCount", cb.getUnloadedClassCount());
            root.put("totalLoadedClassCount", cb.getTotalLoadedClassCount());
            return root;
        }
        return null;
    }

    private JsonObject collectThreads() {
        ThreadMXBean tb = ManagementFactory.getThreadMXBean();
        if (tb != null) {
            JsonObject root = new JsonObject();
            root.put("threadCount", tb.getThreadCount());
            root.put("peakThreadCount", tb.getPeakThreadCount());
            return root;
        }
        return null;
    }

    private JsonObject collectGC() {
        List<GarbageCollectorMXBean> gcs = ManagementFactory.getGarbageCollectorMXBeans();
        if (gcs != null && !gcs.isEmpty()) {
            JsonObject root = new JsonObject();
            long count = 0;
            long time = 0;
            for (GarbageCollectorMXBean gc : gcs) {
                count += gc.getCollectionCount();
                time += gc.getCollectionTime();
            }
            root.put("collectionCount", count);
            root.put("collectionTime", time);
            return root;
        }
        return null;
    }

    private JsonObject collectVaults() {
        JsonObject root = new JsonObject();
        // aws-secrets is optional
        Optional<DevConsole> dcAws = camelContext.adapt(ExtendedCamelContext.class)
                .getDevConsoleResolver().lookupDevConsole("aws-secrets");
        if (dcAws.isPresent()) {
            JsonObject json = (JsonObject) dcAws.get().call(DevConsole.MediaType.JSON);
            if (json != null) {
                root.put("aws-secrets", json);
            }
        }
        // gcp-secrets is optional
        Optional<DevConsole> dcGcp = camelContext.adapt(ExtendedCamelContext.class)
                .getDevConsoleResolver().lookupDevConsole("gcp-secrets");
        if (dcGcp.isPresent()) {
            JsonObject json = (JsonObject) dcGcp.get().call(DevConsole.MediaType.JSON);
            if (json != null) {
                root.put("gcp-secrets", json);
            }
        }
        return root;
    }

    @Override
    protected void doStop() throws Exception {
        // cleanup
        if (lockFile != null) {
            FileUtil.deleteFile(lockFile);
        }
        if (statusFile != null) {
            FileUtil.deleteFile(statusFile);
        }
        if (actionFile != null) {
            FileUtil.deleteFile(actionFile);
        }
        if (outputFile != null) {
            FileUtil.deleteFile(outputFile);
        }
        if (executor != null) {
            camelContext.getExecutorServiceManager().shutdown(executor);
            executor = null;
        }
    }

    private static String getPid() {
        try {
            return "" + ProcessHandle.current().pid();
        } catch (Throwable e) {
            return null;
        }
    }

    private static File createLockFile(String name) {
        File answer = null;
        if (name != null) {
            File dir = new File(System.getProperty("user.home"), ".camel");
            try {
                dir.mkdirs();
                answer = new File(dir, name);
                if (!answer.exists()) {
                    answer.createNewFile();
                }
                answer.deleteOnExit();
            } catch (Exception e) {
                answer = null;
            }
        }
        return answer;
    }

}
