package com.biscience.automation.utils;

import com.biscience.automation.driver.DriverManager;
import io.qameta.allure.Allure;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.biscience.automation.config.ConfigReader.PROPERTIES;

@Slf4j
public final class ScreenshotUtil {

    private static final DateTimeFormatter TIMESTAMP = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");

    private ScreenshotUtil() {}

    public static void captureAndAttach(String testName) {
        try {
            byte[] imageBytes = ((TakesScreenshot) DriverManager.getDriver())
                .getScreenshotAs(OutputType.BYTES);

            Allure.addAttachment(
                "Screenshot – " + testName,
                "image/png",
                new ByteArrayInputStream(imageBytes),
                "png"
            );

            String dir = PROPERTIES.getProperty("screenshot.dir");
            Path screenshotDir = Paths.get(dir);
            Files.createDirectories(screenshotDir);

            String filename = sanitize(testName) + "_" + LocalDateTime.now().format(TIMESTAMP) + ".png";
            Path filePath = screenshotDir.resolve(filename);
            Files.write(filePath, imageBytes);

            log.info("Screenshot saved → {}", filePath.toAbsolutePath());

        } catch (IOException e) {
            log.error("Failed to save screenshot for '{}': {}", testName, e.getMessage());
        } catch (Exception e) {
            log.warn("Screenshot capture skipped (driver unavailable?): {}", e.getMessage());
        }
    }

    private static String sanitize(String name) {
        return name.replaceAll("[^a-zA-Z0-9_\\-]", "_").replaceAll("_+", "_");
    }
}

