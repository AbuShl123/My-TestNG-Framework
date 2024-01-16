package com.abu.utils;

import com.abu.selenium.Driver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.util.List;

public class WebUtils {

    private WebUtils() {
    }

    public static WebDriver getDriver() {
        return Driver.getDriver();
    }

    public static Actions getActions() {
        return new Actions(getDriver());
    }

    public static WebElement findElement(By by) {
        return getDriver().findElement(by);
    }

    public static List<WebElement> findElements(By by) {
        return getDriver().findElements(by);
    }
}
