package com.abu.framework;

import com.abu.selenium.Driver;
import com.abu.utils.Logger;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

@Listeners(ExecutionListener.class)

public abstract class TestBase {

    private final static Map<Integer, TestObject> tests = new HashMap<>();

    public synchronized static TestObject getCurrentTest() {
        return tests.get((int) Thread.currentThread().getId());
    }

    @BeforeMethod(alwaysRun = true)
    public void beforeMethod(ITestContext ctx, Method method) {
        LogFactory.setLogProps(ctx, method);
        tests.put((int) Thread.currentThread().getId(), new TestObject(method.getName()));
        Test test = method.getAnnotation(Test.class);
        ExtentManager.startReport(ctx, method.getName().toUpperCase() + ": " + test.description());
        Logger.consoleLog("*".repeat(25) + " Starting " + method.getName().toUpperCase() + " " + "*".repeat(25));
    }

    @AfterMethod(alwaysRun = true)
    public void afterMethod(ITestResult result) {
        this.reportTearDownMethod(result);
        ExtentManager.flush();
        Driver.close();
    }

    private void reportTearDownMethod(ITestResult result) {
        if (result.isSuccess()) {
            Logger.consoleLog("passed.");
        } else if (result.getStatus() == ITestResult.FAILURE) {
            Throwable e = result.getThrowable();
            Logger.logException(e);
            Logger.consoleLog("failed.");
        } else if (result.getStatus() == ITestResult.SKIP) {
            Throwable e = result.getThrowable();
            Logger.logException(e);
            Logger.consoleLog("skipped.");
        }
    }
}
