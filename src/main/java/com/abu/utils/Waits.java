package com.abu.utils;

import com.abu.selenium.Driver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class Waits {
    private Waits() {
    }

    /**
     * Default delay between any action on the page (usually is half of a second).
     */
    public static final long DEFAULT_DELAY = 450L;

    /**
     * Default duration time - duration of seconds for webdriver wait to time out.
     */
    public static final int DEFAULT_DURATION = 25;

    public static WebDriverWait getWait(int duration) {
        return new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(duration));
    }

    public static WebDriverWait getWait() {
        return getWait(DEFAULT_DURATION);
    }

    public static void waitFor(int sec) {
        waitFor(sec * 1000L);
    }

    public static void waitFor(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
