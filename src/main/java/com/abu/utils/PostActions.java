package com.abu.utils;

public enum PostActions implements Runnable {
    SHORT_SLEEP      (() -> Waits.waitFor(1)),
    MEDIUM_SLEEP     (() -> Waits.waitFor(3)),
    LONG_SLEEP       (() -> Waits.waitFor(5)),
    NONE             (() -> Waits.waitFor(Waits.DEFAULT_DELAY));

    final Runnable action;

    PostActions(Runnable action) {
        this.action = action;
    }

    public void run() {
        action.run();
    }
}
