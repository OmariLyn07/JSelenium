package org.example;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.time.Duration;
import java.util.*;
import java.io.File;

public class Main {

    public static void main(String[] args) {

        WebDriver driver = new FirefoxDriver();

        //Load Login Page
        driver.get("https://home.cunyfirst.cuny.edu/psp/cnyihprd/EMPLOYEE/SA/c/SCC_ADMIN_OVRD_STDNT.SSS_STUDENT_CENTER.GBL");
        driver.manage().window().maximize();

        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(1000));

        //Enter Login Credentials
        WebElement mouse = driver.findElement(By.xpath("//*[@id='CUNYfirstUsernameH']"));
        mouse.sendKeys("Insert UserName");

        mouse = driver.findElement(By.xpath("//*[@id='CUNYfirstPassword']"));
        mouse.sendKeys("Insert Password");

        driver.findElement(By.xpath("//*[@id='submit']")).click();

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1000));

        automation(driver);

    }

    private static void automation(WebDriver driver) {
        String Lname = "";
        String Fname = "";
        String DOB = "";
        WebElement elem;
        List<WebElement> elems;
        File output = new File("Path for output file");


        try{
            //Reads Excel file and starts from sheet 1
            FileInputStream file = new FileInputStream("Path for input file");
            XSSFWorkbook book = new XSSFWorkbook(file);
            XSSFSheet sheet = book.getSheetAt(3);

            FileWriter fos = new FileWriter(output);

            //Iterates through each row one by one
            Iterator<Row> rowIterator = sheet.iterator();

            while (rowIterator.hasNext()) {
                driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
                Row row = rowIterator.next();
                System.out.println("Getting new Row");

                //For each row iterate through all columns
                Iterator<Cell> cellIterator = row.cellIterator();
                System.out.println("Getting row Cell values");
                Lname = cellIterator.next().getStringCellValue();
                Fname = cellIterator.next().getStringCellValue();
                DOB = cellIterator.next().getStringCellValue();

                //Inserts fetched Name into search keys and locates the student via DOB matching
                System.out.println("Entering Credentials");
                driver.switchTo().frame(0);
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
                try{
                    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("STDNT_SRCH_LAST_NAME_SRCH")));
                }
                catch(Exception e){
                    System.out.println("Waiting for last name visibility error\n" + e);
                    driver.navigate().refresh();
                }


                driver.findElement(By.id("STDNT_SRCH_LAST_NAME_SRCH")).sendKeys(Lname);
                driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

                driver.findElement(By.id("STDNT_SRCH_FIRST_NAME_SRCH")).sendKeys(Fname, Keys.ENTER);
                driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));


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

                    if(hash.get(DOB) == null){
                        fos.write("NA\n");
                        fos.flush();
                    }
                    else{
                        fos.write(hash.get(DOB) + " " + Fname + " " + Lname + "\n");
                        fos.flush();
                    }

                    driver.findElement(By.id("PTS_CFG_CL_WRK_PTS_SRCH_CLEAR")).click();
                    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
                    driver.navigate().refresh();
                    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
                }catch(Exception e){
                    System.out.println("No Results Error\n" + e);
                    driver.navigate().refresh();
                    fos.write("\n");
                    fos.flush();


                }


            }
            fos.close();
            driver.close();
            book.close();
            file.close();

        }catch(Exception e){
            System.out.println("Miscellaneous Error\n"+e);
            driver.navigate().refresh();

        }

    }

    public void ViewCheck(WebDriver driver){
        WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(1));
        WebElement check = wait2.until(ExpectedConditions.elementToBeClickable(By.id("PTS_CFG_CL_STD_RSL$hviewall$0")));
        check.click();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    }
}