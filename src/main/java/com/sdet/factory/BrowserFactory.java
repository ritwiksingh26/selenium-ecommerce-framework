package com.sdet.factory;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import com.sdet.config.ConfigReader;
import com.sdet.utils.LogUtil;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BrowserFactory {
    
    private static final Logger log = LogUtil.getLogger(BrowserFactory.class);

    public static WebDriver createDriver(String browser){
        boolean headless = Boolean.parseBoolean(ConfigReader.get("headless"));
        log.info("Creating browser: {} | headless: {}", browser, headless);

        return switch (browser.toLowerCase().trim()){
            case "firefox" -> createFireFoxDriver(headless);
            case "edge" -> createEdgeDriver(headless);
            default -> createChromeDriver(headless);
        };
    }

    private static WebDriver createChromeDriver(boolean headless){
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-gpu");
        options.addArguments("--window-size=1920,1080");
        if (headless) options.addArguments("--headless=new");
        return new ChromeDriver(options);
    }

    private static WebDriver createFireFoxDriver(boolean headless) {
        WebDriverManager.firefoxdriver().setup();
        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("--width=1920");
        options.addArguments("--height=1080");
        if (headless) options.addArguments("--headless");
        return new FirefoxDriver(options);
    }

    private static WebDriver createEdgeDriver(boolean headless) {
        WebDriverManager.edgedriver().setup();
        EdgeOptions options = new EdgeOptions();
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--window-size=1920,1080");
        if (headless) options.addArguments("--headless=new");
        return new EdgeDriver(options);
    }
}
