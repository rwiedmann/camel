/* Generated by camel build tools - do NOT edit this file! */
package org.apache.camel.component.cxf.jaxws;

import java.util.Map;

import org.apache.camel.CamelContext;
import org.apache.camel.spi.ExtendedPropertyConfigurerGetter;
import org.apache.camel.spi.PropertyConfigurerGetter;
import org.apache.camel.spi.ConfigurerStrategy;
import org.apache.camel.spi.GeneratedPropertyConfigurer;
import org.apache.camel.util.CaseInsensitiveMap;
import org.apache.camel.support.component.PropertyConfigurerSupport;

/**
 * Generated by camel build tools - do NOT edit this file!
 */
@SuppressWarnings("unchecked")
public class CxfEndpointConfigurer extends PropertyConfigurerSupport implements GeneratedPropertyConfigurer, PropertyConfigurerGetter {

    @Override
    public boolean configure(CamelContext camelContext, Object obj, String name, Object value, boolean ignoreCase) {
        CxfEndpoint target = (CxfEndpoint) obj;
        switch (ignoreCase ? name.toLowerCase() : name) {
        case "allowstreaming":
        case "allowStreaming": target.setAllowStreaming(property(camelContext, java.lang.Boolean.class, value)); return true;
        case "bindingid":
        case "bindingId": target.setBindingId(property(camelContext, java.lang.String.class, value)); return true;
        case "bridgeerrorhandler":
        case "bridgeErrorHandler": target.setBridgeErrorHandler(property(camelContext, boolean.class, value)); return true;
        case "bus": target.setBus(property(camelContext, org.apache.cxf.Bus.class, value)); return true;
        case "continuationtimeout":
        case "continuationTimeout": target.setContinuationTimeout(property(camelContext, java.time.Duration.class, value).toMillis()); return true;
        case "cookiehandler":
        case "cookieHandler": target.setCookieHandler(property(camelContext, org.apache.camel.http.base.cookie.CookieHandler.class, value)); return true;
        case "cxfbinding":
        case "cxfBinding": target.setCxfBinding(property(camelContext, org.apache.camel.component.cxf.common.CxfBinding.class, value)); return true;
        case "cxfconfigurer":
        case "cxfConfigurer": target.setCxfConfigurer(property(camelContext, org.apache.camel.component.cxf.jaxws.CxfConfigurer.class, value)); return true;
        case "dataformat":
        case "dataFormat": target.setDataFormat(property(camelContext, org.apache.camel.component.cxf.common.DataFormat.class, value)); return true;
        case "defaultbus":
        case "defaultBus": target.setDefaultBus(property(camelContext, boolean.class, value)); return true;
        case "defaultoperationname":
        case "defaultOperationName": target.setDefaultOperationName(property(camelContext, java.lang.String.class, value)); return true;
        case "defaultoperationnamespace":
        case "defaultOperationNamespace": target.setDefaultOperationNamespace(property(camelContext, java.lang.String.class, value)); return true;
        case "exceptionhandler":
        case "exceptionHandler": target.setExceptionHandler(property(camelContext, org.apache.camel.spi.ExceptionHandler.class, value)); return true;
        case "exchangepattern":
        case "exchangePattern": target.setExchangePattern(property(camelContext, org.apache.camel.ExchangePattern.class, value)); return true;
        case "headerfilterstrategy":
        case "headerFilterStrategy": target.setHeaderFilterStrategy(property(camelContext, org.apache.camel.spi.HeaderFilterStrategy.class, value)); return true;
        case "hostnameverifier":
        case "hostnameVerifier": target.setHostnameVerifier(property(camelContext, javax.net.ssl.HostnameVerifier.class, value)); return true;
        case "lazystartproducer":
        case "lazyStartProducer": target.setLazyStartProducer(property(camelContext, boolean.class, value)); return true;
        case "loggingfeatureenabled":
        case "loggingFeatureEnabled": target.setLoggingFeatureEnabled(property(camelContext, boolean.class, value)); return true;
        case "loggingsizelimit":
        case "loggingSizeLimit": target.setLoggingSizeLimit(property(camelContext, int.class, value)); return true;
        case "mergeprotocolheaders":
        case "mergeProtocolHeaders": target.setMergeProtocolHeaders(property(camelContext, boolean.class, value)); return true;
        case "mtomenabled":
        case "mtomEnabled": target.setMtomEnabled(property(camelContext, boolean.class, value)); return true;
        case "password": target.setPassword(property(camelContext, java.lang.String.class, value)); return true;
        case "portname":
        case "portName": target.setPortName(property(camelContext, java.lang.String.class, value)); return true;
        case "properties": target.setProperties(property(camelContext, java.util.Map.class, value)); return true;
        case "publishedendpointurl":
        case "publishedEndpointUrl": target.setPublishedEndpointUrl(property(camelContext, java.lang.String.class, value)); return true;
        case "serviceclass":
        case "serviceClass": target.setServiceClass(property(camelContext, java.lang.Class.class, value)); return true;
        case "servicename":
        case "serviceName": target.setServiceName(property(camelContext, java.lang.String.class, value)); return true;
        case "skipfaultlogging":
        case "skipFaultLogging": target.setSkipFaultLogging(property(camelContext, boolean.class, value)); return true;
        case "skippayloadmessagepartcheck":
        case "skipPayloadMessagePartCheck": target.setSkipPayloadMessagePartCheck(property(camelContext, boolean.class, value)); return true;
        case "sslcontextparameters":
        case "sslContextParameters": target.setSslContextParameters(property(camelContext, org.apache.camel.support.jsse.SSLContextParameters.class, value)); return true;
        case "synchronous": target.setSynchronous(property(camelContext, boolean.class, value)); return true;
        case "username": target.setUsername(property(camelContext, java.lang.String.class, value)); return true;
        case "wrapped": target.setWrapped(property(camelContext, boolean.class, value)); return true;
        case "wrappedstyle":
        case "wrappedStyle": target.setWrappedStyle(property(camelContext, java.lang.Boolean.class, value)); return true;
        case "wsdlurl":
        case "wsdlURL": target.setWsdlURL(property(camelContext, java.lang.String.class, value)); return true;
        default: return false;
        }
    }

