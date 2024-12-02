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
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static base.BaseTest.driver;
import static base.BaseTest.logger;

public class DownloadCMTFile {


    //  Code to clean the directory
    public static void cleanCMTFolder() throws IOException {
        String downloadDirPath = System.getProperty("user.dir") + File.separator + "CMTFile";
        File directory = new File(downloadDirPath);
        logger.info("Delete the previous file from the CMT folder so that new file will be downloaded");
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

            // Perform actions in child window
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
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'Apri Progetto')]")));
            WebElement openproject = elementFetch.getWebElement("XPATH", AdminProjectElements.projectOpen);
            openproject.click();


            // Wait for the new sub-child tab to open
            Thread.sleep(40000);

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

            WebElement document = elementFetch.getWebElement("XPATH", AdminProjectElements.documentManagement);
            document.click();

            WebElement printmanagement = elementFetch.getWebElement("XPATH", AdminProjectElements.printManagement);
            printmanagement.click();

            WebElement cmt = elementFetch.getWebElement("XPATH", AdminProjectElements.CMT);
            cmt.click();
            WebDriverWait wait1 = new WebDriverWait(driver, Duration.ofSeconds(30));
            wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='extract']")));


            WebDriverWait wit2 = new WebDriverWait(driver, Duration.ofSeconds(30));
            wit2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@class='dojoxFloatingCloseIcon']")));

            WebElement close = elementFetch.getWebElement("XPATH", AdminProjectElements.close);
            Actions action = new Actions(driver);
            action.moveToElement(close).click().perform();

            WebDriverWait wait3 = new WebDriverWait(driver, Duration.ofSeconds(30));
            wait3.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(), 'Fibre e giunzioni')]")));

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

            WebDriverWait wait4 = new WebDriverWait(driver, Duration.ofSeconds(30));
            wait4.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='btnSubmit']")));


//          Click on the Invia button to download the file

            logger.info("click on Invia to download file");
            WebElement invia = driver.findElement(By.xpath(AdminProjectElements.invia));
            invia.click();


//          Get the download directory path
            String downloadDirPath = System.getProperty("user.dir") + File.separator + "CMTFile";

//          Define the file to check for
            File file = new File(downloadDirPath + File.separator + "Computo_Ripianificato_TEST_SEC.xlsx");

//          Wait for the file to be downloaded
            int timeout = 1500; // seconds (25 minutes)
            int pollingInterval = 30000; // 30 seconds wait time to check the file downloaded or not after clicking on the button to download file
            long startTime = System.currentTimeMillis();

            while (!file.exists() && (System.currentTimeMillis() - startTime) < (timeout * 30000)) {
                logger.info("Waiting for file to download: " + file.getAbsolutePath());
                System.out.println("Waiting for file to download: " + file.getAbsolutePath());
                try {
                    Thread.sleep(pollingInterval); // Wait for every 30 second before checking file downloaded in the folder
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt(); // Restore the interrupted status
                    logger.info("Thread was interrupted while waiting for file download");
                    System.out.println("Thread was interrupted while waiting for file download");
                }
            }


//          Verify the file existence in the folder
            if (file.exists()) {
                logger.info("File downloaded successfully: " + file.getAbsolutePath());
                System.out.println("File downloaded successfully: " + file.getAbsolutePath());
            } else {
                logger.info("File not downloaded: " + file.getAbsolutePath());
                System.out.println("File not downloaded: " + file.getAbsolutePath());
                Assert.fail("File not downloaded");
            }
        } catch (Exception e) {
            logger.info("An error occurred during file download process");
            System.out.println("An error occurred during file download process");
        }
    }


    //  Code to clean the directory
    public static void cleanoldCMTFolder() throws IOException {
        String OldCMT = System.getProperty("user.dir") + File.separator + "OldCMTFor161070481";
        File directory = new File(OldCMT);
        logger.info("Delete the old CMT file from the folder so that CMT File from previous Test will be moved to the folder for comparision with new file");
        FileUtils.cleanDirectory(directory);
    }


    // Code to move the downloaded CMT file in the Folder
    public static void moveFile() {

        // Specify the source file path and destination directory
        Path sourceFile = Paths.get("CMTFile/Computo_Ripianificato_TEST_SEC.xlsx");
        Path destinationFile = Paths.get("OldCMTFor161070481/Computo_Ripianificato_TEST_SEC.xlsx");

        try {
            // Move the file
            Files.move(sourceFile, destinationFile);

            System.out.println("File moved successfully.");
            logger.info("CMT File from previous Test moved to the folder so that new CMT file will be compared with this Old file");

        } catch (IOException e) {
            e.printStackTrace();
        } finally {

        }
    }
}






