package com.abu.selenium;

import com.abu.utils.EnvParams;
import com.abu.utils.Logger;
import io.github.bonigarcia.wdm.managers.ChromeDriverManager;
import org.apache.commons.logging.Log;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class Driver {
    private static WebDriver driver;

    private Driver() {
    }

    public static WebDriver getDriver() {
        if (driver == null) {
            initDriver();
        }

        return driver;
    }

    private static void initDriver() {
        String browser = EnvParams.BROWSER;

        if (browser.equalsIgnoreCase("chrome")) {
            Logger.log("Launching Chrome...");
            ChromeDriverManager.chromedriver().setup();

            ChromeOptions options = new ChromeOptions();
            options.addArguments("maximize-window");

            driver = new ChromeDriver(options);
            Logger.log("Chrome started successfully");
        }
    }

    public void close() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}
