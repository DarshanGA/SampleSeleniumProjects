package com.justmedarshan.caseStudyPractice;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

@Listeners(CustomListner.class)
public class AccountCreationValidation {

    private static WebDriver driver;
    private static String parabankUrl = "https://parabank.parasoft.com/parabank/index.htm", validUsername, validPassword, validFirstname, newAccountNo = null;

    @BeforeTest
    public static void initiateTest(){

        driver = WebActions.initiateDriver(driver, "chrome");
        driver.get(parabankUrl);
        Reporting.startReport();
        Reporting.createTestCase("TC_01_Account Creation and its validation using Chrome driver",
                Reporting.captureScreen(driver,"ParabankHomePage"));

    }

    @AfterTest
    private static void endTest(){

        Reporting.flush();
        driver.quit();
    }

    @Test(priority = 0)
    public static void testRegestration(){

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HHmmss");
        LocalDateTime now = LocalDateTime.now();
        String time = dtf.format(now);
        ArrayList<String> inputData = new ArrayList<String>(){{
            add("Test");
            add("Data_" + time);
            add("starbucks street, 7th cross");
            add("New york");
            add("Color state");
            add("099432");
            add("3243562344");
            add("234536");
            add("testdata_" + time);
            add("testdata@" + time);
        }};
        Assert.assertTrue(WebActions.registerUser(driver, inputData));
        validUsername = inputData.get(8);
        validPassword = inputData.get(9);
        validFirstname = inputData.get(0);
        Reporting.setTestStepStatus("PASS","Registeration Succesful",Reporting.captureScreen(driver, "SuccesfulRegisterationPage"));
    }

    @Test(priority = 1)
    public static void testLogout(){

        Assert.assertTrue(WebActions.logout(driver));
        Reporting.setTestStepStatus("PASS","Succesfully LogedOUt", Reporting.captureScreen(driver, "AfterLoggingOut"));

    }

    @Test(priority = 2)
    public static void testLogin(){

        Assert.assertTrue(WebActions.login(driver, validUsername , validPassword, validFirstname));
        Reporting.setTestStepStatus("PASS", "User_" + driver.findElement(By.xpath("//div[@id = 'leftPanel']/p")).getText().split("Welcome")[1]
        + "_loginSuccessful", Reporting.captureScreen(driver, "LoginSuccesful"));
    }

    @Test(priority = 3)
    public static void testAccountCreation(){


        newAccountNo = WebActions.openNewAccount(driver, "SAVINGS");
        Assert.assertNotNull(newAccountNo);
        System.out.println(newAccountNo);
        Assert.assertTrue(WebActions.validateCreatedAccount(driver, newAccountNo));
        Reporting.setTestStepStatus("PASS", "Created Account is validated", Reporting.captureScreen(driver, "AccountValidationSuccessful"));

    }

    public static WebDriver getDriver() {
        return driver;
    }
}
