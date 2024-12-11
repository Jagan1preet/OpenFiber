package testCases;

import org.testng.annotations.Test;
import pageEvents.adminPageEvents.AdminProjectEvents;
import pageEvents.adminPageEvents.DownloadCMTFile;


import java.io.IOException;


public class DataCompare extends LogIn {

    AdminProjectEvents adminProjectsRoutes = new AdminProjectEvents();

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
