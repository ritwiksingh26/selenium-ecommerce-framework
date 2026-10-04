package com.sdet.base;

import com.sdet.config.ConfigReader;
import com.sdet.factory.BrowserFactory;
import com.sdet.utils.LogUtil;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.time.Duration;


public class DriverManager {

    private static final Logger log = LogUtil.getLogger(DriverManager.class);
    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public static void initDriver(){
        // Read browser from system property first, fall back to config
        String browser = System.getProperty("browser", ConfigReader.get("browser")).toLowerCase().trim();
        boolean useGrid = Boolean.parseBoolean(System.getProperty("useGrid", ConfigReader.get("useGrid")));

        if (useGrid) {
            initRemoteDriver(browser);
        } else {
            initLocalDriver(browser);
        }
        
        applyTimeouts();
        getDriver().get(ConfigReader.get("baseUrl"));
        log.info("Navigated to: {}", ConfigReader.get("baseUrl"));
    }

    private static void initLocalDriver(String browser){
        driver.set(BrowserFactory.createDriver(browser));
        getDriver().manage().window().maximize();
        log.info("Local driver initialised: {}", browser);
    }

    private static void initRemoteDriver(String browser){
        String gridUrl = ConfigReader.get("gridUrl");
        log.info("Connecting to Selenium Grid at: {} | browser: {}", gridUrl, browser);

        try {
            URL gridUri = URI.create(gridUrl).toURL();

            WebDriver remoteDriver = switch (browser) {
                case "firefox" -> new RemoteWebDriver(gridUri, new FirefoxOptions());
                default        -> new RemoteWebDriver(gridUri, new ChromeOptions());
            };

            driver.set(remoteDriver);
            log.info("Remote driver initialised successfully");
        } catch (MalformedURLException e) {
            throw new RuntimeException("Invalid Grid URL: " + gridUrl, e);
        }
    }

    private static void applyTimeouts(){
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(ConfigReader.getInt("implicitWait")));
        getDriver().manage().timeouts().pageLoadTimeout(Duration.ofSeconds(ConfigReader.getInt("pageLoadTimeout")));;
    }

    public static WebDriver getDriver(){
        return driver.get();
    }

    public static void quitDriver(){
        WebDriver d = driver.get();
        if (d != null) {
            try {
                d.quit();
                log.info("Browser closed");
            } catch (Exception e) {
                log.warn("Driver quit failed: {}", e.getMessage());
            } finally {
                driver.remove();
            }
        }
    }
}
