package com.biscience.automation.utils;

import org.awaitility.Awaitility;
import org.awaitility.core.ConditionTimeoutException;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

import static com.biscience.automation.config.ConfigReader.PROPERTIES;

public final class WaitUtil {

    private static final long DEFAULT_POLL_MS = 500;

    private WaitUtil() {
    }

    public static WebElement waitForVisible(WebDriver driver, WebElement element) {
        return wait(driver).until(ExpectedConditions.visibilityOf(element));
    }

    public static WebElement waitForClickable(WebDriver driver, WebElement element) {
        return wait(driver).until(ExpectedConditions.elementToBeClickable(element));
    }

    public static boolean waitForAttributeContains(WebDriver driver, WebElement element, String attribute, String value) {
        try {
            return wait(driver).until(ExpectedConditions.attributeContains(element, attribute, value));
        } catch (TimeoutException e) {
            return false;
        }
    }

    public static void waitForPageLoad(WebDriver driver) {
        wait(driver).until(d -> "complete".equals(((JavascriptExecutor) d).executeScript("return document.readyState")));
    }

    public static boolean awaitUntilTrue(Supplier<Boolean> condition) {
        long timeoutSec = Long.parseLong(PROPERTIES.getProperty("explicit.wait"));
        try {
            Awaitility.await()
                    .atMost(timeoutSec, TimeUnit.SECONDS)
                    .pollInterval(DEFAULT_POLL_MS, TimeUnit.MILLISECONDS)
                    .ignoreExceptions()
                    .until(condition::get);
            return true;
        } catch (ConditionTimeoutException e) {
            return false;
        }
    }

    private static WebDriverWait wait(WebDriver driver) {
        return new WebDriverWait(driver, Duration.ofSeconds(Long.parseLong(PROPERTIES.getProperty("explicit.wait"))));
    }
}

