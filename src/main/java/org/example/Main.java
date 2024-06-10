package org.example;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.FileInputStream;
import java.time.Duration;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        WebDriver driver = new FirefoxDriver();

        //Load Courses Page
        driver.get("https://home.cunyfirst.cuny.edu/psp/cnyihprd/EMPLOYEE/SA/c/SCC_ADMIN_OVRD_STDNT.SSS_STUDENT_CENTER.GBL");

        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(1000));

        WebElement mouse = driver.findElement(By.xpath("//*[@id='CUNYfirstUsernameH']"));
        mouse.sendKeys("omari.lyn02");

        mouse = driver.findElement(By.xpath("//*[@id='CUNYfirstPassword']"));
        mouse.sendKeys("Dissidia97!");

        mouse = driver.findElement(By.xpath("//*[@id='submit']"));
        mouse.click();

        automation(driver);

    }

    private static void automation(WebDriver driver) {
        try{
            FileInputStream file = new FileInputStream("C:\\Users\\lynom\\Desktop\\Java Projects\\RE-Selenium\\TestSelenium\\src\\main\\resources\\Book1.xlsx");
        }catch(Exception e){

        }
    }
}