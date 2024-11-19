package testCases;

import org.testng.annotations.Test;
import pageEvents.adminPageEvents.DownloadCMTFile;
import pageEvents.adminPageEvents.CompareCMTFiles;

import java.io.IOException;

public class DataCompare extends LogIn {

    DownloadCMTFile compare = new DownloadCMTFile();
    CompareCMTFiles datafile = new CompareCMTFiles();


    // Test case for Downloading CMT file and compare it with old file
    @Test(description = "Verify user able to download and compare CMT file ")
    public void downloadAndCompareDataFile() throws InterruptedException, IOException {
        loginPage.login();
        DownloadCMTFile.cleanFolder();
        DownloadCMTFile.CMTFile();
        CompareCMTFiles.compareFiles();

    }

}
