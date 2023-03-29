package com.justmedarshan.seleniumcasestudy;

import com.justmedarshan.caseStudyPractice.supportclasses.Reporting;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;

@Listeners (CustomListner.class )
public class TestCreateNewAccountUsingChrome extends CommonTestSteps{

    /* This before test method sets browser name and initializes the specified web driver. It also starts the test case in Extent reports. */
    @BeforeTest
    public void testDriverInitialization(){

        super.setDriverName("Chrome");
        Reporting.startReport();
        Reporting.createTestCase("TC001_Test \"Create_New_Bank_Account\" model using Chrome browser.", "..//Reports//Screenshots//BrowserLogos//ChromeBrowserLogo.png");
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--remote-allow-origins=*");
        WebDriver driver = new ChromeDriver(chromeOptions);
        driver.manage().window().maximize();
        super.setDriver(driver);
    }

    /* This after test method quits the running web driver. */
    @AfterTest
    public void quitDriver(){

        Reporting.flush();
        super.getDriver().quit();
    }
}
