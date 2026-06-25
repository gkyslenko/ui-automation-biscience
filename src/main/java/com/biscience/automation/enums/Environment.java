package com.biscience.automation.enums;

import lombok.Getter;

@Getter
public enum Environment {
    STAGING("https://stg-ui.adcint.com/", "STAGING"),
    PRODUCTION("https://prod-ui.adcint.com/", "PRODUCTION");

    private final String baseUri;
    private final String environmentName;

    Environment(final String baseUri, final String environmentName) {
        this.baseUri = baseUri;
        this.environmentName = environmentName;
    }
}
