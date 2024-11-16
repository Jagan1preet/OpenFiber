package testCases;

import base.BaseTest;
import org.testng.annotations.Test;
import pageEvents.loginEvents.LoginPageEvents;

public class LogIn extends BaseTest {

    LoginPageEvents loginPage = new LoginPageEvents();


// Test case to validate if the login page load properly
    @Test(description = "Verify Login page is loaded completely")
    public void verifyLoginPage() {
        loginPage.verifyIfLoginPageIsLoaded();
    }

//  Test case to Log in
    @Test(description = "Verify User able to Login with correct credentials")
    public void verifyLogin() {
        loginPage.login();
    }

}