package com.intelliplatforms.bizevents.autoconfigure;

import com.dynatrace.openkit.api.OpenKitConstants;
import com.dynatrace.openkit.core.configuration.ConfigurationDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;


/**
 * OpenKitAutoConfiguration properties
 *
 * These properties are adapted to match the primary focus which is sending bizevents.
 * To-Do: elaborate more before showing the following:
 *
 * ApplicationName = ModelId
 * Namespace = Manufacturer
 *
 * The following two OpenKit Configurations are intentionally left out since they are unrelated to bizevent funcitonality
 *  1. DataCollectionLevel
 *  2. CrashReportingLevel
 *
 *
 * <a href="http://json.org/">OpenKit GitHub Documentation</a>.
 */
@ConfigurationProperties(prefix = "com.intelliplatforms.bizevents")
public class OpenKitProperties {


    private String applicationId;

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    public String getEndpointUrl() {
        return endpointUrl;
    }

    public void setEndpointUrl(String endpointUrl) {
        this.endpointUrl = endpointUrl;
    }

    public long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(long deviceId) {
        this.deviceId = deviceId;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public String getApplicationVersion() {
        return applicationVersion;
    }

    public void setApplicationVersion(String applicationVersion) {
        this.applicationVersion = applicationVersion;
    }

    public String getOperatingSystem() {
        return operatingSystem;
    }

    public void setOperatingSystem(String operatingSystem) {
        this.operatingSystem = operatingSystem;
    }

    public boolean isWaitForInitCompletion() {
        return waitForInitCompletion;
    }

    public void setWaitForInitCompletion(boolean waitForInitCompletion) {
        this.waitForInitCompletion = waitForInitCompletion;
    }

    private String endpointUrl;

    public String getLogLevel() {
        return logLevel;
    }

    public void setLogLevel(String logLevel) {
        this.logLevel = logLevel;
    }

    private String logLevel;

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    private String manufacturer;
    private String operatingSystem;
    private boolean waitForInitCompletion;


    public boolean isDisableSSLVerification() {
        return disableSSLVerification;
    }

    public void setDisableSSLVerification(boolean disableSSLVerification) {
        this.disableSSLVerification = disableSSLVerification;
    }

    private boolean disableSSLVerification = false;

    private String applicationName;
    private String applicationVersion;

    private long deviceId;

}
