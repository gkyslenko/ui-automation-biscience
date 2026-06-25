package com.biscience.automation.enums;

import lombok.Getter;

@Getter
public enum Endpoints {
    LOGIN("/login"),
    HOME_PAGE("/ad-intelligence/start"),
    BRAND_PAGE("/brand");

    private final String path;

    Endpoints(String path) {
        this.path = path;
    }
}
