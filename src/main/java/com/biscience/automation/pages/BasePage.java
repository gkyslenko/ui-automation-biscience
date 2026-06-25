package com.biscience.automation.pages;

import com.biscience.automation.pages.components.SidebarComponent;
import com.biscience.automation.utils.WaitUtil;

import static com.biscience.automation.config.ConfigReader.PROPERTIES;

public abstract class BasePage<T extends BasePage<T>> extends BaseElement {

    protected abstract String getEndpoint();

    public final SidebarComponent sidebar = new SidebarComponent();

    protected BasePage() {
        WaitUtil.waitForPageLoad(driver);
    }

    public void navigateTo(String relativePath) {
        driver.get(PROPERTIES.getProperty("base.url") + relativePath);
    }

    public T open() {
        navigateTo(getEndpoint());
        return (T) this;
    }

    public String currentUrl() {
        return driver.getCurrentUrl();
    }
}
