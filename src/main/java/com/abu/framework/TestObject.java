package com.abu.framework;


/**
 * This class contains data specific to the current test method being run.
 * Useful for parallel execution
 */
public class TestObject {
    private final String name;
    private boolean loggable;

    public TestObject(String name) {
        this.name = name;
        loggable = true;
    }

    public void setLoggable(boolean loggable) {
        this.loggable = loggable;
    }

    public String getName() {
        return name;
    }

    public boolean isLoggable() {
        return loggable;
    }
}
