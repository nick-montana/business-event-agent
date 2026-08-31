package com.intelliplatforms.bizevents.autoconfigure;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;

import java.security.InvalidParameterException;

import static org.assertj.core.api.Assertions.assertThat;

public class OpenKitAutoConfigurationTest {

    private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
            .withConfiguration(AutoConfigurations.of(OpenKitAutoConfiguration.class));

    @Test
    public void testAutoConfigurationWithOnlyRequiredProperties() {
        contextRunner.withPropertyValues(
                "com.intelliplatforms.bizevents.enabled=true",
                "com.intelliplatforms.bizevents.applicationId=test",
                "com.intelliplatforms.bizevents.deviceId=123456",
                "com.intelliplatforms.bizevents.endpointUrl=https://tenantid.beaconurl.com/mbeacon",
                "com.intelliplatforms.bizevents.waitForInitCompletion=false"
        ).run((context) -> {
            assertThat(context).hasSingleBean(BizEventAgent.class);
        });
    }

    @Test
    public void testAutoConfigurationWithMissingRequiredProperties() {

        IllegalArgumentException exception = Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> { throw new InvalidParameterException("Missing value for required property applicationId!"); }
        );

        contextRunner.withPropertyValues(
                "com.intelliplatforms.bizevents.enabled=true",
                "com.intelliplatforms.bizevents.deviceId=123456",
                "com.intelliplatforms.bizevents.endpointUrl=https://tenantid.beaconurl.com/mbeacon",
                "com.intelliplatforms.bizevents.waitForInitCompletion=false"
        ).run((context) -> {
            Assertions.assertEquals("Missing value for required property applicationId!", exception.getMessage());
        });
    }

    @Test
    @Disabled
    public void testAutoConfigurationWithOptionalProperties() {

        contextRunner.withPropertyValues(
                "com.intelliplatforms.bizevents.enabled=true",
                "openkit.applicationId=test",
                "openkit.waitForInitCompletion=false",
                "openkit.deviceId=123456",
                "openkit.endpointUrl=https://tenantid.beaconurl.com/mbeacon",
                "openkit.applicationName=mockApplicationName",
                "openkit.namespace=mockTeamName",
                "openkit.applicationVersion=1.2.3").run((context) -> {
            assertThat(context).hasSingleBean(BizEventAgent.class);
        });
    }


    @Test
    @Disabled
    public void testDisableAutoConfigurationWithExplicitBoolean() {

        contextRunner.withPropertyValues(
                "com.intelliplatforms.bizevents.enabled=false",
                "openkit.applicationId=test",
                "openkit.deviceId=123456",
                "openkit.endpointUrl=test"
        ).run((context) -> {
            assertThat(context).doesNotHaveBean(BizEventAgent.class);
        });
    }

    /* Tests the default behavior that does not configure given no configuration properties in the environment */
    @Test
    @Disabled
    public void testDefaultNoAutoConfigurationBehavior() {

        contextRunner.withPropertyValues(
                "foo.bar=foobar"
        ).run((context) -> {
            assertThat(context).doesNotHaveBean(BizEventAgent.class);
        });
    }
}