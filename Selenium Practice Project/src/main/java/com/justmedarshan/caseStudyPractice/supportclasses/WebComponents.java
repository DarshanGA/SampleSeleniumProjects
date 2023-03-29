package com.justmedarshan.caseStudyPractice.supportclasses;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;


public class WebComponents {

    public static String getRegisterationFormIds(int index) {


        String returnString = null;
        switch (index) {
            case 0:
                returnString = "customer.firstName";
                break;
            case 1:
                returnString = "customer.lastName";
                break;
            case 2:
                returnString = "customer.address.street";
                break;
            case 3:
                returnString = "customer.address.city";
                break;
            case 4:
                returnString = "customer.address.state";
                break;
            case 5:
                returnString = "customer.address.zipCode";
                break;
            case 6:
                returnString = "customer.phoneNumber";
                break;
            case 7:
                returnString = "customer.ssn";
                break;
            case 8:
                returnString = "customer.username";
                break;
            case 9:
                returnString = "customer.password";
                break;
            case 10:
                returnString = "repeatedPassword";
                break;
        }
        return returnString;
    }

    public static void waitTillOptionsLoad(WebDriver givenDriver, int waitingTime){

        WebDriverWait wait = new WebDriverWait(givenDriver, Duration.ofSeconds(waitingTime));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//select[@id = 'fromAccountId']//option")));
    }

    public static void waitTillSuccessfulAccountCreation(WebDriver givenDriver, int waitingTime){

        WebDriverWait wait = new WebDriverWait(givenDriver, Duration.ofSeconds(waitingTime));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='rightPanel']/div/div/p")));
    }
}
