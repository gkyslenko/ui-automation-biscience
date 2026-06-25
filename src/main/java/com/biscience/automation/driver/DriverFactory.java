package com.biscience.automation.driver;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import static com.biscience.automation.config.ConfigReader.PROPERTIES;

@Slf4j
public final class DriverFactory {

    private DriverFactory() {}

    public static WebDriver create() {
        String browser = PROPERTIES.getProperty("browser").toLowerCase();
        boolean headless = Boolean.parseBoolean(PROPERTIES.getProperty("headless"));

        log.info("Creating WebDriver  browser={} headless={}", browser, headless);

        return switch (browser) {
            case "firefox" -> createFirefox(headless);
            case "edge"    -> createEdge(headless);
            default        -> createChrome(headless);
        };
    }

    private static WebDriver createChrome(boolean headless) {
        ChromeOptions opts = new ChromeOptions();
        opts.setCapability("webSocketUrl", true);
        if (headless) opts.addArguments("--headless=new");
        opts.addArguments("--no-sandbox", "--disable-dev-shm-usage", "--disable-gpu");
        opts.addArguments("--window-size=1920,1080");
        return new ChromeDriver(opts);
    }

    private static WebDriver createFirefox(boolean headless) {
        FirefoxOptions opts = new FirefoxOptions();
        opts.setCapability("webSocketUrl", true);
        if (headless) opts.addArguments("--headless");
        opts.addArguments("--width=1920", "--height=1080");
        return new FirefoxDriver(opts);
    }

    private static WebDriver createEdge(boolean headless) {
        EdgeOptions opts = new EdgeOptions();
        opts.setCapability("webSocketUrl", true);
        if (headless) opts.addArguments("--headless=new");
        opts.addArguments("--no-sandbox", "--disable-dev-shm-usage", "--disable-gpu");
        opts.addArguments("--window-size=1920,1080");
        return new EdgeDriver(opts);
    }
}

