package com.intelliplatforms.bizevents.autoconfigure;

import com.dynatrace.openkit.CrashReportingLevel;
import com.dynatrace.openkit.DataCollectionLevel;
import com.dynatrace.openkit.DynatraceOpenKitBuilder;
import com.dynatrace.openkit.api.LogLevel;
import com.dynatrace.openkit.api.OpenKitConstants;

import java.security.InvalidParameterException;
import java.text.MessageFormat;

/* Contains utility methods for @OpenKitAutConfiguration
*/
public interface AutoConfigUtils {

    DataCollectionLevel bizeventAgentCollectionLevel = DataCollectionLevel.OFF;

    enum OS {
        WINDOWS, LINUX, MAC, SOLARIS, UNKOWN
    };// Operating systems.


    static DynatraceOpenKitBuilder openKitBuilder(OpenKitProperties properties) {

        // check that the three required configurations are present
        // throws exception if any are missing
        validateRequiredProperty("applicationId",  properties.getApplicationId());
        validateRequiredProperty("deviceId", properties.getDeviceId());
        validateRequiredProperty("endpointUrl", properties.getEndpointUrl());

        DynatraceOpenKitBuilder openKitBuilder = new DynatraceOpenKitBuilder(
                properties.getEndpointUrl(),
                properties.getApplicationId(),
                properties.getDeviceId()
        );

        // only configure with a logger if its level is explicitly set
        if(properties.getLogLevel() != null) {
            openKitBuilder.withLogLevel(setOpenKitLogger(properties.getLogLevel()));
        }
        String osManufacturer = System.getProperty("os.manufacturer").toLowerCase();
        openKitBuilder.withManufacturer(osManufacturer);
        openKitBuilder.withModelID(properties.getApplicationName());
        openKitBuilder.withOperatingSystem(getOS().toString());
        openKitBuilder.withApplicationVersion(openKitBuilder.getApplicationVersion());
        openKitBuilder.withDataCollectionLevel(bizeventAgentCollectionLevel);
        openKitBuilder.withCrashReportingLevel(CrashReportingLevel.OFF);
        return openKitBuilder;
    };

    /* Throws Exception if implementation is missing required properties
     *  - this prevents failure during OpenKit initialization
     * */
    private static void validateRequiredProperty(String propertyKey, Object propertyValue) {
        if(propertyValue == null) {
            throw new InvalidParameterException(MessageFormat.format("Missing value for required property {0}!", propertyKey));
        }
    }


    private static OS getOS() {
        String operSys = System.getProperty("os.name").toLowerCase();

        if (operSys.contains("win")) {
            return OS.WINDOWS;
        } else if (operSys.contains("nix") || operSys.contains("nux")
                || operSys.contains("aix")) {
            return OS.LINUX;
        } else if (operSys.contains("mac")) {
            return OS.MAC;
        } else if (operSys.contains("sunos")) {
            return OS.SOLARIS;
        }
        return OS.UNKOWN;
    }


    /* Properly sets OpenKit compatible log-level from a string representation
    * */
    private static LogLevel setOpenKitLogger(String logLevelString) {
        return LogLevel.valueOf(logLevelString);
    };
}
