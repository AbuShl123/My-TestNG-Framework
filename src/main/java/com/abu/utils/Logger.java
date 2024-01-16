package com.abu.utils;

import com.abu.framework.ExtentManager;
import com.abu.framework.TestBase;
import com.aventstack.extentreports.Status;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Logger {

    private Logger() {
    }

    static final private
        org.apache.logging.log4j.Logger logger = LogManager.getLogger("defaultLogger");

    public static synchronized void log(Object details) {
        if (!TestBase.getCurrentTest().loggable) return;
        String message = updateMessage(getString(details));

        logger.info(message);

        ExtentManager.getTest().log(Status.INFO, message);
    }

    public static synchronized void log(Level level, Object details) {
        if (!TestBase.getCurrentTest().loggable) return;
        String message = updateMessage(getString(details));

        logger.log(level, message);

        ExtentManager.getTest().log(getExtentStatus(level), message);
    }

    public static synchronized void consoleLog(Object details) {
        String message = updateMessage(getString(details));
        logger.info(message);
    }

    public static synchronized void consoleLog(Level level, Object details) {
        String message = updateMessage(getString(details));
        logger.log(level, message);
    }

    private static String updateMessage(String message) {
        return String.format("%s - %s", Thread.currentThread().getStackTrace()[3].getMethodName(), message);
    }

    private static Status getExtentStatus(Level level) {
        var levelStatus = level.getStandardLevel();

        switch (levelStatus) {
            case INFO: return Status.INFO;
            case ERROR: return Status.ERROR;
            case DEBUG: return Status.DEBUG;
            case WARN: return Status.WARNING;
            case FATAL: return Status.FATAL;
            default: throw new IllegalArgumentException("Provided level is not supported: " + levelStatus);
        }
    }

    private static String removeColors(String message) {
        if (message.matches(".*<.*>.*</.*>.*")) {
            Pattern pattern = Pattern.compile("<[^>]*>");
            Matcher matcher = pattern.matcher(message);

            return matcher.replaceAll("");
        }
        return message;
    }

    private static String getString(Object o) {
        if (o instanceof Number) return String.valueOf(o);
        if (o instanceof String) return (String) o;
        return o.toString();
    }
}
