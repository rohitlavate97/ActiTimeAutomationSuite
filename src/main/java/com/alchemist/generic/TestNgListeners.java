package com.alchemist.generic;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TestNgListeners implements ITestListener {

    private static final Logger log = LogManager.getLogger(TestNgListeners.class);

    @Override
    public void onTestStart(ITestResult result) {
        log.info("===== Test Started: " + result.getName() + " =====");
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        log.info("===== Test Passed: " + result.getName() + " =====");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        log.error("===== Test Failed: " + result.getName() + " =====");

        // Get the driver from the test instance (BaseTest)
        Object testClass = result.getInstance();
        BaseTest base = (BaseTest) testClass;

        try {
            base.takeScreenshot(result.getName());
            log.info("Screenshot captured for failed test: " + result.getName());
        } catch (Exception e) {
            log.error("Failed to capture screenshot for test: " + result.getName(), e);
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        log.warn("===== Test Skipped: " + result.getName() + " =====");
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        log.warn("===== Test Failed but within success %: " + result.getName() + " =====");
    }

    @Override
    public void onTestFailedWithTimeout(ITestResult result) {
        log.error("===== Test Failed due to Timeout: " + result.getName() + " =====");
        onTestFailure(result); // Capture screenshot and log
    }

    @Override
    public void onStart(ITestContext context) {
        log.info("===== Test Suite Started: " + context.getName() + " =====");
    }

    @Override
    public void onFinish(ITestContext context) {
        log.info("===== Test Suite Finished: " + context.getName() + " =====");
    }
}
