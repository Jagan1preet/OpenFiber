package testCases;

import base.BaseTest;
import org.testng.annotations.Test;
import pageEvents.adminPageEvents.AdminProjectEvents;
import pageEvents.adminPageEvents.DownloadCMTFile;


import java.io.IOException;

import static pageObjects.adminProject.AdminProjectElements.adminProjectsRoutes;

public class DataCompare extends BaseTest {

    AdminProjectEvents adminProjectsRoutes = new AdminProjectEvents();

    // Test case for Downloading CMT file and compare it with old file
    @Test(description = "Verify user able to download and compare CMT file ")
    public void downloadAndCompareDataFile() throws IOException, InterruptedException {
        DownloadCMTFile.cleanoldCMTFolder();
        login();
        DownloadCMTFile.moveFile();
        adminProjectsRoutes.openAdminProject();
        DownloadCMTFile.CMTFile();
        DownloadCMTFile.compareFiles();

    }

}
