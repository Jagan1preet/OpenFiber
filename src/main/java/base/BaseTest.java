package base;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.Getter;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.*;
import org.yaml.snakeyaml.Yaml;
import pageObjects.logIn.LoginPageElements;
import utils.ElementFetch;


import java.io.*;
import java.lang.reflect.Method;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


public class BaseTest {

    public static WebDriver driver;
    public static ExtentReports extent;
    public static ExtentSparkReporter sparkReporter;
    public static ExtentTest logger;
    private static String url;
    private static Map<String, Object> credentials;
    private ElementFetch elementFetch = new ElementFetch();
    @Getter
    private String username;
    @Getter
    private String password;


    @BeforeSuite
    public void beforeSuite() {


        sparkReporter = new ExtentSparkReporter(System.getProperty("user.dir") + File.separator + "reports" + File.separator + "Result.html");
        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);
        sparkReporter.config().setTheme(Theme.DARK);
        extent.setSystemInfo("Company", "Eagle Projects");
        extent.setSystemInfo("Project", "Open Fiber");
        extent.setSystemInfo("Name", "Jagan");
        extent.setSystemInfo("Role", "Software Tester");
        sparkReporter.config().setDocumentTitle("Automation Report");
        sparkReporter.config().setReportName("Automation Report");

//      Inject custom JavaScript to change the logo
        String customJS = "document.addEventListener('DOMContentLoaded', function() {" +
                "   console.log('DOM fully loaded and parsed');" +
                "   var logo = document.querySelector('.logo');" +
                "   if (logo) {" +
                "       console.log('Logo element found');" +
                "       logo.style.backgroundImage = 'url(\\\"../Logo/02.08.2024_09.26.13_REC.png\\\")';" +
                "       logo.style.backgroundSize = 'contain';" +
                "       logo.style.backgroundRepeat = 'no-repeat';" +
                "       logo.style.width = '60px';" +  // Set the desired width
                "       logo.style.height = 'px';" + // Set the desired height
                "       logo.style.marginRight = '0px';" +
                "   } else {" +
                "       console.log('Logo element not found');" +
                "   }" +
                "   var link = document.querySelector('link[rel*=\"icon\"]') || document.createElement('link');" +
                "   link.type = 'image/png';" +
                "   link.rel = 'shortcut icon';" +
                "   link.href = '..Logo/02.08.2024_09.26.13_REC.png';" +
                "   document.getElementsByTagName('head')[0].appendChild(link);" +
                "});";

