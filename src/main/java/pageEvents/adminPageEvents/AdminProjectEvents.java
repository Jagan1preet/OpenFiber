package pageEvents.adminPageEvents;

import base.BaseTest;
import lombok.Getter;
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
    @Getter
    private String search_project;
    @Getter
    private String open_project;



    private void loadConfig() {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("config.yml")) {
            if (inputStream == null) {
                throw new FileNotFoundException("config.yml not found in the classpath");
            }
            Yaml yaml = new Yaml();
            Map<String, Object> config = yaml.load(inputStream);

            // Load credentials
            if (config.containsKey("credentials")) {
                Map<String, Object> creds = (Map<String, Object>) config.get("credentials");
                search_project  = (String) creds.get("search_project");
                open_project= (String) creds.get("open_project");

                // Replace placeholders with actual values
                search_project = search_project.replace("${SEARCHPROJECT}", System.getProperty("SEARCHPROJECT"));
                open_project = open_project.replace("${OPENPROJECT}", System.getProperty("OPENPROJECT"));
            } else {
                System.err.println("Credentials key not found in config.yml");
            }



        } catch (IOException e) {
            System.err.println("Error loading configuration: " + e.getMessage());
        }
    }



    //  Navigates to Admin Projects Routes page
    public void openAdminProject() throws InterruptedException {
        loadConfig();

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
        field.sendKeys(search_project);

        WebElement searchButton = elementFetch.getWebElement("CSS", AdminProjectElements.searchButton);
        searchButton.click();

        WebElement closeFilter = elementFetch.getWebElement("CSS", AdminProjectElements.closeButton);
        closeFilter.click();

        logger.info("Click on Project having project id " + search_project);
        WebElement selectProject = elementFetch.getWebElement("XPATH", AdminProjectElements.selectProject1579594004);
        selectProject.click();

        WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(30));
        wait2.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[contains(text(), '161070481')]")));
        logger.info("Open Project having id" +open_project);
        WebElement project = elementFetch.getWebElement("XPATH", AdminProjectElements.selectProject161070481);
        project.click();
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
        System.out.println("Sub child Tab Title: " + driver.getTitle());
    }
}












