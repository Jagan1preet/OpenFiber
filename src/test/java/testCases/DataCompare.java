package testCases;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pageEvents.adminPageEvents.AdminProjectEvents;
import pageEvents.adminPageEvents.DownloadCMTFile;
import java.io.IOException;

public class DataCompare extends LogIn {

    AdminProjectEvents adminProjectsRoutes = new AdminProjectEvents();


    @BeforeMethod
    @Parameters("browser") // Expecting a parameter named 'browser'
    public void setUp(@Optional("chrome") String browser) {
        System.out.println("Setting up tests for browser: " + browser);
    }

    // Test case for Downloading CMT file and compare it with old file
    @Test(description = "Verify user able to download and compare CMT file ")
    public void downloadAndCompareDataFile() throws IOException, InterruptedException {
        DownloadCMTFile.cleanoldCMTFolder();
        loginPage.login();
        DownloadCMTFile.moveFile();
        adminProjectsRoutes.openAdminProject();
        DownloadCMTFile.CMTFile();
        DownloadCMTFile.compareFiles();

    }

}