        sparkReporter.config().setJs(customJS);
        loadProperties();
        loadConfig();


    }

    private void loadProperties() {
        Properties properties = new Properties();
        try (InputStream input = BaseTest.class.getClassLoader().getResourceAsStream("property.env")) {
            if (input == null) {
                System.err.println("property.env not found in the classpath");
                return;
            }
            properties.load(input);
        } catch (IOException ex) {
            System.err.println("Error loading properties: " + ex.getMessage());
        }

        // Set system properties for later use in config.yml
        for (String name : properties.stringPropertyNames()) {
            System.setProperty(name, properties.getProperty(name));
        }
    }

    private void loadConfig() {
        try (InputStream inputStream = BaseTest.class.getClassLoader().getResourceAsStream("config.yml")) {
            if (inputStream == null) {
                throw new FileNotFoundException("config.yml not found in the classpath");
            }
            Yaml yaml = new Yaml();
            Map<String, Object> config = yaml.load(inputStream);

            // Default to testing environment
            String environmentType = "testing"; // Change this if needed

            // Check if the environments key is present in the config
            if (config.containsKey("environments")) {
                Map<String, Object> environments = (Map<String, Object>) config.get("environments");

                // Check if the specified environment is present
                if (environments.containsKey(environmentType)) {
                    Map<String, Object> selectedConfig = (Map<String, Object>) environments.get(environmentType);

                    // Get the URL for the selected environment
                    if (selectedConfig.containsKey("url")) {
                        url = (String) selectedConfig.get("url");
                        // Replace placeholders with system properties
                        url = url.replace("${URL}", System.getProperty("URL"));
                        System.out.println(url);
                    } else {
                        System.err.println("URL key not found in " + environmentType + " section of config.yml");
                    }
                } else {
                    System.err.println(environmentType + " environment not found in config.yml");
                }
            } else {
                System.err.println("Environments key not found in config.yml");
            }
        } catch (IOException e) {
            System.err.println("Error loading configuration: " + e.getMessage());
        }
    }


    @BeforeMethod
    @Parameters({"browser", "headless"})
    public void beforeTestMethod(String browser, @Optional("false") String headless, Method testMethod) {

//      Retrieve the description from the @Test annotation
        Test testAnnotation = testMethod.getAnnotation(Test.class);
        String description = testAnnotation.description();

//      Create a test instance with description
        logger = extent.createTest(description);

//      Convert headless string parameter to boolean
        boolean isHeadless = Boolean.parseBoolean(headless);

        setupDriver(browser, isHeadless);
        driver.manage().window().maximize();

//      URL from YAML file
        driver.get(url);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

    }


    @AfterMethod
    public void afterMethod(ITestResult result) throws IOException {
        if (result.getStatus() == ITestResult.FAILURE) {
            String screenshotPath = captureScreenshot(result.getMethod().getMethodName());
            logger.log(Status.FAIL, MarkupHelper.createLabel(result.getName() + " - Test Case Failed", ExtentColor.RED));

            String failureDetails = result.getThrowable().getMessage();
            logger.fail("Failure Attachment " + failureDetails,
                    MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());

        } else if (result.getStatus() == ITestResult.SKIP) {
            logger.log(Status.SKIP, MarkupHelper.createLabel(result.getName() + " - Test Case Skipped", ExtentColor.ORANGE));
        } else if (result.getStatus() == ITestResult.SUCCESS) {
            logger.log(Status.PASS, MarkupHelper.createLabel(result.getName() + " - Test Case PASS", ExtentColor.GREEN));
        }

        driver.quit();
    }

    @AfterSuite
    public void afterSuite() {
        extent.flush();

    }

    public void setupDriver(String browser, boolean headless) {
        if (browser.equalsIgnoreCase("chrome")) {
            WebDriverManager.chromedriver().setup();

            // Set the download directory
            String downloadFilepath = System.getProperty("user.dir") + File.separator + "CMTFile"; // Specify your download directory here

            ChromeOptions options = new ChromeOptions();
            options.addArguments("--disable-extensions");
            options.addArguments("--disable-infobars");
            options.addArguments("--disable-notifications");
            options.addArguments("--disable-autofill");

            // Set Chrome preferences for downloading files
            options.setExperimentalOption("prefs", new HashMap<String, Object>() {{
                put("download.default_directory", downloadFilepath); // Change default download directory
                put("download.prompt_for_download", false); // Do not ask for confirmation
                put("download.directory_upgrade", true); // Allow directory upgrade
                put("safebrowsing.enabled", true); // Enable safe browsing
            }});

            if (headless) {
                options.addArguments("--headless=new");
                options.addArguments("--disable-gpu"); // for Windows OS
                options.addArguments("--no-sandbox"); // Bypass OS security model
                options.addArguments("--disable-dev-shm-usage"); // Overcome limited resource problems
            }
            driver = new ChromeDriver(options);
        } else if (browser.equalsIgnoreCase("firefox")) {
            WebDriverManager.firefoxdriver().setup();
            FirefoxOptions options = new FirefoxOptions();
            options.addArguments("--disable-notifications");
            if (headless) {
                options.addArguments("--headless");
            }
            driver = new FirefoxDriver(options);
        } else if (browser.equalsIgnoreCase("edge")) {
            WebDriverManager.edgedriver().setup();
            EdgeOptions options = new EdgeOptions();
            options.addArguments("--disable-notifications");
            if (headless) {
                options.addArguments("headless");
            }
            driver = new EdgeDriver(options);
        }
        driver.manage().window().maximize();

//      URL from config file
        driver.get(url);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
    }

    public String captureScreenshot(String methodName) {
        String screenshotPath = System.getProperty("user.dir") + File.separator + "reports" + File.separator + "Screenshots" + methodName + ".png";
        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(screenshot, new File(screenshotPath));
            System.out.println("Screenshot saved at: " + screenshotPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "screenshots/" + methodName + ".png";
    }

    private void loadConfigprop() {
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

    //  Verifies login with correct username and password
    public void login() {
        loadConfigprop();
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



