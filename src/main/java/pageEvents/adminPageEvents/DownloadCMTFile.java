package pageEvents.adminPageEvents;

import base.BaseTest;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeTest;
import org.yaml.snakeyaml.Yaml;
import pageObjects.adminProject.AdminProjectElements;
import utils.ElementFetch;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static base.BaseTest.driver;
import static base.BaseTest.logger;

public class DownloadCMTFile {


    //  Code to clean the directory
    public static void cleanFolder() throws IOException {
        String downloadDirPath = System.getProperty("user.dir") + File.separator + "CMTFile";
        File directory = new File(downloadDirPath);
        logger.info("Delete the previous file from the folder so that new file will be downloaded");
        FileUtils.cleanDirectory(directory);
    }

    //  Code to download CMT File
    public static void CMTFile() throws InterruptedException {

        ElementFetch elementFetch = new ElementFetch();
        try {

            logger.info("Click on As built in the dashboard");
            WebElement select = elementFetch.getWebElement("XPATH", AdminProjectElements.selectAsBuiltText);
            select.click();

            logger.info("Open Tratte Progetti Admin");

            WebElement open = elementFetch.getWebElement("XPATH", AdminProjectElements.adminProjectsRoutes);
            open.click();

            // Wait for the new tab to open
            Thread.sleep(50000);

            // Get the current window handle
            String parentWindow = driver.getWindowHandle();

            // Store all window handles in a list
            List<String> windowHandles = new ArrayList<>(driver.getWindowHandles());

            // Switch to the child window (tab)
            for (String windowHandle : windowHandles) {
                if (!windowHandle.equals(parentWindow)) {
                    driver.switchTo().window(windowHandle);
                    break;
                }
            }

            // Now you are in the child window, perform actions here
            System.out.println("Child Tab Title: " + driver.getTitle());
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

            WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(30));
            wait2.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[contains(text(), '161070481')]")));
            logger.info("Open Project having id 161070481");
            WebElement project161070481 = elementFetch.getWebElement("XPATH", AdminProjectElements.selectProject161070481);
            project161070481.click();
            WebDriverWait wait1 = new WebDriverWait(driver, Duration.ofSeconds(30));
            wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'Apri Progetto')]")));
            WebElement opennew = elementFetch.getWebElement("XPATH", AdminProjectElements.projectOpen);
            opennew.click();


            // Wait for the new sub-child tab to open
            Thread.sleep(40000); // Use WebDriverWait in production code

            // Store all window handles again
            windowHandles = new ArrayList<>(driver.getWindowHandles());

            // Switch to the sub-child window (tab)
            for (String windowHandle : windowHandles) {
                if (!windowHandle.equals(parentWindow) && !windowHandle.equals(driver.getWindowHandle())) {
                    driver.switchTo().window(windowHandle);
                    break;
                }
            }

            // Perform actions in the sub-child tab
            System.out.println("Sub-Child Tab Title: " + driver.getTitle());
            WebDriverWait wait3 = new WebDriverWait(driver, Duration.ofSeconds(20));
            wait3.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains (text(),'Gestione Elaborati')]")));


            WebElement document = elementFetch.getWebElement("XPATH", AdminProjectElements.documentManagement);
            document.click();

            WebElement printmanagement = elementFetch.getWebElement("XPATH", AdminProjectElements.printManagement);
            printmanagement.click();

            WebElement cmt = elementFetch.getWebElement("XPATH", AdminProjectElements.CMT);
            cmt.click();
            WebDriverWait waait = new WebDriverWait(driver, Duration.ofSeconds(30));
            waait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='extract']")));


            WebElement extract = elementFetch.getWebElement("XPATH", AdminProjectElements.extract);
            extract.click();

            WebDriverWait waait1 = new WebDriverWait(driver, Duration.ofSeconds(30));
            waait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@class='dojoxFloatingCloseIcon']")));

            WebElement close = elementFetch.getWebElement("XPATH", AdminProjectElements.close);
            close.click();

            WebDriverWait waait2 = new WebDriverWait(driver, Duration.ofSeconds(30));
            waait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(), 'Fibre e giunzioni')]")));

            WebElement fiber = elementFetch.getWebElement("XPATH", AdminProjectElements.fiber);
            Actions actions = new Actions(driver);
            actions.moveToElement(fiber).click().perform();


            logger.info("click on Gestione Elaborati");
            WebElement Printmanagement = elementFetch.getWebElement("XPATH", AdminProjectElements.documentManagement);
            Printmanagement.click();

            logger.info("click on Gestione Stampe AsBuilt");
            WebElement Built = elementFetch.getWebElement("XPATH", AdminProjectElements.Asbuilt);
            Built.click();


            logger.info("click on Estrai CMT Progettato + Realizzato");
            WebElement Cmt = elementFetch.getWebElement("XPATH", AdminProjectElements.CMT);
            Cmt.click();

            WebDriverWait wt1 = new WebDriverWait(driver, Duration.ofSeconds(30));
            wt1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='extract']")));


            logger.info("click on Extract");
            WebElement Extract = elementFetch.getWebElement("XPATH", AdminProjectElements.extract);
            Extract.click();

//            WebDriverWait wt = new WebDriverWait(driver, Duration.ofSeconds(30));
//            wt.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='btnSubmit']")));


//     Click on the Invia button to download the file

            logger.info("click on Invia to download file");
            WebElement invia = driver.findElement(By.xpath(AdminProjectElements.invia));
            invia.click();

//     Get the download directory path
            String downloadDirPath = System.getProperty("user.dir") + File.separator + "CMTFile";

//     Wait for the file to be downloaded
            File file = new File(downloadDirPath + File.separator + "Computo_Ripianificato_TEST_SEC.xlsx"); // Replace with the actual file name
            int timeout = 60; // seconds
            while (!file.exists() && timeout > 0) {
                Thread.sleep(90000); // wait for 90 second
                timeout--;


//     Verify the file existence
                if (file.exists()) {
                    logger.info("File downloaded successfully: " + file.getAbsolutePath());
                } else {
                    logger.info("File not downloaded: " + file.getAbsolutePath());
                    Assert.fail("File not downloaded");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Teardown WebDriver
            driver.close();
        }
    }
}


