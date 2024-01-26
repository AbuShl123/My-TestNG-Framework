package com.abu.framework;

import org.testng.ITestContext;

import java.lang.reflect.Method;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LogFactory {

    private static final String LOG_DIRECTORY = "logs/";

    private LogFactory() {
    }

    synchronized static void setLogProps(ITestContext ctx, Method method) {
        clearLogs();
        setLogFileName(ctx, method);
        System.out.println("LOG: " + System.getProperty("logfile"));
    }

    private static void setLogFileName(ITestContext ctx, Method method) {
        StringBuilder logFileName = new StringBuilder(LOG_DIRECTORY);

        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        String filename = getFileName(ctx, method);
        String thread = getThreadName();

        logFileName
                .append(date)
                .append("/")
                .append(filename)
                .append(thread)
                .append(".log");

        System.setProperty("logfile", logFileName.toString());
    }

    private static void clearLogs() {

    }

    private static String getThreadName() {
        if (!"main".equals(Thread.currentThread().getName())) {
            return String.format(" [%s]", Thread.currentThread().getName());
        }

        return "";
    }

    private static String getFileName(ITestContext ctx, Method method) {
        switch (ctx.getSuite().getName()) {
            case "Default Suite": return method.getName();

            case "Bulk Execution": return "regression";

            case "Smoke Execution": return "smoke";

            default: return ctx.getSuite().getName();
        }
    }
}
