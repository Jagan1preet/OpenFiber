package testCases;

import org.testng.annotations.Test;
import pageEvents.adminPageEvents.AdminProjectEvents;

import java.io.IOException;

public class AdminProject extends LogIn {


    AdminProjectEvents adminProjectsRoutes = new AdminProjectEvents();

    //  Test case to navigate to Admin Project Routes Page
    @Test(description = "Verify user able to Open Project")
    public void openAdminProject() throws InterruptedException {
        loginPage.login();
        adminProjectsRoutes.openAdminProject();
    }
}






