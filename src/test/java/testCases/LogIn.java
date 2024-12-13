package testCases;

import base.BaseTest;
import org.testng.annotations.Test;
import pageEvents.adminPageEvents.DownloadCMTFile;

import java.io.IOException;

public class LogIn extends BaseTest {


    @Test(description = "Verify Login Page loads properly")
    public void verifyLogInPadeLoad() {
        verifyIfLoginPageIsLoaded();
    }


    @Test(description = "Verify user able to LogIn by Providing valid credentials")
    public void logIn() {
        login();
    }
}
