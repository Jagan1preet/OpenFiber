package pageEvents.adminPageEvents;

import base.BaseTest;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.chromium.ChromiumOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.yaml.snakeyaml.Yaml;
import pageObjects.adminProject.AdminProjectElements;
import utils.ElementFetch;

import java.io.*;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static base.BaseTest.driver;
import static base.BaseTest.logger;

public class AdminProjectEvents {


    private ElementFetch elementFetch = new ElementFetch();
    private static String adminproject;


    //  Read the data from the config file
    public AdminProjectEvents() {
        try (InputStream inputStream = BaseTest.class.getClassLoader().getResourceAsStream("config.yml")) {
            if (inputStream == null) {
                throw new FileNotFoundException("config.yml not found in the classpath");
            }
            Yaml yaml = new Yaml();
            Map<String, Object> config = yaml.load(inputStream);

            adminproject = (String) config.get("adminproject");
        } catch (IOException e) {
            System.err.println("Error loading configuration: " + e.getMessage());
        }
    }


    //  Navigates to Admin Projects Routes page
    public void navigateToAdminProjectsRoutesPage() {


        logger.info("Click on As built in the dashboard");
        WebElement select = elementFetch.getWebElement("XPATH", AdminProjectElements.selectAsBuiltText);
        select.click();

        logger.info("Open Tratte Progetti Admin");
        driver.switchTo().newWindow(WindowType.TAB);
        driver.get(adminproject);


        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//td[contains(text(), '107248507')][2]")));


    }

    //  Search Project
    public void searchProjectById() {
        logger.info("Search Project by Id");
        WebElement search = elementFetch.getWebElement("XPATH", AdminProjectElements.searchFilter);
        search.click();

        WebElement field = elementFetch.getWebElement("XPATH", AdminProjectElements.searchField);
        field.click();
        field.clear();
        field.sendKeys("1579594004");

        WebElement searchButton = elementFetch.getWebElement("CSS", AdminProjectElements.searchButton);
        searchButton.click();

        WebElement closeFilter = elementFetch.getWebElement("CSS", AdminProjectElements.closeButton);
        closeFilter.click();

        logger.info("Click on Project having project id " + 1579594004);
        WebElement selectProject = elementFetch.getWebElement("XPATH", AdminProjectElements.selectProject1579594004);
        selectProject.click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[contains(text(), '161070481')]")));
        logger.info("Open Project having id 161070481");
        WebElement project161070481 = elementFetch.getWebElement("XPATH", AdminProjectElements.selectProject161070481);
        project161070481.click();
        String mainWindow = driver.getWindowHandle();
        WebDriverWait wait1 = new WebDriverWait(driver, Duration.ofSeconds(30));
        wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'Apri Progetto')]")));
        WebElement open = elementFetch.getWebElement("XPATH", AdminProjectElements.projectOpen);
        open.click();

    }


}


//
//    public void compareDataFiles() throws IOException {
//
//
//        String file1 = "CMTFile/Computo_Ripianificato_TEST_SEC (3).xlsx";
//        String file2 = "dataToCompare/Computo_Ripianificato_TEST_SEC.xlsx";
//
//        Workbook workbook1 = new XSSFWorkbook(new FileInputStream(file1));
//        Workbook workbook2 = new XSSFWorkbook(new FileInputStream(file2));
//
//        Sheet sheet1 = workbook1.getSheetAt(0);
//        Sheet sheet2 = workbook2.getSheetAt(0);
//
//        for (int i = 0; i < sheet1.getPhysicalNumberOfRows(); i++) {
//            Row row1 = sheet1.getRow(i);
//            Row row2 = sheet2.getRow(i);
//            if (!row1.getCell(0).getStringCellValue().equals(row2.getCell(0).getStringCellValue())) {
//                System.out.println("Difference found at row " + (i + 1));
//            }
//        }
//
//        workbook1.close();
//        workbook2.close();
//    }
//}











