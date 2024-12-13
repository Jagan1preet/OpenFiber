package pageEvents.adminPageEvents;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pageObjects.adminProject.AdminProjectElements;
import utils.ElementFetch;

import java.io.*;
import java.nio.file.*;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import static base.BaseTest.driver;
import static base.BaseTest.logger;


public class DownloadCMTFile {

    private static String oldCMTFilePath;
    private static String downloadedFilePath;


    //  Code to download CMT File
    public static void CMTFile() {

        ElementFetch elementFetch = new ElementFetch();


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


        // Click on the Invia button to download the file

        logger.info("click on Invia to download file");
        WebElement invia = driver.findElement(By.xpath(AdminProjectElements.invia));
        invia.click();


        // Get the download directory path

        String downloadDirPath = System.getProperty("user.dir") + File.separator + "CMTFile";


        // Wait for the file to be downloaded
        String downloadedFileName = waitForFileToDownload(downloadDirPath);

        if (downloadedFileName != null) {
            System.out.println("Name of Downloaded file: " + downloadedFileName);
            logger.info("Name of Downloaded file: " + downloadedFileName);

            // Get the absolute path of the downloaded file
            File downloadedFile = new File(downloadDirPath + File.separator + downloadedFileName);
            downloadedFilePath = downloadedFile.getAbsolutePath();
            System.out.println("Absolute path of the downloaded file: " + downloadedFilePath);
            logger.info("Absolute path of the downloaded file: " + downloadedFilePath);

        } else {
            System.out.println("File not downloaded within the expected time.");
            logger.warning("File not downloaded within the expected time.");
        }
    }

