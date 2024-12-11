package testCases;

import base.BaseTest;
import org.testng.annotations.Test;
import pageEvents.adminPageEvents.AdminProjectEvents;


public class AdminProject extends BaseTest {

    AdminProjectEvents adminProjectsRoutes = new AdminProjectEvents();

    //  Test case to navigate to Admin Project Routes Page
    @Test(description = "Verify user able to Open Project")
    public void openAdminProject() throws InterruptedException {
        verifyIfLoginPageIsLoaded();
        login();
        adminProjectsRoutes.openAdminProject();
    }
}






