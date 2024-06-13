package org.example;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) {

        WebDriver driver = new FirefoxDriver();

        //Load Login Page
        driver.get("https://home.cunyfirst.cuny.edu/psp/cnyihprd/EMPLOYEE/SA/c/SCC_ADMIN_OVRD_STDNT.SSS_STUDENT_CENTER.GBL");
        driver.manage().window().maximize();

        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(1000));

        //Enter Login Credentials
        WebElement mouse = driver.findElement(By.xpath("//*[@id='CUNYfirstUsernameH']"));
        mouse.sendKeys("omari.lyn02");

        mouse = driver.findElement(By.xpath("//*[@id='CUNYfirstPassword']"));
        mouse.sendKeys("Dissidia97!");

        driver.findElement(By.xpath("//*[@id='submit']")).click();

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1000));

/*
        try{
            //Driver waits until "Student Services Center" is visible on screen
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#ptcp_pagetitle_spantext")));

            //Changes frame to the search table and student elements
            driver.switchTo().frame(0);
            driver.findElement(By.cssSelector("input[name='STDNT_SRCH_LAST_NAME_SRCH']")).sendKeys("Campbell");
            driver.findElement(By.cssSelector("input[name='STDNT_SRCH_FIRST_NAME_SRCH']")).sendKeys("Christine");
            driver.findElement(By.cssSelector("input[value='Search'][name='PTS_CFG_CL_WRK_PTS_SRCH_BTN']")).click();

            wait.until(ExpectedConditions.elementToBeClickable(By.id("win0divPTS_CFG_CL_STD_RSLGP$0")));

            //Test to Add elements to hashmap and grab the emplid using the DOB as a Key
            String dob = "08/25";
            String real_emp = "24579614";
            String emp = "";

            //Campbell Christine, 08/25, 24579614

            //Maps all student elements in table to a list via the tag tr in HTML
            WebElement elem = driver.findElement(By.id("tdgbrPTS_CFG_CL_STD_RSL$0"));
            List<WebElement> elems = elem.findElements(By.tagName("tr"));

            HashMap<String, String> hash = new HashMap<String, String>();
            int counter = 0;

            for(WebElement element : elems){
                hash.put(element.findElement(By.id("win0divPTS_CFG_CL_RSLT_NUI_SRCH3$14$$" + String.valueOf(counter))).getText(), element.findElement(By.id("win0divPTS_CFG_CL_RSLT_NUI_SRCH0$11$$" + String.valueOf(counter))).getText());
                counter++;
            }

            driver.findElement(By.id("PTS_CFG_CL_WRK_PTS_SRCH_CLEAR")).click();

            emp = hash.get(dob);
            System.out.println(emp + " real-> " + real_emp);
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
            driver.close();

        }
        catch(Exception e){
            System.out.println(e);
            driver.close();
        }

*/
        automation(driver);

    }

    private static void automation(WebDriver driver) {
        String Lname = "";
        String Fname = "";
        String DOB = "";
        String emp = "";
        WebElement elem;
        List<WebElement> elems;
        WebElement navi;
        try{
            //Reads Excel file and starts from sheet 1
            FileInputStream file = new FileInputStream("C:/Users/lynom/Desktop/Java Projects/RE-Selenium/TestSelenium/src/main/resources/Book1.xlsx");
            XSSFWorkbook book = new XSSFWorkbook(file);
            XSSFSheet sheet = book.getSheetAt(0);

            //Create new Excel file with EmplIDs


            //Iterates through each row one by one
            Iterator<Row> rowIterator = sheet.iterator();

            while (rowIterator.hasNext()) {

                driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));


                Row row = rowIterator.next();

                if(row.getRowNum() == 0){
                    continue;
                }

                System.out.println("Getting new Row");

                //For each row iterate through all columns
                Iterator<Cell> cellIterator = row.cellIterator();

                System.out.println("Getting row Cell values");
                Lname = cellIterator.next().getStringCellValue();
                Fname = cellIterator.next().getStringCellValue();
                DOB = cellIterator.next().getStringCellValue();

                System.out.println(Lname + " " + Fname + " " + DOB);




                //Inserts fetched Name into search keys and locates the student via DOB matching

                System.out.println("Entering Credentials");
                driver.switchTo().frame(0);
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
                try{
                    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("STDNT_SRCH_LAST_NAME_SRCH")));
                }
                catch(Exception e){
                    System.out.println(e);
                    driver.close();
                }


                driver.findElement(By.id("STDNT_SRCH_LAST_NAME_SRCH")).sendKeys(Lname);
                driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

                driver.findElement(By.id("STDNT_SRCH_FIRST_NAME_SRCH")).sendKeys(Fname, Keys.ENTER);
                driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
                //driver.findElement(By.cssSelector("input[value='Search'][name='PTS_CFG_CL_WRK_PTS_SRCH_BTN']")).click();
                try {
                    driver.findElement(By.id("win0divPTS_CFG_CL_STD_RSLGP$0")).click();
                    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
                }
                catch(Exception e){

                }

                try{
                    System.out.println("Hashing search results");
                    elem = driver.findElement(By.id("tdgbrPTS_CFG_CL_STD_RSL$0"));
                    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
                    elems = elem.findElements(By.tagName("tr"));
                    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

                    HashMap<String, String> hash = new HashMap<String, String>();
                    int counter = 0;

                    for(WebElement element : elems){
                        hash.put(element.findElement(By.id("win0divPTS_CFG_CL_RSLT_NUI_SRCH3$14$$" + String.valueOf(counter))).getText(), element.findElement(By.id("win0divPTS_CFG_CL_RSLT_NUI_SRCH0$11$$" + String.valueOf(counter))).getText());
                        counter++;
                    }
                    System.out.println(Lname + ", " + Fname + ": " + hash.get(DOB));

                    driver.findElement(By.id("PTS_CFG_CL_WRK_PTS_SRCH_CLEAR")).click();
                    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
                    driver.navigate().refresh();
                    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
                }catch(Exception e){
                    System.out.println(e);
                    driver.close();
                }


            }

        }catch(Exception e){
            System.out.println(e);
            driver.close();


        }
        driver.close();
    }
}