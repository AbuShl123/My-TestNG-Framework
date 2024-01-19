package com.abu.framework;

import org.testng.ITestContext;

import java.lang.reflect.Method;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LogFactory {

    private LogFactory() {
    }

    public static void setLogProps(ITestContext ctx, Method method) {
        StringBuilder logFileName = new StringBuilder();

        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));

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

        logFileName.append(" ").append(date);

        System.out.println(logFileName);

        System.setProperty("logfile.name", logFileName.toString());
    }
}
