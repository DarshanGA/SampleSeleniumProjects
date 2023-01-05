package com.justmedarshan.practicePackage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.time.Duration;
import java.util.ArrayList;


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

    public static String openNewAccount(WebDriver usedDriver, String accountType){

        //usedDriver.findElement();
        return "";
    }

    public static boolean logout(WebDriver usedDriver){

        usedDriver.findElement(By.linkText("Log Out")).click();
        if(usedDriver.getTitle().equals("ParaBank | Welcome | Online Banking"))
            return true;
        return false;
    }
}
