package com.sdet.base;

import com.sdet.config.ConfigReader;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;


public class DriverManager {
    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public static void initDriver(){
        String browser = ConfigReader.get("browser").toLowerCase();

        //noinspection SwitchStatementWithTooFewBranches
        switch (browser) {
            case "firefox" -> {
                WebDriverManager.firefoxdriver().setup();
                driver.set(new FirefoxDriver());
            }
            default -> {
                WebDriverManager.chromedriver().setup();
                driver.set(new ChromeDriver());
            }
        }
        getDriver().manage().window().maximize();
        getDriver().get(ConfigReader.get("baseUrl"));

    }

    public static WebDriver getDriver(){
        return driver.get();
    }

    public static void quitDriver(){
        if (driver.get() != null) {
            driver.get().quit();
            driver.remove();
        }
    }
}
