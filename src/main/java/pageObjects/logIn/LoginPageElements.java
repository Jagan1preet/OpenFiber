package pageObjects.logIn;

public interface LoginPageElements {

    //  Click on the email address field
    String emailAddress = "//input[@id='inputUserName']";

    //  Click on the Password field
    String passwordField = "//input[@type='password']";

    //  Click on Login button
    String loginButton = "//span[@id='dijit_form_Button_0_label']";

    //  Click on Logout button
    String logout = "//font[contains(text(), 'Logout')]";
}

