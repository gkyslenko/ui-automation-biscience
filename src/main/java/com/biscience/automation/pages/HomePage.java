package com.biscience.automation.pages;

import static com.biscience.automation.enums.Endpoints.HOME_PAGE;

public class HomePage extends BasePage<HomePage> {

    @Override
    protected String getEndpoint() {
        return HOME_PAGE.getPath();
    }
}
