package com.sdet.utils;

import com.sdet.base.DriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ScreenshotUtil {

    private static final String SCREENSHOT_DIR = "test-output/screenshots/";

    public static String captureScreenshot(String testName){
        try {
            File destDir = new File(SCREENSHOT_DIR);
            if (!destDir.exists()){
                destDir.mkdirs();
            }

            String timestamp = LocalDateTime.now()
                    .format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String fileName = testName + timestamp + ".png";
            String filePath = SCREENSHOT_DIR + fileName;

            TakesScreenshot ts = (TakesScreenshot) DriverManager.getDriver();
            File source = ts.getScreenshotAs(OutputType.FILE);
            File destination = new File(filePath);
            FileUtils.copyFile(source, destination);

            return filePath;
        } catch (IOException e) {
            throw new RuntimeException("Screenshot capture failed: " + e.getMessage());
        }
    }
}
