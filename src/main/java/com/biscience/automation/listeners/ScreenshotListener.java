package com.biscience.automation.listeners;

import com.biscience.automation.utils.ScreenshotUtil;
import lombok.extern.slf4j.Slf4j;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestResult;

@Slf4j
public class ScreenshotListener implements IInvokedMethodListener {

    @Override
    public void afterInvocation(IInvokedMethod method, ITestResult result) {
        if (method.isTestMethod() && !result.isSuccess()) {
            String testName = result.getTestClass().getName() + "." + result.getName();
            log.warn("Test FAILED – capturing screenshot: {}", testName);
            ScreenshotUtil.captureAndAttach(testName);
        }
    }
}
