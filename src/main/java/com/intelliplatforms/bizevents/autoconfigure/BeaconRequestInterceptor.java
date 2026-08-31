package com.intelliplatforms.bizevents.autoconfigure;

import com.dynatrace.openkit.api.http.HttpRequest;
import com.dynatrace.openkit.api.http.HttpRequestInterceptor;

/*
 *  Customized HttpRequestInterceptor useful for monitoring improvements in Dynatrace
 *  - It sets
 *
 * */
public class BeaconRequestInterceptor implements HttpRequestInterceptor {

    public static final String DEFAULT_USER_AGENT_HEADER_PREFIX = "CUSTOM_APP_ID";

    private final String userAgentHeaderValue;

    public BeaconRequestInterceptor(String applicationId) {
        this.userAgentHeaderValue = DEFAULT_USER_AGENT_HEADER_PREFIX + applicationId;
    }

    @Override
    public void intercept(HttpRequest httpRequest) {
        httpRequest.setHeader("User-Agent",
                userAgentHeaderValue
        );
    }
}