    @Override
    public Class<?> getOptionType(String name, boolean ignoreCase) {
        switch (ignoreCase ? name.toLowerCase() : name) {
        case "allowstreaming":
        case "allowStreaming": return java.lang.Boolean.class;
        case "bindingid":
        case "bindingId": return java.lang.String.class;
        case "bridgeerrorhandler":
        case "bridgeErrorHandler": return boolean.class;
        case "bus": return org.apache.cxf.Bus.class;
        case "continuationtimeout":
        case "continuationTimeout": return long.class;
        case "cookiehandler":
        case "cookieHandler": return org.apache.camel.http.base.cookie.CookieHandler.class;
        case "cxfbinding":
        case "cxfBinding": return org.apache.camel.component.cxf.common.CxfBinding.class;
        case "cxfconfigurer":
        case "cxfConfigurer": return org.apache.camel.component.cxf.jaxws.CxfConfigurer.class;
        case "dataformat":
        case "dataFormat": return org.apache.camel.component.cxf.common.DataFormat.class;
        case "defaultbus":
        case "defaultBus": return boolean.class;
        case "defaultoperationname":
        case "defaultOperationName": return java.lang.String.class;
        case "defaultoperationnamespace":
        case "defaultOperationNamespace": return java.lang.String.class;
        case "exceptionhandler":
        case "exceptionHandler": return org.apache.camel.spi.ExceptionHandler.class;
        case "exchangepattern":
        case "exchangePattern": return org.apache.camel.ExchangePattern.class;
        case "headerfilterstrategy":
        case "headerFilterStrategy": return org.apache.camel.spi.HeaderFilterStrategy.class;
        case "hostnameverifier":
        case "hostnameVerifier": return javax.net.ssl.HostnameVerifier.class;
        case "lazystartproducer":
        case "lazyStartProducer": return boolean.class;
        case "loggingfeatureenabled":
        case "loggingFeatureEnabled": return boolean.class;
        case "loggingsizelimit":
        case "loggingSizeLimit": return int.class;
        case "mergeprotocolheaders":
        case "mergeProtocolHeaders": return boolean.class;
        case "mtomenabled":
        case "mtomEnabled": return boolean.class;
        case "password": return java.lang.String.class;
        case "portname":
        case "portName": return java.lang.String.class;
        case "properties": return java.util.Map.class;
        case "publishedendpointurl":
        case "publishedEndpointUrl": return java.lang.String.class;
        case "serviceclass":
        case "serviceClass": return java.lang.Class.class;
        case "servicename":
        case "serviceName": return java.lang.String.class;
        case "skipfaultlogging":
        case "skipFaultLogging": return boolean.class;
        case "skippayloadmessagepartcheck":
        case "skipPayloadMessagePartCheck": return boolean.class;
        case "sslcontextparameters":
        case "sslContextParameters": return org.apache.camel.support.jsse.SSLContextParameters.class;
        case "synchronous": return boolean.class;
        case "username": return java.lang.String.class;
        case "wrapped": return boolean.class;
        case "wrappedstyle":
        case "wrappedStyle": return java.lang.Boolean.class;
        case "wsdlurl":
        case "wsdlURL": return java.lang.String.class;
        default: return null;
        }
    }

