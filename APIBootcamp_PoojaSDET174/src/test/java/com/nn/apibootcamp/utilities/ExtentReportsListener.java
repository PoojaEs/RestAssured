package com.nn.apibootcamp.utilities;

import com.relevantcodes.extentreports.*;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestResult;
import org.testng.xml.XmlSuite;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class ExtentReportsListener implements ITestListener, ISuiteListener {

    private ExtentReports extent;
    private ExtentTest test;

    // This method is called when the suite starts
    @Override
    public void onStart(ISuite suite) {
        // Create a new report file at the specified location
        String reportPath = "test-output" + File.separator + "APIBootcamp_ExtentReportTestNG.html";
        extent = new ExtentReports(reportPath, true);
    }

    // This method is called when the suite finishes
    @Override
    public void onFinish(ISuite suite) {
        // Write the final output and close the report
        extent.flush();
        extent.close();
    }

    // This method is called when a test starts
    @Override
    public void onTestStart(ITestResult result) {
        test = extent.startTest(result.getMethod().getMethodName());
        test.setStartedTime(getTime(result.getStartMillis()));
    }

    // This method is called when a test succeeds
    @Override
    public void onTestSuccess(ITestResult result) {
        test.setEndedTime(getTime(result.getEndMillis()));
        test.log(LogStatus.PASS, "Test Passed");
        extent.endTest(test);
    }

    // This method is called when a test fails
    @Override
    public void onTestFailure(ITestResult result) {
        test.setEndedTime(getTime(result.getEndMillis()));
        test.log(LogStatus.FAIL, result.getThrowable().getMessage());
        extent.endTest(test);
    }

    // This method is called when a test is skipped
    @Override
    public void onTestSkipped(ITestResult result) {
        test.setEndedTime(getTime(result.getEndMillis()));
        test.log(LogStatus.SKIP, "Test Skipped");
        extent.endTest(test);
    }

    // This method is called when the test is finished
    @Override
    public void onFinish(ITestContext context) {
        // Handle finishing the context if needed (for example, reporting summary or suite data)
    }

    // Helper method to convert milliseconds to date/time
    private Date getTime(long millis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);
        return calendar.getTime();
    }
}
