package com.sdet.listeners;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.sdet.utils.ExtentManager;
import com.sdet.utils.LogUtil;
import org.apache.logging.log4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.util.Arrays;


public class TestListeners implements ITestListener {

    private static final Logger log = LogUtil.getLogger(TestListeners.class);
    private static ExtentReports extent;
    private static final ThreadLocal<ExtentTest> currentTest = new ThreadLocal<>();

    @Override
    public void onStart(ITestContext context) {
        synchronized (TestListeners.class) {
            extent = ExtentManager.getInstance();
        }
        log.info("===== Test Suite Started: {} =====", context.getName());
    }

    @Override
    public void onTestStart(ITestResult result) {
        String displayName = result.getMethod().getMethodName();
        Object[] params = result.getParameters();
        if (params != null && params.length > 0) {
            displayName += " " + Arrays.toString(params);
        }
        currentTest.set(extent.createTest(displayName, result.getMethod().getDescription()));
        log.info("Test started: {}", displayName);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        log("PASS", Status.PASS, "Test passed", result);
    }

    @Override
    public void onTestFailure(ITestResult result) {
        log("FAIL", Status.FAIL, "Test failed: " + result.getThrowable(), result);
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        log("SKIP", Status.SKIP, "Test skipped", result);
    }

    @Override
    public void onFinish(ITestContext context) {
        extent.flush();
        log.info("===== Test Suite Finished: {} =====", context.getName());
    }

    // Called from BaseTest.tearDown while the browser is still open
    public static void attachScreenshot(ITestResult result, String screenshotPath) {
        ExtentTest test = currentTest.get();
        if (test == null || screenshotPath == null || screenshotPath.isEmpty()) {
            log.warn("Screenshot not attached for: {}", result.getName());
            return;
        }
        test.addScreenCaptureFromPath("../screenshots/"
            + screenshotPath.substring(screenshotPath.lastIndexOf('/') + 1));
    }

    private void log(String label, Status status, String message, ITestResult result) {
        ExtentTest test = currentTest.get();
        if (test != null) {
            test.log(status, message);
        }
        log.info("Test {}: {}", label, result.getName());
    }   
}
