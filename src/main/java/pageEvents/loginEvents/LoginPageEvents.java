package pageEvents.loginEvents;

import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.yaml.snakeyaml.Yaml;
import pageObjects.logIn.LoginPageElements;
import utils.ElementFetch;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.util.Map;
import java.util.Properties;

import static base.BaseTest.driver;
import static base.BaseTest.logger;

public class LoginPageEvents {

    private ElementFetch elementFetch = new ElementFetch();
    @Getter
    private String username;
    @Getter
    private String password;


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
                username = (String) creds.get("username");
                password = (String) creds.get("password");

                // Replace placeholders with actual values
                username = username.replace("${USERNAME}", System.getProperty("USERNAME"));
                password = password.replace("${PASSWORD}", System.getProperty("PASSWORD"));
            } else {
                System.err.println("Credentials key not found in config.yml");
            }

            // Debugging statements
            System.out.println("Username: " + username);
            System.out.println("Password: " + password);

        } catch (IOException e) {
            System.err.println("Error loading configuration: " + e.getMessage());
        }
    }

    //  Verifies if the login page is loaded
    public void verifyIfLoginPageIsLoaded() {
        logger.info("Validate if all the elements load properly");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(LoginPageElements.loginButton)));
    }


    //  Logs in with correct credentials
    public void login() {
        loadConfig();
        System.out.println("Parent Tab Title: " + driver.getTitle());
        logger.info("Enter correct credentials");
        WebElement emailField = elementFetch.getWebElement("XPATH", LoginPageElements.emailAddress);
        WebElement passwordField = elementFetch.getWebElement("XPATH", LoginPageElements.passwordField);

        passwordField.clear();
        passwordField.click();

//      Use username from config file
        passwordField.sendKeys(password);
        emailField.clear();
        emailField.click();

//      Use password from config file
        emailField.sendKeys(username);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(LoginPageElements.loginButton)));

        logger.info("Click on login button");
        loginButton.click();
        logger.info("Login Successful");

    }

}