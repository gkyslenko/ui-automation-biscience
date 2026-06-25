package com.biscience.automation.pages;

import com.biscience.automation.driver.DriverManager;
import com.biscience.automation.utils.WaitUtil;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.bidi.module.Network;
import org.openqa.selenium.support.PageFactory;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import static com.biscience.automation.config.ConfigReader.PROPERTIES;

public abstract class BaseElement {
    protected final WebDriver driver;
    protected final Network network;

    protected BaseElement() {
        this.driver = DriverManager.getDriver();
        this.network = DriverManager.getNetwork();
        PageFactory.initElements(driver, this);
    }

    protected int waitForApiResponse(String urlFragment, Runnable action) {
        AtomicLong statusCode = new AtomicLong();
        CountDownLatch latch = new CountDownLatch(1);

        network.onResponseStarted(response -> {
            if (response.getRequest().getUrl().contains(urlFragment)) {
                statusCode.set(response.getResponseData().getStatus());
                latch.countDown();
            }
        });

        action.run();

        try {
            int timeout = Integer.parseInt(PROPERTIES.getProperty("explicit.wait"));
            boolean received = latch.await(timeout, TimeUnit.SECONDS);
            if (!received) {
                throw new RuntimeException(
                        "No response received for '" + urlFragment + "' within " + timeout + "s");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Interrupted waiting for API response: " + urlFragment, e);
        }

        return (int) statusCode.get();
    }

    protected void clickElement(WebElement element) {
        WaitUtil.waitForClickable(driver, element).click();
    }

    protected void sendKeys(WebElement element, String text) {
        WaitUtil.waitForVisible(driver, element).sendKeys(text);
    }

    protected boolean isElementVisible(WebElement element) {
        try {
            return WaitUtil.waitForVisible(driver, element).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
