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


    //Builds a unique key that works for both regular and data-driven tests
    private String getTestKey(ITestResult result){
        String methodName = result.getMethod().getMethodName();
        Object[] params = result.getParameters();
        if (params != null && params.length > 0){
            return methodName + "_" + result.getMethod().getCurrentInvocationCount();
        }
        return methodName;
    }
    @Override
    public void onStart(ITestContext context) {
        extent = ExtentManager.getInstance();
        log.info("==== Test Suite Started: {} ====", context.getName());
    }

    @Override
    public void onTestStart(ITestResult result){
        String testKey = getTestKey(result);

        // For data-driven tests, append parameter summary to the display name
        String displayName = result.getMethod().getMethodName();
        Object[] params = result.getParameters();
        if (params != null && params.length > 0){
            displayName += " " + java.util.Arrays.toString(params);
        }
        ExtentTest extentTest = extent.createTest(displayName, result.getMethod().getDescription());
        testMap.put(testKey, extentTest);
        log.info("Test started: {}", testKey);
    }

    @Override
    public void onTestSuccess(ITestResult result){
        String testKey = getTestKey(result);
        ExtentTest extentTest = testMap.get(testKey);
        if (extentTest != null){
            extentTest.log(Status.PASS, "Test passed");
        }
        log.info("Test PASSED: {}", testKey);
    }

    @Override
    public void onTestFailure(ITestResult result){
        String testKey = getTestKey(result);
        ExtentTest extentTest = testMap.get(testKey);

        if (extentTest != null){
            extentTest.log(Status.FAIL, "Test failed: " + result.getThrowable());

            try {
                String screenshotPath = ScreenshotUtil.captureScreenshot(result.getMethod().getMethodName());
                extentTest.addScreenCaptureFromPath("../screenshots/" +
                        screenshotPath.substring(screenshotPath.lastIndexOf("/") + 1));
                log.info("Screenshot captured: {}", screenshotPath);
            } catch (Exception e){
                log.error("Screenshot capture failed: {}", e.getMessage(), e);
            }
            log.error("Test FAILED: {} - Reason {}", testKey, result.getThrowable().getMessage());
        } else {
            log.error("ExtentTest instance not found for key: {} - screenshot skipped", testKey);
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        String testKey = getTestKey(result);
        ExtentTest extentTest = testMap.get(testKey);
        if (extentTest != null){
            extentTest.log(Status.SKIP, "Test skipped");
        }
        log.warn("Test SKIPPED: {}", testKey);
    }

    @Override
    public void onFinish(ITestContext context){
        extent.flush();
        log.info("==== Test Suite Finished: {} ====", context.getName());
    }
}
