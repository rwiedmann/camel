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
package org.apache.camel.component.jms;

import java.util.Set;

import javax.management.MBeanServer;
import javax.management.ObjectName;

import org.apache.camel.builder.RouteBuilder;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 *
 */
public class ManagedJmsEndpointTopicTest extends AbstractJMSTest {

    @Override
    protected boolean useJmx() {
        return true;
    }

    @Override
    protected String getComponentName() {
        return "activemq";
    }

    protected MBeanServer getMBeanServer() {
        return context.getManagementStrategy().getManagementAgent().getMBeanServer();
    }

    @Test
    public void testJmsEndpoint() throws Exception {
        MBeanServer mbeanServer = getMBeanServer();

        Set<ObjectName> objectNames = mbeanServer.queryNames(
                new ObjectName("org.apache.camel:context=camel-*,type=endpoints,name=\"activemq://topic:start\""), null);
        assertEquals(1, objectNames.size());
        ObjectName name = objectNames.iterator().next();

        String uri = (String) mbeanServer.getAttribute(name, "EndpointUri");
        assertEquals("activemq://topic:start", uri);

        Boolean singleton = (Boolean) mbeanServer.getAttribute(name, "Singleton");
        assertTrue(singleton);

        Integer running = (Integer) mbeanServer.getAttribute(name, "RunningMessageListeners");
        assertEquals(2, running.intValue());

        getMockEndpoint("mock:result").expectedMessageCount(2);

        template.sendBody("activemq:topic:start", "Hello World");

        assertMockEndpointsSatisfied();
    }

    @Override
    protected RouteBuilder createRouteBuilder() {
        return new RouteBuilder() {
            @Override
            public void configure() {
                from("activemq:topic:start").routeId("foo").to("log:foo").to("mock:result");

                from("activemq:topic:start").routeId("bar").to("log:bar").to("mock:result");
            }
        };
    }

}
