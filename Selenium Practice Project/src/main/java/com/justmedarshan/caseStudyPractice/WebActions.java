package com.justmedarshan.caseStudyPractice;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;


public class WebActions {

    private static ArrayList<String> registerationIds = new ArrayList<String>(){{
        add("customer.firstName");
        add("customer.lastName");
        add("customer.address.street");
        add("customer.address.city");
        add("customer.address.state");
        add("customer.address.zipCode");
        add("customer.phoneNumber");
        add("customer.ssn");
        add("customer.username");
        add("customer.password");
        add("repeatedPassword");
    }};


    public static WebDriver initiateDriver(WebDriver driver, String driverType){

        switch(driverType){

            case "chrome":
                driver = new ChromeDriver();
                break;
            case "firefox":
                driver = new FirefoxDriver();
                break;
            case "edge":
                driver = new EdgeDriver();
                break;
        }
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        return driver;
    }


    public static boolean login(WebDriver usedDriver, String username, String password, String firstName){


        usedDriver.findElement(By.name("username")).sendKeys(username);
        usedDriver.findElement(By.name("password")).sendKeys(password);
        Reporting.setTestStepStatus("PASS", "Login Form filled", Reporting.captureScreen(usedDriver, "Login screen filled"));
        usedDriver.findElement(By.xpath("//input[@value = 'Log In']")).click();
        String welcomePanel = usedDriver.findElement(By.xpath("//div[@id = 'leftPanel']/p")).getText();
        if(usedDriver.getTitle().equals("ParaBank | Accounts Overview") &&
                (welcomePanel.contains(firstName) || welcomePanel.contains(username)))
            return true;
        return false;
    }

    public static boolean registerUser(WebDriver usedDriver, ArrayList<String> regData){

        usedDriver.findElement(By.linkText("Register")).click();
        for(int i = 0; i < registerationIds.size(); i++){

            //System.out.println("Element id accesed at index " + i + " :" + registerationIds.get(i) + " :: Data : " + regData.get(i));
            if(registerationIds.get(i).equals("repeatedPassword"))

                usedDriver.findElement(By.id(registerationIds.get(i))).sendKeys(regData.get(i - 1));
            else

                usedDriver.findElement(By.id(registerationIds.get(i))).sendKeys(regData.get(i));
        }
        Reporting.setTestStepStatus("PASS", "Filled Registration Form", Reporting.captureScreen(usedDriver, "FilledRegistrationPageOf_" + regData.get(8)));
        usedDriver.findElement(By.xpath("//input[@value='Register']")).click();
        if(usedDriver.findElement(By.xpath("//div[@id='rightPanel']/h1")).getText().contains(regData.get(8)) &&
                usedDriver.findElement(By.xpath("//div[@id='rightPanel']/p")).getText().equals("Your account was created successfully. You are now logged in.") &&
                usedDriver.findElement(By.xpath("//div[@id='leftPanel']/p")).getText().contains(regData.get(0) + " " + regData.get(1)))
            return true;

        return false;
    }

    public static String openNewAccount(WebDriver usedDriver, String accountType) {

        String accountNo = null;
        usedDriver.findElement(By.linkText("Open New Account")).click();
        if(usedDriver.getTitle().equals("ParaBank | Open Account")){

            Reporting.setTestStepStatus("PASS", "Navigation To Account Open Page",Reporting.captureScreen(usedDriver, "NewAccountOpenPage"));
            Select dropdown = new Select(usedDriver.findElement(By.id("type")));
            dropdown.selectByVisibleText(accountType);
            Reporting.setTestStepStatus("PASS", "Selected  "+accountType+" as account type",Reporting.captureScreen(usedDriver, "AfterSelectingAccountType"));
            WebDriverWait wait = new WebDriverWait(usedDriver, Duration.ofSeconds(3));
            wait.until(ExpectedConditions.presenceOfNestedElementLocatedBy(By.id("fromAccountId"), By.tagName("option")));
            Reporting.setTestStepStatus("PASS", "Selected a base account to transfer money"
                    ,Reporting.captureScreen(usedDriver, "AfterSelectingSourceAccount"));
            //visibilityOf(usedDriver.findElement(By.xpath("//div[@id='rightPanel']/div/div/h1")))
            usedDriver.findElement(By.xpath("//input[@value = 'Open New Account']")).click();
            Reporting.setTestStepStatus("INFO", "After clicking open new account button"
                    ,Reporting.captureScreen(usedDriver, "AfterClickingOpenAccountButton"));
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Reporting.setTestStepStatus("INFO", "After wait"
                    ,Reporting.captureScreen(usedDriver, "AfterExplicitWait"));
            if(usedDriver.findElement(By.xpath("//div[@id='rightPanel']/div/div/h1")).getText().equals("Account Opened!") &&
                    usedDriver.findElement(By.xpath("//div[@id='rightPanel']/div/div/p")).getText().equals("Congratulations, your account is now open.")){

                Reporting.setTestStepStatus("PASS", "New Account creation successful ",Reporting.captureScreen(usedDriver, "NewAccountCreated"));
                accountNo = usedDriver.findElement(By.id("newAccountId")).getText();
                // System.out.println("Inside if -> " + accountNo);
            }
        }
        else {
            //System.out.println("Inside else -> " + accountNo);
            Reporting.setTestStepStatus("FAIL", "Failed_To get the account Number", Reporting.captureScreen(usedDriver, "Failed_CurrentScreen"));
        }
        return accountNo;
    }

    public static boolean validateCreatedAccount(WebDriver usedDriver, String accountNo){

        usedDriver.findElement(By.linkText("Accounts Overview")).click();
        Reporting.setTestStepStatus("PASS", "Accounts overview page with accounts table", Reporting.captureScreen(usedDriver, "AccountsOverviewPage"));
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        List<WebElement> tableData = usedDriver.findElements(By.xpath("//table[@id='accountTable']//td[1]"));
        for(WebElement element : tableData){

            System.out.println(element.getText());
            if(element.getText().equals(accountNo))
                return true;
        }
        return false;
    }

    public static boolean logout(WebDriver usedDriver){

        usedDriver.findElement(By.linkText("Log Out")).click();
        if(usedDriver.getTitle().equals("ParaBank | Welcome | Online Banking"))
            return true;
        return false;
    }
}
