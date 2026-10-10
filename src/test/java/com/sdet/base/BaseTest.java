package com.sdet.base;

import org.apache.logging.log4j.Logger;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import com.beust.jcommander.Parameter;
import com.sdet.listeners.TestListeners;
import com.sdet.utils.LogUtil;
import com.sdet.utils.ScreenshotUtil;

public class BaseTest {

    private static final Logger log = LogUtil.getLogger(BaseTest.class);

    @Parameters({"browser"})
    @BeforeMethod
    public void setUp(@Optional("") String browser){
        // Override config browser with suite XML parameter
        if (!browser.isBlank()) {
            System.setProperty("browser", browser);
        }
        DriverManager.initDriver();
        log.info("Browser launched: {}", browser);
    }

    @AfterMethod
    public void tearDown(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            String screenshotPath = ScreenshotUtil.captureScreenshot(result.getMethod().getMethodName());
            TestListeners.attachScreenshot(result, screenshotPath);
            log.info("Failure screenshot saved: {}", screenshotPath);
        }
        DriverManager.quitDriver();
    }
}