    public static String waitForFileToDownload(String downloadDir) {
        File dir = new File(downloadDir);
        int attempts = 0;

        // Wait until a new file is downloaded
        while (attempts < 1500) { // Wait
            try {
                TimeUnit.SECONDS.sleep(30);// Sleep for 30 second
                System.out.println("waiting for file to be download");
                logger.info("waiting for file to be download");

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // List all file in the download directory
            File[] files = dir.listFiles();
            if (files != null && files.length > 0) {
                // Sort files by last modified time
                Arrays.sort(files, Comparator.comparingLong(File::lastModified).reversed());
                // Return the name of the most recently modified file
                return files[0].getName();
            }
            attempts++;
        }
        return null;

    }

    // Code to move the downloaded CMT file in the Folder
    public static void moveFile() {
        Path sourceDir = Paths.get("CMTFile");
        Path targetDir = Paths.get("OldCMTFile");

        try {
            // Create target directory if it doesn't exist
//            if (Files.notExists(targetDir)) {
//                Files.createDirectories(targetDir);
//                System.out.println("Target directory created.");
//                logger.info("Target directory created.");
//            }

            // Move files from source to target directory
            try (DirectoryStream<Path> stream = Files.newDirectoryStream(sourceDir)) {
                for (Path file : stream) {
                    Path targetFile = targetDir.resolve(file.getFileName());

                    // If the target file exists, append a timestamp to the file name
                    if (Files.exists(targetFile)) {
                        targetFile = getTimestampedFileName(targetFile);
                        System.out.println("File with the same name exists. Renaming file to: " + targetFile.getFileName());
                        logger.info("File with the same name exists. Renaming file to: " + targetFile.getFileName());
                    }

                    // Move the file to the target directory (overwrite if necessary)
                    Files.move(file, targetFile, StandardCopyOption.REPLACE_EXISTING);
                    System.out.println("Moved: " + file.getFileName() + " to " + targetFile);
                    logger.info("Moved: " + file.getFileName() + " to " + targetFile);

                    // Get the absolute path of the file
                    oldCMTFilePath = targetFile.toAbsolutePath().toString();
                    System.out.println("Absolute path of the Old file: " + oldCMTFilePath);
                    logger.info("Absolute path of the Old file: " + oldCMTFilePath);
                }
            }

        } catch (IOException e) {
            logger.warning("Error moving files: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Helper method to add a timestamp to the file name
    public static Path getTimestampedFileName(Path targetFile) {
        // Get the file name without extension
        String fileNameWithoutExtension = targetFile.getFileName().toString();
        String fileExtension = "";

        int extIndex = fileNameWithoutExtension.lastIndexOf(".");
        if (extIndex > 0) {
            fileExtension = fileNameWithoutExtension.substring(extIndex);
            fileNameWithoutExtension = fileNameWithoutExtension.substring(0, extIndex);
        }

        // Generate a timestamp (e.g., 20241213123000 for "2024-12-13 12:30:00")
        String timestamp = new SimpleDateFormat("yyyy-MM-dd- HH-mm-ss").format(new Date());

        // Create the new file name with timestamp
        String newFileName = fileNameWithoutExtension + "_" + timestamp + fileExtension;

        // Return the new file path with the timestamped name
        return targetFile.getParent().resolve(newFileName);
    }


    public static void compareFiles() {
        String newFile = downloadedFilePath;
        String oldFile = oldCMTFilePath;
        System.out.println("New CMT File Path: " + newFile);
        logger.info("New CMT File Path : " + newFile);
        System.out.println("Old CMT File Path: " + oldFile);
        logger.info("Old CMT File Path: " + oldFile);
        try {
            compareExcelFiles(newFile, oldFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void compareExcelFiles(String newFile, String oldFile) throws IOException {
        logger.info("Read Data from the Excel Files");
        try (FileInputStream newCMTFile = new FileInputStream(newFile);
             FileInputStream oldCMTFile = new FileInputStream(oldFile);
             Workbook workbook1 = new XSSFWorkbook(newCMTFile);
             Workbook workbook2 = new XSSFWorkbook(oldCMTFile)) {
            Sheet sheet1 = workbook1.getSheetAt(0);
            Sheet sheet2 = workbook2.getSheetAt(0);
            logger.info("Compare Data in New and Old CMT file");
            int rowCount = Math.max(sheet1.getPhysicalNumberOfRows(), sheet2.getPhysicalNumberOfRows());
            boolean identical = true;
            logger.info("Compares the number of rows in both sheets");
            logger.info("Compares the values of cells from both files");
            for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
                Row row1 = sheet1.getRow(rowIndex);
                Row row2 = sheet2.getRow(rowIndex);
                if (row1 == null && row2 == null) {
                    continue; // Both rows are empty
                }
                if (row1 == null || row2 == null) {
                    identical = false;
                    System.out.println("Difference found at row " + (rowIndex + 1) + ": One of the rows is empty");
                    logger.warning("Difference found at row " + (rowIndex + 1) + ": One of the rows is empty");
                    continue;
                }
                int cellCount = Math.max(row1.getPhysicalNumberOfCells(), row2.getPhysicalNumberOfCells());
                for (int cellIndex = 0; cellIndex < cellCount; cellIndex++) {
                    Cell cell1 = row1.getCell(cellIndex);
                    Cell cell2 = row2.getCell(cellIndex);
                    String value1 = (cell1 == null) ? "" : cell1.toString();
                    String value2 = (cell2 == null) ? "" : cell2.toString();
                    if (!value1.equals(value2)) {
                        identical = false;
                        System.out.println("Difference found at row " + (rowIndex + 1) + ", column " + (cellIndex + 1) + ": " +
                                "newfile has '" + value1 + "'; oldfile has '" + value2 + "'");
                        logger.warning("Difference found at row " + (rowIndex + 1) + ", column " + (cellIndex + 1) + ": " +
                                "newfile has '" + value1 + "'; oldfile has '" + value2 + "'");
                    }
                }
            }
            if (identical) {
                System.out.println("Data in Both The Files are Same");
                logger.info("Data in Both The Files are Same");
            }
        }
    }
}