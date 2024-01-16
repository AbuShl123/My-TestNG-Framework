package com.abu.utils;

import com.abu.framework.ExtentManager;
import com.abu.framework.FrameworkConstants;
import com.abu.framework.TestBase;
import com.abu.selenium.Driver;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.MediaEntityModelProvider;
import com.aventstack.extentreports.Status;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Logger {

    private Logger() {
    }

    static final private
        org.apache.logging.log4j.Logger logger = LogManager.getLogger("defaultLogger");

    public static synchronized void log(Object details) {
        if (!TestBase.getCurrentTest().loggable) return;
        String message = getString(details);

        logger.info(updateMessage(removeColors(message)));

        ExtentManager.getTest().log(Status.INFO, message);
    }

    public static synchronized void log(Level level, Object details) {
        if (!TestBase.getCurrentTest().loggable) return;
        String message = getString(details);

        logger.log(level, updateMessage(removeColors(message)));

        ExtentManager.getTest().log(getExtentStatus(level), message);
    }

    public static synchronized void consoleLog(Object details) {
        String message = removeColors(updateMessage(getString(details)));
        logger.info(message);
    }

    public static synchronized void consoleLog(Level level, Object details) {
        String message = removeColors(updateMessage(getString(details)));
        logger.log(level, removeColors(message));
    }

    public static synchronized void logException(Throwable e) {
        logger.error("Exception occurred", e);

        StringBuilder sb = new StringBuilder()
                .append("Exception in thread [")
                .append(Thread.currentThread().getName())
                .append("] ")
                .append(e)
                .append("\n");

        for (StackTraceElement stackTraceElement : e.getStackTrace()) {
            sb.append("\tat ").append(stackTraceElement).append("\n");
        }

        String exceptionStackTrace = String.format("<span style=\"color: red\">%s</span>",  sb);

        captureScreenshot(exceptionStackTrace);
    }

    private static void captureScreenshot(String message) {
        // TC001 1-17-2024
        var date = LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        String filePath = FrameworkConstants.SCREENSHOTS_DIRECTORY + TestBase.getCurrentTest().name.toUpperCase() + " " + date + ".png";

        File screenshot = ((TakesScreenshot) Driver.getDriver()).getScreenshotAs(OutputType.FILE);

        try {
            FileUtils.copyFile(screenshot, new File(filePath));
            ExtentManager.getTest().fail(message, MediaEntityBuilder.createScreenCaptureFromPath(filePath).build());
        } catch (IOException e) {
            Logger.log(Level.WARN, "Cannot attach screenshot");
            e.printStackTrace();
        }
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

    private static String updateMessage(String message) {
        return String.format("%s - %s", Thread.currentThread().getStackTrace()[3].getMethodName(), message);
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
