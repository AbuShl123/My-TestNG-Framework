package com.abu.utils;

public class Waits {

    private Waits() {
    }

    public static final long DEFAULT_DELAY = 450L;
    public static final int DEFAULT_DURATION = 25;

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
