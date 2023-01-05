package com.justmedarshan.practicePackage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.ArrayList;

@Listeners(CustomListners.class)
public class AccountCreationValidation {

    private static WebDriver driver;
    private static String parabankUrl = "https://parabank.parasoft.com/parabank/index.htm", validUsername, validPassword, validFirstname;

    @BeforeTest
    public static void initiateTest(){

        driver = WebActions.initiateDriver(driver, "chrome");
        driver.get(parabankUrl);
        Reporting.startReport();
        Reporting.createTestCase("TC_01_Account Creation and its validation using Chrome driver",
                Reporting.captureScreen(driver,"ParabankHomePage"));

    }

    @Test(priority = 0)
    public static void testRegestration(){

        /*ArrayList<String> inputData = new ArrayList<String>(){{
            add("James");
            add("Bond");
            add("starbucks street, 7th cross");
            add("New york");
            add("Color state");
            add("099432");
            add("3243562344");
            add("234536");
            add("jamesbond");
            add("jamesbond@007");
        }};*/
        ArrayList<String> inputData = new ArrayList<String>(){{
            add("Test");
            add("Data_5");
            add("starbucks street, 7th cross");
            add("New york");
            add("Color state");
            add("099432");
            add("3243562344");
            add("234536");
            add("testdata_5");
            add("testdata_5@123");
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


    @AfterTest
    private static void endTest(){

        Reporting.flush();
        driver.quit();
    }

    public static WebDriver getDriver() {
        return driver;
    }
}
