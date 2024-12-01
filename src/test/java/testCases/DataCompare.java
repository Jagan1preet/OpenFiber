package testCases;

import org.testng.annotations.Test;
import pageEvents.adminPageEvents.DownloadCMTFile;
import pageEvents.adminPageEvents.ExcelComparator;

import java.io.IOException;

public class DataCompare extends LogIn {

    DownloadCMTFile compare = new DownloadCMTFile();
    ExcelComparator datafile = new ExcelComparator();


    // Test case for Downloading CMT file and compare it with old file
    @Test(description = "Verify user able to download and compare CMT file ")
    public void downloadAndCompareDataFile() throws InterruptedException, IOException {
        loginPage.login();
        DownloadCMTFile.cleanoldCMTFolder();
        DownloadCMTFile.moveFile();
        DownloadCMTFile.CMTFile();
        ExcelComparator.compareFiles();

    }

}
