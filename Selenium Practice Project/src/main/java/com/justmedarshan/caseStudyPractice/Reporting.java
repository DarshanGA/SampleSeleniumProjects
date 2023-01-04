package com.justmedarshan.caseStudyPractice;

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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Logger;

public class Reporting {

    private static ExtentSparkReporter sparkReport = new ExtentSparkReporter("C:\\Users\\darsh\\Downloads\\Selenium files\\Selenium Practice Project\\Reports\\TestExecutionReports.html");
    private static ExtentReports reports;
    private static ExtentTest test;

    public static String captureScreen(WebDriver usedDriver, String filename){

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH_mm_ss");
        LocalDateTime now = LocalDateTime.now();
        String time = dtf.format(now);
        File captured = ((TakesScreenshot)usedDriver).getScreenshotAs(OutputType.FILE);
        File saved = new File("C:\\Users\\darsh\\Downloads\\Selenium files\\Selenium Practice Project\\Reports\\Screenshots\\" + filename + "_T" + time + ".jpg");
        try {
            FileUtils.copyFile(captured, saved);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return saved.getAbsolutePath();
    }

    public static void startReport(){

        reports = new ExtentReports();
        reports.attachReporter(sparkReport);
    }


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

    public static void createTestCase(String testName, String path){

        test = reports.createTest(testName).addScreenCaptureFromPath(path);
    }

    public static void flush(){

        reports.flush();
    }
}
