/* Generated by camel build tools - do NOT edit this file! */
package org.apache.camel.main;

import java.util.Map;

import org.apache.camel.CamelContext;
import org.apache.camel.spi.GeneratedPropertyConfigurer;
import org.apache.camel.spi.PropertyConfigurerGetter;
import org.apache.camel.util.CaseInsensitiveMap;
import org.apache.camel.main.Resilience4jConfigurationProperties;

/**
 * Generated by camel build tools - do NOT edit this file!
 */
@SuppressWarnings("unchecked")
public class Resilience4jConfigurationPropertiesConfigurer extends org.apache.camel.support.component.PropertyConfigurerSupport implements GeneratedPropertyConfigurer, PropertyConfigurerGetter {

    private static final Map<String, Object> ALL_OPTIONS;
    static {
        Map<String, Object> map = new CaseInsensitiveMap();
        map.put("AutomaticTransitionFromOpenToHalfOpenEnabled", java.lang.Boolean.class);
        map.put("BulkheadEnabled", java.lang.Boolean.class);
        map.put("BulkheadMaxConcurrentCalls", java.lang.Integer.class);
        map.put("BulkheadMaxWaitDuration", java.lang.Integer.class);
        map.put("CircuitBreakerRef", java.lang.String.class);
        map.put("ConfigRef", java.lang.String.class);
        map.put("FailureRateThreshold", java.lang.Float.class);
        map.put("MinimumNumberOfCalls", java.lang.Integer.class);
        map.put("PermittedNumberOfCallsInHalfOpenState", java.lang.Integer.class);
        map.put("SlidingWindowSize", java.lang.Integer.class);
        map.put("SlidingWindowType", java.lang.String.class);
        map.put("SlowCallDurationThreshold", java.lang.Integer.class);
        map.put("SlowCallRateThreshold", java.lang.Float.class);
        map.put("TimeoutCancelRunningFuture", java.lang.Boolean.class);
        map.put("TimeoutDuration", java.lang.Integer.class);
        map.put("TimeoutEnabled", java.lang.Boolean.class);
        map.put("TimeoutExecutorServiceRef", java.lang.String.class);
        map.put("WaitDurationInOpenState", java.lang.Integer.class);
        map.put("WritableStackTraceEnabled", java.lang.Boolean.class);
        ALL_OPTIONS = map;
    }

    @Override
    public boolean configure(CamelContext camelContext, Object obj, String name, Object value, boolean ignoreCase) {
        org.apache.camel.main.Resilience4jConfigurationProperties target = (org.apache.camel.main.Resilience4jConfigurationProperties) obj;
        switch (ignoreCase ? name.toLowerCase() : name) {
        case "automatictransitionfromopentohalfopenenabled":
        case "AutomaticTransitionFromOpenToHalfOpenEnabled": target.setAutomaticTransitionFromOpenToHalfOpenEnabled(property(camelContext, java.lang.Boolean.class, value)); return true;
        case "bulkheadenabled":
        case "BulkheadEnabled": target.setBulkheadEnabled(property(camelContext, java.lang.Boolean.class, value)); return true;
        case "bulkheadmaxconcurrentcalls":
        case "BulkheadMaxConcurrentCalls": target.setBulkheadMaxConcurrentCalls(property(camelContext, java.lang.Integer.class, value)); return true;
        case "bulkheadmaxwaitduration":
        case "BulkheadMaxWaitDuration": target.setBulkheadMaxWaitDuration(property(camelContext, java.lang.Integer.class, value)); return true;
        case "circuitbreakerref":
        case "CircuitBreakerRef": target.setCircuitBreakerRef(property(camelContext, java.lang.String.class, value)); return true;
        case "configref":
        case "ConfigRef": target.setConfigRef(property(camelContext, java.lang.String.class, value)); return true;
        case "failureratethreshold":
        case "FailureRateThreshold": target.setFailureRateThreshold(property(camelContext, java.lang.Float.class, value)); return true;
        case "minimumnumberofcalls":
        case "MinimumNumberOfCalls": target.setMinimumNumberOfCalls(property(camelContext, java.lang.Integer.class, value)); return true;
        case "permittednumberofcallsinhalfopenstate":
        case "PermittedNumberOfCallsInHalfOpenState": target.setPermittedNumberOfCallsInHalfOpenState(property(camelContext, java.lang.Integer.class, value)); return true;
        case "slidingwindowsize":
        case "SlidingWindowSize": target.setSlidingWindowSize(property(camelContext, java.lang.Integer.class, value)); return true;
        case "slidingwindowtype":
        case "SlidingWindowType": target.setSlidingWindowType(property(camelContext, java.lang.String.class, value)); return true;
        case "slowcalldurationthreshold":
        case "SlowCallDurationThreshold": target.setSlowCallDurationThreshold(property(camelContext, java.lang.Integer.class, value)); return true;
        case "slowcallratethreshold":
        case "SlowCallRateThreshold": target.setSlowCallRateThreshold(property(camelContext, java.lang.Float.class, value)); return true;
        case "timeoutcancelrunningfuture":
        case "TimeoutCancelRunningFuture": target.setTimeoutCancelRunningFuture(property(camelContext, java.lang.Boolean.class, value)); return true;
        case "timeoutduration":
        case "TimeoutDuration": target.setTimeoutDuration(property(camelContext, java.lang.Integer.class, value)); return true;
        case "timeoutenabled":
        case "TimeoutEnabled": target.setTimeoutEnabled(property(camelContext, java.lang.Boolean.class, value)); return true;
        case "timeoutexecutorserviceref":
        case "TimeoutExecutorServiceRef": target.setTimeoutExecutorServiceRef(property(camelContext, java.lang.String.class, value)); return true;
        case "waitdurationinopenstate":
        case "WaitDurationInOpenState": target.setWaitDurationInOpenState(property(camelContext, java.lang.Integer.class, value)); return true;
        case "writablestacktraceenabled":
        case "WritableStackTraceEnabled": target.setWritableStackTraceEnabled(property(camelContext, java.lang.Boolean.class, value)); return true;
        default: return false;
        }
    }

