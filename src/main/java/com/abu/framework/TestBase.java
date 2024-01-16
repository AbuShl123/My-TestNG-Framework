package com.abu.framework;

import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public abstract class TestBase extends ExecutionListener {

    private final static Map<Integer, TestObject> tests = new HashMap<>();

    public static TestObject getCurrentTest() {
        return tests.get((int) Thread.currentThread().getId());
    }

    @BeforeMethod(alwaysRun = true)
    public void beforeMethod(ITestContext ctx, Method method) {


        tests.put((int) Thread.currentThread().getId(), new TestObject(method.getName()));
        Test test = method.getAnnotation(Test.class);
        ExtentManager.startReport(ctx, method.getName().toUpperCase() + ": " + test.description());
    }

    @AfterMethod(alwaysRun = true)
    public void afterMethod(ITestResult result) {

    }
}
