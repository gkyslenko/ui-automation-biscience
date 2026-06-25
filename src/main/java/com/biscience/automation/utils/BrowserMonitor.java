package com.biscience.automation.utils;

import com.biscience.automation.driver.DriverManager;
import io.qameta.allure.Allure;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.testng.Assert.assertTrue;

@Slf4j
public class BrowserMonitor {

    private final List<String> httpErrors = Collections.synchronizedList(new ArrayList<>());
    private final List<String> consoleErrors = Collections.synchronizedList(new ArrayList<>());
    private final List<String> jsExceptions = Collections.synchronizedList(new ArrayList<>());

    public BrowserMonitor() {
        DriverManager.getNetwork().onResponseStarted(response -> {
            long status = response.getResponseData().getStatus();
            if (status >= 500) {
                String entry = "[HTTP " + status + "] " + response.getRequest().getUrl();
                log.warn("Browser 5xx detected: {}", entry);
                httpErrors.add(entry);
            }
        });

        DriverManager.getLogInspector().onConsoleEntry(entry -> {
            if ("error".equalsIgnoreCase(entry.getLevel().toString())) {
                String msg = entry.getText();
                log.warn("Console error: {}", msg);
                consoleErrors.add(msg);
            }
        });

        DriverManager.getLogInspector().onJavaScriptException(entry -> {
            String msg = entry.getText();
            log.warn("JS exception: {}", msg);
            jsExceptions.add(msg);
        });
    }

    public void assertNoHttpErrors() {
        attachIfNotEmpty("HTTP 5xx errors", httpErrors);
        assertTrue(httpErrors.isEmpty(),
                "HTTP 5xx errors detected:\n" + String.join("\n", httpErrors));
    }

    public void assertNoConsoleErrors() {
        attachIfNotEmpty("Console errors", consoleErrors);
        assertTrue(consoleErrors.isEmpty(),
                "Console errors detected:\n" + String.join("\n", consoleErrors));
    }

    public void assertNoJsExceptions() {
        attachIfNotEmpty("JS exceptions", jsExceptions);
        assertTrue(jsExceptions.isEmpty(),
                "Unhandled JS exceptions detected:\n" + String.join("\n", jsExceptions));
    }

    public void assertClean() {
        assertNoHttpErrors();
        assertNoConsoleErrors();
        assertNoJsExceptions();
    }

    private void attachIfNotEmpty(String label, List<String> entries) {
        if (!entries.isEmpty()) {
            Allure.addAttachment(label, "text/plain",
                    String.join("\n", entries));
        }
    }
}