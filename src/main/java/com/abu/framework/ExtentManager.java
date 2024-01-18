package com.abu.framework;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.testng.ITestContext;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class ExtentManager {
    private static ExtentHtmlReporter reporter;
    private static ExtentReports extent;
    private static final Map<Integer, ExtentTest> extentTestMap = new HashMap<>();

    public synchronized static ExtentTest getTest() {
        return extentTestMap.get((int) Thread.currentThread().getId());
    }

    private static void setInstance(ITestContext ctx) {
        if (reporter == null) {
            String reportName = getReportName(ctx);
            reporter = new ExtentHtmlReporter(FrameworkConstants.REPORT_DIRECTORY + reportName);
            extent = new ExtentReports();
            extent.attachReporter(reporter);
            reporter.config().setDocumentTitle("Test Report");
            reporter.config().setTheme(Theme.STANDARD);
        }
    }

    static synchronized void startReport(ITestContext ctx, String testName) {
        setInstance(ctx);

        var test = extent.createTest(testName);
        extentTestMap.put((int) Thread.currentThread().getId(), test);
    }

    static synchronized void flush() {
        extent.flush();
    }

    private static String getReportName(ITestContext ctx) {
        StringBuilder reportName = new StringBuilder();
        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("MMM d"));
        reportName.append("Report from ").append(date).append(" for ");

        String suiteName = ctx.getSuite().getName();

        switch (suiteName) {
            default:
                reportName.append(TestBase.getCurrentTest().name.toUpperCase());
                break;

            case "Bulk Execution":
                reportName.append("regression run");
                break;

            case "Smoke":
                reportName.append("smoke run");
                break;
        }


        return reportName.append(".html").toString();
    }
}
