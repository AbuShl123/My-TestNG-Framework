package com.abu.framework;

import org.testng.ITestContext;

import java.lang.reflect.Method;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LogFactory {

    private LogFactory() {
    }

    synchronized static void setLogProps(ITestContext ctx, Method method) {
        clearLogs();
        setLogFileName(ctx, method);
        System.out.println(System.getProperty("logfile.name"));
    }

    private static void setLogFileName(ITestContext ctx, Method method) {
        StringBuilder logFileName = new StringBuilder();

        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM"));

        switch (ctx.getSuite().getName()) {
            case "Default Suite":
                logFileName.append(method.getName());
                break;
            case "Bulk Execution":
                logFileName.append("regression");
                break;
            case "Smoke Execution":
                logFileName.append("smoke");
                break;
            default:
                logFileName.append(ctx.getSuite().getName());
        }

        if (Thread.currentThread().getName().equals("main")) {
            logFileName.append(" [main]");
        } else {
            logFileName.append(" [").append(Thread.currentThread().getName()).append("]");
        }

        logFileName.append(" ").append(date);
        System.setProperty("logfile.name", logFileName.toString());
    }

    private static void clearLogs() {

    }
}
