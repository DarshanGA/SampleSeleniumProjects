package com.justmedarshan.seleniumcasestudy;

import com.justmedarshan.caseStudyPractice.supportclasses.ExcelOperations;
import com.justmedarshan.caseStudyPractice.supportclasses.ParabankUser;
import com.justmedarshan.caseStudyPractice.supportclasses.WebComponents;
import com.justmedarshan.caseStudyPractice.supportclasses.Reporting;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class CommonTestSteps {

    private static WebDriver driver = null;
    private static String url = "https://parabank.parasoft.com/parabank/index.htm", driverName = null;
    private static ParabankUser registeredUser = new ParabankUser();
    private static ExcelOperations excelOperationsObject;

    public static String getDriverName() {
        return driverName;
    }

    public static void setDriverName(String driverName) {
        CommonTestSteps.driverName = driverName;
    }

    public static WebDriver getDriver() {
        return driver;
    }

    public static void setDriver(WebDriver driver) {
        CommonTestSteps.driver = driver;
    }

    /* This method tests whether a driver is initialized or not. */
    @Test(priority = 0)
    public void testInitiatedDriver(){

        Assert.assertNotNull(driver);
    }
    /* This method test the navigation to 'Parabank url', it verifies whether driver is navigated to proved url or not. This also update the same status in Extent report. */
    @Test(priority = 1)
    public void testNavigationToUrl(){

        driver.get(url);
        String navigatedUrl = driver.getCurrentUrl();
        Assert.assertEquals(navigatedUrl, url);
        try {
            Reporting.setTestStepStatus("PASS", "Navigated to parabank HomePage.",Reporting.captureScreen(driver,driverName + "_NavigatedToUrl"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    /* This method test the navigation to Registration page from home page, in parabank site. This also updates the status onto the Extent report. */
    @Test(priority = 2)
    public void testNavigationToRegistrationPage() throws IOException {

        Reporting.setTestStepStatus("INFO", "2.2.1 User Registeration.");
        driver.findElement(By.linkText("Register")).click();
        Assert.assertTrue(driver.getTitle().contains("Register"));
        Reporting.setTestStepStatus("PASS", "Navigated to \"Registeration\" page.", Reporting.captureScreen(driver,driverName + "_NavigatedToRegisterationPage"));
    }
    /* This method test the entire user Registeration processes. First it tests the registeration filling action and after which it tests for successful registeration.
    * This method extracts the data from provided excel data sheet 'DataSheet - [In Use].xlsx' found under 'TestData -> DataSheet - [In Use].xlsx' folder.
    * It also stores the registered user after successful registeration which is used in later test methods. */
    @Test(priority = 3)
    public void testUserRegisteration(){

        ParabankUser retrievedUser;
        try {
            excelOperationsObject = new ExcelOperations(System.getProperty("user.dir") + "//TestData//DataSheet - [In Use].xlsx", "Registration information");
            retrievedUser = excelOperationsObject.getNextRowData();
            for(int i = 0; i < 11; i++){

                if(i == 10)
                    driver.findElement(By.id(WebComponents.getRegisterationFormIds(i))).sendKeys(retrievedUser.getUserDetails(i - 1));
                else
                    driver.findElement(By.id(WebComponents.getRegisterationFormIds(i))).sendKeys(retrievedUser.getUserDetails(i));
            }
            Reporting.setTestStepStatus("PASS", "Filled User Registeration form", Reporting.captureScreen(driver,driverName + "_FilledRegisterationForm"));
            driver.findElement(By.xpath("//input[@value='Register']")).click();
            Assert.assertTrue(driver.findElement(By.xpath("//div[@id='rightPanel']/h1")).getText().contains(retrievedUser.getUsername())
                                && driver.findElement(By.xpath("//div[@id='rightPanel']/p")).getText().equals("Your account was created successfully. You are now logged in.")
                                && driver.findElement(By.xpath("//div[@id='leftPanel']/p")).getText().contains(retrievedUser.getFirstname() + " " + retrievedUser.getLastname()));
            Reporting.setTestStepStatus("PASS", "User '" + retrievedUser.getFirstname() + " " +retrievedUser.getLastname() + "' Registered Successfully and Logged in by default",
                    Reporting.captureScreen(driver, driverName + "_RegisterationSuccessful"));
            registeredUser = retrievedUser;
            System.out.println("Registered user: " + registeredUser.getFirstname() + " " + registeredUser.getLastname() + " \nUser name: " + registeredUser.getUsername()
            + "\nPassword: " + retrievedUser.getPassword() + "\npassword length: " + retrievedUser.getPassword().length());

        } catch (IOException e) {
            e.printStackTrace();

        }
    }
    /* 'testLogout' as the name implies, it tests the logout action which is done after successful  registration.
    In Parabank site, the user get logged in by default after successful registeration. But the requirement also says that user has to log in with registered credentials, in order to achieve this user has to logout first.
    The result of this test is also updated in Extent report.*/
    @Test(priority = 4)
    public void testLogout(){

        try {

            driver.findElement(By.linkText("Log Out")).click();
            Assert.assertEquals(driver.getTitle(), "ParaBank | Welcome | Online Banking");
            Reporting.setTestStepStatus("PASS", "Logged out after Registeration and default login.", Reporting.captureScreen(driver,driverName + "_LoggedOUtAfterDefaultLogin"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /* This method is to test the user login feature and update the status in report, login is tested with registered 'username' and 'password'.*/
    @Test(priority = 5)
    public void testRegisteredUserLogin(){

        /*registeredUser.setUsername("jamesbond");
        registeredUser.setPassword("jamesbond@007");
        registeredUser.setFirstname("James");
        registeredUser.setLastname("Bond");*/
        Reporting.setTestStepStatus("INFO", "2.2.2 User login.");
        driver.findElement(By.name("username")).sendKeys(registeredUser.getUsername());
        driver.findElement(By.name("password")).sendKeys(registeredUser.getPassword());
        try {

            Reporting.setTestStepStatus("PASS", "Filled Login form.", Reporting.captureScreen(driver,driverName + "_FilledLoginForm"));
            driver.findElement(By.xpath("//input[@value = 'Log In']")).click();
            Assert.assertEquals(driver.getTitle(), "ParaBank | Accounts Overview");
            Assert.assertTrue(driver.findElement(By.xpath("//div[@id = 'leftPanel']/p")).getText().contains(registeredUser.getFirstname() + " " + registeredUser.getLastname()));
            Reporting.setTestStepStatus("PASS", "User '" + registeredUser.getFirstname() + " " + registeredUser.getLastname() + "' successfully logged in. ",
                    Reporting.captureScreen(driver,driverName + "_UserLoginSuccessful"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /* This method is to test the feature which allows a user to create an account in parabank site. This tests the creation of new saving account by selecting a 'From' account to debit minimum balance into the newly created account.
    * Once the savings account is created successfully, this method also stores the created account number in the same Excel sheet. The account number is stored in 'DataSheet - '[In Use].xlsx' excel file,
    * it is store in 'User Account Info' sheet of given excel against the user's name.  */
    @Test(priority = 6)
    public void testOpenNewAccount(){

        Reporting.setTestStepStatus("INFO", "2.2.3 User Opens Account.");
        driver.findElement(By.linkText("Open New Account")).click();
        Assert.assertTrue(driver.getTitle().equals("ParaBank | Open Account"));
        try {
            Reporting.setTestStepStatus("PASS", "Navigated to New account open page.", Reporting.captureScreen(driver,driverName + "_NavigatedToOpenAccountPage"));
            Select accountTypeDropdown = new Select(driver.findElement(By.id("type")));
            accountTypeDropdown.selectByVisibleText("SAVINGS");
            Select fromAccount = new Select(driver.findElement(By.id("fromAccountId")));
            WebComponents.waitTillOptionsLoad(driver,2);
            fromAccount.selectByIndex(0);
            Reporting.setTestStepStatus("PASS", "Selected 'SAVINGS' account type and a from account to transfer money from.",
                    Reporting.captureScreen(driver,driverName + "_SelectedAccountTypeFromAccount"));
            driver.findElement(By.xpath("//input[@value = 'Open New Account']")).click();
            WebComponents.waitTillSuccessfulAccountCreation(driver, 2);
            Reporting.setTestStepStatus("INFO", "2.2.3 User Registeration.");
            Assert.assertEquals(driver.findElement(By.xpath("//div[@id='rightPanel']/div/div/p")).getText(), "Congratulations, your account is now open.");
             registeredUser.setCreatedAccountNumber(Integer.parseInt(driver.findElement(By.id("newAccountId")).getText()));
            Reporting.setTestStepStatus("PASS", "Savings Account " + registeredUser.getCreatedAccountNumber() + " created successfully",
                    Reporting.captureScreen(driver, driverName + "_AccountCreatedSuccessfully"));
            excelOperationsObject = new ExcelOperations(System.getProperty("user.dir") + "//TestData//DataSheet - [In Use].xlsx", "Registeration Information");
            Assert.assertTrue(excelOperationsObject.locateUserNameAndWriteAccountNo("User Account Info", registeredUser.getFirstname(),
                    registeredUser.getLastname(), registeredUser.getCreatedAccountNumber()));

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
