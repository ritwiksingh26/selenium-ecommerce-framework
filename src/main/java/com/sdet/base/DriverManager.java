package com.sdet.base;

import com.sdet.config.ConfigReader;
import com.sdet.utils.LogUtil;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.time.Duration;


public class DriverManager {

    private static final Logger log = LogUtil.getLogger(DriverManager.class);
    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public static void initDriver(){
        String browser = ConfigReader.get("browser").toLowerCase();
        boolean headless = Boolean.parseBoolean(ConfigReader.get("headless"));
        log.info("Initialising browser: {} | headless: {}", browser, headless);

        //noinspection SwitchStatementWithTooFewBranches
        switch (browser) {
            case "firefox" -> {
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions options = new FirefoxOptions();
                if (headless) options.addArguments("--headless");
                driver.set(new FirefoxDriver(options));
            }
            default -> {
                WebDriverManager.chromedriver().setup();
                ChromeOptions options = new ChromeOptions();
                options.addArguments("--no-sandbox");
                options.addArguments("--disable-dev-shm-usage");
                options.addArguments("--disable-gpu");
                options.addArguments("--window-size=1920, 1080");
                if (headless) options.addArguments("--headless=new");
                driver.set(new ChromeDriver());
            }
        }
        getDriver().manage().window().maximize();
        getDriver().manage().timeouts()
                .implicitlyWait(Duration.ofSeconds(ConfigReader.getInt("implicitWait")));
        getDriver().manage().timeouts()
                .pageLoadTimeout(Duration.ofSeconds(ConfigReader.getInt("pageLoadTimeout")));
        getDriver().get(ConfigReader.get("baseUrl"));
        log.info("Browser launched and navigated to: {}", ConfigReader.get("baseUrl"));

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
