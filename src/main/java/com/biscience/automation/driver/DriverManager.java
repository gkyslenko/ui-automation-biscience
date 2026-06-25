package com.biscience.automation.driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.bidi.module.LogInspector;
import org.openqa.selenium.bidi.module.Network;

public final class DriverManager {

    private static final ThreadLocal<WebDriver> DRIVER = new ThreadLocal<>();
    private static final ThreadLocal<Network> NETWORK = new ThreadLocal<>();
    private static final ThreadLocal<LogInspector> LOG_INSPECTOR = new ThreadLocal<>();

    private DriverManager() {
    }

    public static void init() {
        WebDriver driver = DriverFactory.create();
        DRIVER.set(driver);
        NETWORK.set(new Network(driver));
        LOG_INSPECTOR.set(new LogInspector(driver));
    }

    public static WebDriver getDriver() {
        return DRIVER.get();
    }

    public static Network getNetwork() {
        return NETWORK.get();
    }

    public static LogInspector getLogInspector() {
        return LOG_INSPECTOR.get();
    }

    public static void quit() {
        WebDriver driver = DRIVER.get();
        if (driver != null) {
            driver.quit();
            DRIVER.remove();
        }

        closeQuietly(NETWORK.get());
        NETWORK.remove();
        closeQuietly(LOG_INSPECTOR.get());
        LOG_INSPECTOR.remove();
    }

    private static void closeQuietly(AutoCloseable c) {
        if (c != null) {
            try {
                c.close();
            } catch (Exception ignored) {
            }
        }
    }
}