    @Override
    public Object getOptionValue(Object obj, String name, boolean ignoreCase) {
        CxfEndpoint target = (CxfEndpoint) obj;
        switch (ignoreCase ? name.toLowerCase() : name) {
        case "allowstreaming":
        case "allowStreaming": return target.getAllowStreaming();
        case "bindingid":
        case "bindingId": return target.getBindingId();
        case "bridgeerrorhandler":
        case "bridgeErrorHandler": return target.isBridgeErrorHandler();
        case "bus": return target.getBus();
        case "continuationtimeout":
        case "continuationTimeout": return target.getContinuationTimeout();
        case "cookiehandler":
        case "cookieHandler": return target.getCookieHandler();
        case "cxfbinding":
        case "cxfBinding": return target.getCxfBinding();
        case "cxfconfigurer":
        case "cxfConfigurer": return target.getCxfConfigurer();
        case "dataformat":
        case "dataFormat": return target.getDataFormat();
        case "defaultbus":
        case "defaultBus": return target.isDefaultBus();
        case "defaultoperationname":
        case "defaultOperationName": return target.getDefaultOperationName();
        case "defaultoperationnamespace":
        case "defaultOperationNamespace": return target.getDefaultOperationNamespace();
        case "exceptionhandler":
        case "exceptionHandler": return target.getExceptionHandler();
        case "exchangepattern":
        case "exchangePattern": return target.getExchangePattern();
        case "headerfilterstrategy":
        case "headerFilterStrategy": return target.getHeaderFilterStrategy();
        case "hostnameverifier":
        case "hostnameVerifier": return target.getHostnameVerifier();
        case "lazystartproducer":
        case "lazyStartProducer": return target.isLazyStartProducer();
        case "loggingfeatureenabled":
        case "loggingFeatureEnabled": return target.isLoggingFeatureEnabled();
        case "loggingsizelimit":
        case "loggingSizeLimit": return target.getLoggingSizeLimit();
        case "mergeprotocolheaders":
        case "mergeProtocolHeaders": return target.isMergeProtocolHeaders();
        case "mtomenabled":
        case "mtomEnabled": return target.isMtomEnabled();
        case "password": return target.getPassword();
        case "portname":
        case "portName": return target.getPortName();
        case "properties": return target.getProperties();
        case "publishedendpointurl":
        case "publishedEndpointUrl": return target.getPublishedEndpointUrl();
        case "serviceclass":
        case "serviceClass": return target.getServiceClass();
        case "servicename":
        case "serviceName": return target.getServiceName();
        case "skipfaultlogging":
        case "skipFaultLogging": return target.isSkipFaultLogging();
        case "skippayloadmessagepartcheck":
        case "skipPayloadMessagePartCheck": return target.isSkipPayloadMessagePartCheck();
        case "sslcontextparameters":
        case "sslContextParameters": return target.getSslContextParameters();
        case "synchronous": return target.isSynchronous();
        case "username": return target.getUsername();
        case "wrapped": return target.isWrapped();
        case "wrappedstyle":
        case "wrappedStyle": return target.getWrappedStyle();
        case "wsdlurl":
        case "wsdlURL": return target.getWsdlURL();
        default: return null;
        }
    }

    @Override
    public Object getCollectionValueType(Object target, String name, boolean ignoreCase) {
        switch (ignoreCase ? name.toLowerCase() : name) {
        case "properties": return java.lang.Object.class;
        case "serviceclass":
        case "serviceClass": return java.lang.Object.class;
        default: return null;
        }
    }
}

