package com.mahmoud.ui;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class BrowserDriver {
    private static WebDriver driver;

    private BrowserDriver(){}

    public static WebDriver getDriver()
    {
        if(driver == null)
        {
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
        }

        return driver;
    }

    public static boolean closeBrowser() {
        try {
            getDriver().close();
            return true;
        }
        catch (Exception ignore) {
            return false;
        }
    }

    public static WebDriver goTo(String url)
    {
        WebDriver webDriver = getDriver();
        webDriver.get(url);
        return driver;
    }

    public static WebElement findElementByID(String id) {
        WebDriver webDriver = getDriver();
        return webDriver.findElement(By.id(id));
    }

    public static WebElement findElementByXPath(String xpath) {
        WebDriver webDriver = getDriver();
        return webDriver.findElement(By.xpath(xpath));
    }
}
