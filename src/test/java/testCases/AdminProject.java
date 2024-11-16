package testCases;

import org.testng.annotations.Test;
import pageEvents.adminPageEvents.AdminProjectEvents;

import java.io.IOException;

public class AdminProject extends LogIn {


    AdminProjectEvents adminProjectsRoutes = new AdminProjectEvents();

//  Test case to navigate to Admin Project Routes Page
    @Test(description = "Verify user able to Navigate To AdminProject Routes Page")
    public void navigateToAdminProjectsRoutesPage() throws InterruptedException {
        loginPage.login();
        adminProjectsRoutes.navigateToAdminProjectsRoutesPage();
    }

//  Test case to search Project by ID
    @Test(description = "Verify user able to Search Project By Id and open it")
    public void searchProjectById() {
        loginPage.login();
        adminProjectsRoutes.navigateToAdminProjectsRoutesPage();
        adminProjectsRoutes.searchProjectById();

    }

}






