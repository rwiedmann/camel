/* Generated by camel build tools - do NOT edit this file! */
package org.apache.camel.vault;

import java.util.Map;

import org.apache.camel.CamelContext;
import org.apache.camel.spi.ExtendedPropertyConfigurerGetter;
import org.apache.camel.spi.PropertyConfigurerGetter;
import org.apache.camel.spi.ConfigurerStrategy;
import org.apache.camel.spi.GeneratedPropertyConfigurer;
import org.apache.camel.util.CaseInsensitiveMap;
import org.apache.camel.vault.GcpVaultConfiguration;

/**
 * Generated by camel build tools - do NOT edit this file!
 */
@SuppressWarnings("unchecked")
public class GcpVaultConfigurationConfigurer extends org.apache.camel.support.component.PropertyConfigurerSupport implements GeneratedPropertyConfigurer, PropertyConfigurerGetter {

    @Override
    public boolean configure(CamelContext camelContext, Object obj, String name, Object value, boolean ignoreCase) {
        org.apache.camel.vault.GcpVaultConfiguration target = (org.apache.camel.vault.GcpVaultConfiguration) obj;
        switch (ignoreCase ? name.toLowerCase() : name) {
        case "awsvaultconfiguration":
        case "AwsVaultConfiguration": target.setAwsVaultConfiguration(property(camelContext, org.apache.camel.vault.AwsVaultConfiguration.class, value)); return true;
        case "azurevaultconfiguration":
        case "AzureVaultConfiguration": target.setAzureVaultConfiguration(property(camelContext, org.apache.camel.vault.AzureVaultConfiguration.class, value)); return true;
        case "gcpvaultconfiguration":
        case "GcpVaultConfiguration": target.setGcpVaultConfiguration(property(camelContext, org.apache.camel.vault.GcpVaultConfiguration.class, value)); return true;
        case "hashicorpvaultconfiguration":
        case "HashicorpVaultConfiguration": target.setHashicorpVaultConfiguration(property(camelContext, org.apache.camel.vault.HashicorpVaultConfiguration.class, value)); return true;
        case "projectid":
        case "ProjectId": target.setProjectId(property(camelContext, java.lang.String.class, value)); return true;
        case "refreshenabled":
        case "RefreshEnabled": target.setRefreshEnabled(property(camelContext, boolean.class, value)); return true;
        case "refreshperiod":
        case "RefreshPeriod": target.setRefreshPeriod(property(camelContext, long.class, value)); return true;
        case "secrets":
        case "Secrets": target.setSecrets(property(camelContext, java.lang.String.class, value)); return true;
        case "serviceaccountkey":
        case "ServiceAccountKey": target.setServiceAccountKey(property(camelContext, java.lang.String.class, value)); return true;
        case "subscriptionname":
        case "SubscriptionName": target.setSubscriptionName(property(camelContext, java.lang.String.class, value)); return true;
        case "usedefaultinstance":
        case "UseDefaultInstance": target.setUseDefaultInstance(property(camelContext, boolean.class, value)); return true;
        default: return false;
        }
    }

    @Override
    public Class<?> getOptionType(String name, boolean ignoreCase) {
        switch (ignoreCase ? name.toLowerCase() : name) {
        case "awsvaultconfiguration":
        case "AwsVaultConfiguration": return org.apache.camel.vault.AwsVaultConfiguration.class;
        case "azurevaultconfiguration":
        case "AzureVaultConfiguration": return org.apache.camel.vault.AzureVaultConfiguration.class;
        case "gcpvaultconfiguration":
        case "GcpVaultConfiguration": return org.apache.camel.vault.GcpVaultConfiguration.class;
        case "hashicorpvaultconfiguration":
        case "HashicorpVaultConfiguration": return org.apache.camel.vault.HashicorpVaultConfiguration.class;
        case "projectid":
        case "ProjectId": return java.lang.String.class;
        case "refreshenabled":
        case "RefreshEnabled": return boolean.class;
        case "refreshperiod":
        case "RefreshPeriod": return long.class;
        case "secrets":
        case "Secrets": return java.lang.String.class;
        case "serviceaccountkey":
        case "ServiceAccountKey": return java.lang.String.class;
        case "subscriptionname":
        case "SubscriptionName": return java.lang.String.class;
        case "usedefaultinstance":
        case "UseDefaultInstance": return boolean.class;
        default: return null;
        }
    }

    @Override
    public Object getOptionValue(Object obj, String name, boolean ignoreCase) {
        org.apache.camel.vault.GcpVaultConfiguration target = (org.apache.camel.vault.GcpVaultConfiguration) obj;
        switch (ignoreCase ? name.toLowerCase() : name) {
        case "awsvaultconfiguration":
        case "AwsVaultConfiguration": return target.getAwsVaultConfiguration();
        case "azurevaultconfiguration":
        case "AzureVaultConfiguration": return target.getAzureVaultConfiguration();
        case "gcpvaultconfiguration":
        case "GcpVaultConfiguration": return target.getGcpVaultConfiguration();
        case "hashicorpvaultconfiguration":
        case "HashicorpVaultConfiguration": return target.getHashicorpVaultConfiguration();
        case "projectid":
        case "ProjectId": return target.getProjectId();
        case "refreshenabled":
        case "RefreshEnabled": return target.isRefreshEnabled();
        case "refreshperiod":
        case "RefreshPeriod": return target.getRefreshPeriod();
        case "secrets":
        case "Secrets": return target.getSecrets();
        case "serviceaccountkey":
        case "ServiceAccountKey": return target.getServiceAccountKey();
        case "subscriptionname":
        case "SubscriptionName": return target.getSubscriptionName();
        case "usedefaultinstance":
        case "UseDefaultInstance": return target.isUseDefaultInstance();
        default: return null;
        }
    }
}

