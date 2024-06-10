package org.example;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.FormulaEvaluator;

import java.io.FileInputStream;
import java.time.Duration;
import java.util.*;

public class Main {

    public static void main(String[] args) {

        WebDriver driver = new FirefoxDriver();

        //Load Login Page
        driver.get("https://home.cunyfirst.cuny.edu/psp/cnyihprd/EMPLOYEE/SA/c/SCC_ADMIN_OVRD_STDNT.SSS_STUDENT_CENTER.GBL");

        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(1000));

        //Enter Login Credentials
        WebElement mouse = driver.findElement(By.xpath("//*[@id='CUNYfirstUsernameH']"));
        mouse.sendKeys("omari.lyn02");

        mouse = driver.findElement(By.xpath("//*[@id='CUNYfirstPassword']"));
        mouse.sendKeys("Dissidia97!");

        driver.findElement(By.xpath("//*[@id='submit']")).click();

        try{
            driver.manage().timeouts().implicitlyWait(Duration.ofMillis(5000));
            driver.findElement(By.cssSelector(""));


        }catch(Exception c){
            System.out.println(c);

        }

        //automation(driver);

    }

    private static void automation(WebDriver driver) {
        String Lname = "";
        String Fname = "";
        String DOB = "";
        try{
            FileInputStream file = new FileInputStream("C:/Users/lynom/Desktop/Java Projects/RE-Selenium/TestSelenium/src/main/resources/Book1.xlsx");
            XSSFWorkbook book = new XSSFWorkbook(file);
            XSSFSheet sheet = book.getSheetAt(0);

            //Iterates through each row one by one
            Iterator<Row> rowIterator = sheet.iterator();

            while (rowIterator.hasNext()) {

                Row row = rowIterator.next();

                System.out.println(row.getPhysicalNumberOfCells());
                if(row.getRowNum() == 0){
                    continue;
                }

                //For each row iterate through all columns
                Iterator<Cell> cellIterator = row.cellIterator();

                while(cellIterator.hasNext()){

                    Lname = cellIterator.next().getStringCellValue();
                    System.out.print(Lname + " ");
                    Fname = cellIterator.next().getStringCellValue();
                    System.out.print(Fname + " ");
                    DOB = cellIterator.next().getStringCellValue();
                    System.out.print(DOB + " ");
                    System.out.println();

                    System.out.println(Lname + " " + Fname + " " + DOB);

                    break;

                }
                driver.manage().timeouts().implicitlyWait(Duration.ofMillis(1000));
                driver.findElement(By.name("STDNT_SRCH_LAST_NAME_SRCH")).sendKeys(Lname);
                driver.findElement(By.name("STDNT_SRCH_LAST_NAME_SRCH")).sendKeys(Fname);

            }

        }catch(Exception e){
            System.out.println(e);


        }
    }
}