    @Override
    public Map<String, Object> getAllOptions(Object target) {
        return ALL_OPTIONS;
    }

    @Override
    public Object getOptionValue(Object obj, String name, boolean ignoreCase) {
        org.apache.camel.main.Resilience4jConfigurationProperties target = (org.apache.camel.main.Resilience4jConfigurationProperties) obj;
        switch (ignoreCase ? name.toLowerCase() : name) {
        case "automatictransitionfromopentohalfopenenabled":
        case "AutomaticTransitionFromOpenToHalfOpenEnabled": return target.getAutomaticTransitionFromOpenToHalfOpenEnabled();
        case "bulkheadenabled":
        case "BulkheadEnabled": return target.getBulkheadEnabled();
        case "bulkheadmaxconcurrentcalls":
        case "BulkheadMaxConcurrentCalls": return target.getBulkheadMaxConcurrentCalls();
        case "bulkheadmaxwaitduration":
        case "BulkheadMaxWaitDuration": return target.getBulkheadMaxWaitDuration();
        case "circuitbreakerref":
        case "CircuitBreakerRef": return target.getCircuitBreakerRef();
        case "configref":
        case "ConfigRef": return target.getConfigRef();
        case "failureratethreshold":
        case "FailureRateThreshold": return target.getFailureRateThreshold();
        case "minimumnumberofcalls":
        case "MinimumNumberOfCalls": return target.getMinimumNumberOfCalls();
        case "permittednumberofcallsinhalfopenstate":
        case "PermittedNumberOfCallsInHalfOpenState": return target.getPermittedNumberOfCallsInHalfOpenState();
        case "slidingwindowsize":
        case "SlidingWindowSize": return target.getSlidingWindowSize();
        case "slidingwindowtype":
        case "SlidingWindowType": return target.getSlidingWindowType();
        case "slowcalldurationthreshold":
        case "SlowCallDurationThreshold": return target.getSlowCallDurationThreshold();
        case "slowcallratethreshold":
        case "SlowCallRateThreshold": return target.getSlowCallRateThreshold();
        case "timeoutcancelrunningfuture":
        case "TimeoutCancelRunningFuture": return target.getTimeoutCancelRunningFuture();
        case "timeoutduration":
        case "TimeoutDuration": return target.getTimeoutDuration();
        case "timeoutenabled":
        case "TimeoutEnabled": return target.getTimeoutEnabled();
        case "timeoutexecutorserviceref":
        case "TimeoutExecutorServiceRef": return target.getTimeoutExecutorServiceRef();
        case "waitdurationinopenstate":
        case "WaitDurationInOpenState": return target.getWaitDurationInOpenState();
        case "writablestacktraceenabled":
        case "WritableStackTraceEnabled": return target.getWritableStackTraceEnabled();
        default: return null;
        }
    }
}

