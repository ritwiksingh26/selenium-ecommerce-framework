package com.sdet.listeners;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.sdet.utils.ExtentManager;
import com.sdet.utils.LogUtil;
import com.sdet.utils.ScreenshotUtil;
import org.apache.logging.log4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.util.HashMap;
import java.util.Map;

public class TestListeners implements ITestListener {

    private static final Logger log = LogUtil.getLogger(TestListeners.class);
    private static ExtentReports extent;
    private static final Map<String, ExtentTest> testMap = new HashMap<>();

    @Override
    public void onStart(ITestContext context) {
        extent = ExtentManager.getInstance();
        log.info("==== Test Suite Started: {} ====", context.getName());
    }

    @Override
    public void onTestStart(ITestResult result){
        String testName = result.getMethod().getMethodName();
        ExtentTest extentTest = extent.createTest(testName, result.getMethod().getDescription());
        testMap.put(testName, extentTest);
        log.info("Test started: {}", testName);
    }

    @Override
    public void onTestSuccess(ITestResult result){
        String testName = result.getMethod().getMethodName();
        testMap.get(testName).log(Status.PASS, "Test passed");
        log.info("Test PASSED: {}", testName);
    }

    @Override
    public void onTestFailure(ITestResult result){
        String testName = result.getMethod().getMethodName();
        ExtentTest extentTest = testMap.get(testName);
         extentTest.log(Status.FAIL, "Test failed: " + result.getThrowable());
         log.error("Test FAILED: {} - Reason {}", testName, result.getThrowable().getMessage());

         String screenshotPath = ScreenshotUtil.captureScreenshot(testName);
         extentTest.addScreenCaptureFromPath("../screenshots/" +
                 screenshotPath.substring(screenshotPath.lastIndexOf("/") + 1));
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        testMap.get(testName).log(Status.SKIP, "Test skipped");
        log.warn("Test SKIPPED: {}", testName);
    }

    @Override
    public void onFinish(ITestContext context){
        extent.flush();
        log.info("==== Test Suite Finished: {} ====", context.getName());
    }
}
