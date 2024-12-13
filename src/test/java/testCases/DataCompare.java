package testCases;

import base.BaseTest;
import org.testng.annotations.Test;
import pageEvents.adminPageEvents.AdminProjectEvents;
import pageEvents.adminPageEvents.DownloadCMTFile;


import java.io.IOException;


public class DataCompare extends BaseTest {

    AdminProjectEvents adminProject = new AdminProjectEvents();

    // Test case for Downloading CMT file and compare it with old file
    @Test(description = "Verify user able to download and compare CMT file ")
    public void downloadAndCompareDataFile() throws InterruptedException {

        login();
        DownloadCMTFile.moveFile();
        adminProject.openAdminProject();
        DownloadCMTFile.CMTFile();
        DownloadCMTFile.compareFiles();

    }

}
