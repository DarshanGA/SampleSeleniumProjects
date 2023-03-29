package com.justmedarshan.caseStudyPractice.supportclasses;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;

public class Reporting {

    private static ExtentSparkReporter sparkReport = new ExtentSparkReporter(System.getProperty("user.dir") + "\\Reports\\TestExecutionReports.html");
    private static ExtentReports reports;
    private static ExtentTest test;
    /* Below method captures the screenshot of running web driver in current state and stores that in 'Reports -> Screenshots' folder. */
    public static String captureScreen(WebDriver usedDriver, String filename) throws IOException {

        File captured = ((TakesScreenshot)usedDriver).getScreenshotAs(OutputType.FILE);
        File saved = new File((System.getProperty("user.dir") + "//Reports//Screenshots//") + filename + ".jpg");
        FileUtils.copyFile(captured, saved);
        return saved.getAbsolutePath();
    }
    /* This method starts the 'Extent Report'. This is external and popular reporting tool for selenium which shows detailed reports with graphics and charts as HTML file.
     This Extent report HTML file can be found in 'Reports -> TestExecutionReports.html' location of project folder structure.*/
    public static void startReport(){

        reports = new ExtentReports();
        reports.attachReporter(sparkReport);
    }

    /* Below method sets the test step status in extent report. This also attaches the captured screenshot to the steps in report. */
    public static void setTestStepStatus(String status, String message, String path) {

        switch(status){

            case "INFO": test.log(Status.INFO, message, MediaEntityBuilder.createScreenCaptureFromPath(path).build());
                break;
            case "PASS": test.log(Status.PASS, message, MediaEntityBuilder.createScreenCaptureFromPath(path).build());
                break;
            case "FAIL": test.log(Status.FAIL, message, MediaEntityBuilder.createScreenCaptureFromPath(path).build());
                break;
        }
    }
    /* Below method is overloaded version of the above method, this adds only the steps without attaching the screenshot to report */
    public static void setTestStepStatus(String status, String message) {

        switch(status){

            case "INFO": test.log(Status.INFO, message);
                break;
            case "PASS": test.log(Status.PASS, message);
                break;
            case "FAIL": test.log(Status.FAIL, message);
                break;
        }
    }
    /* This method creates a test case with given name and attaches a image to the same. */
    public static void createTestCase(String testName, String path){

        test = reports.createTest(testName).addScreenCaptureFromPath(path);
    }

    public static void flush(){

        reports.flush();
    }

}
