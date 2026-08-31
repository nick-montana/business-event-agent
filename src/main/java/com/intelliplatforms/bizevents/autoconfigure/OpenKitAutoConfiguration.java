package com.intelliplatforms.bizevents.autoconfigure;

import com.dynatrace.openkit.api.OpenKit;
import com.dynatrace.openkit.api.Session;
import com.dynatrace.openkit.DynatraceOpenKitBuilder;
import com.dynatrace.openkit.protocol.ssl.SSLBlindTrustManager;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBooleanProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

import java.util.logging.Logger;


@AutoConfiguration
@ConditionalOnBooleanProperty(name = "com.intelliplatforms.bizevents.enabled", matchIfMissing = true)
@EnableConfigurationProperties(OpenKitProperties.class)
public class OpenKitAutoConfiguration {

    private final OpenKitProperties openKitProperties;

    private final Logger log =  Logger.getLogger(OpenKitAutoConfiguration.class.getName());

    OpenKitAutoConfiguration(OpenKitProperties properties) {
        this.openKitProperties = properties;
    }

    /*
    * This method bootstraps applications with a BizEventAgent through the Spring Bean
    * - the BizEventAgent is our own Dynatrace OpenKit implementation built to simplify bizevent exporting
    * */
    @Bean(destroyMethod = "close")
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    public BizEventAgent injectBizEventAgent() {
        DynatraceOpenKitBuilder builder = AutoConfigUtils.openKitBuilder(openKitProperties);

        //
        if (openKitProperties.isDisableSSLVerification()) {
            log.info("SSL Verification is disabled");
            SSLBlindTrustManager unverifiedSSLCertificateManager = new SSLBlindTrustManager();
            builder.withTrustManager(unverifiedSSLCertificateManager);
        }

        BeaconRequestInterceptor httpInterceptor = new BeaconRequestInterceptor(openKitProperties.getApplicationId());

        builder.withHttpRequestInterceptor(httpInterceptor);

        OpenKit openKit = this.initializeOpenKit(builder);

        // session splitting occurs in OpenKit's asynchronous background threads
        Session bizAgentSession = openKit.createSession();

        return new BizEventAgent(openKit, bizAgentSession);
    };

    /*
    *  Initialize OpenKit with/without blocking start-up thread depending on user properties
    *   - Default behavior waits for complete initialization
    * */
    private OpenKit initializeOpenKit(DynatraceOpenKitBuilder openKitBuilder) {
        OpenKit openKit = openKitBuilder.build();

        if (openKitProperties.isWaitForInitCompletion()) {
            openKit.waitForInitCompletion(60000);
        } else {
            log.info("waitForInitCompletion property is manually set to false. Successful initialization is not a certainty!");
        }

        return openKit;
    }
}
