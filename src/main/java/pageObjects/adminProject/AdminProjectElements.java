package pageObjects.adminProject;

public interface AdminProjectElements {

    //  Click on As Built after Login
    String selectAsBuiltText = "//span[@id='tabBarButton_3_label']";

    //  Click on Admin Project Routes
    String adminProjectsRoutes = "//div[@id='dijit_layout_ContentPane_95']";

    //  Click on Search Filter on Admin Project Routes Page
    String searchFilter = "//span[@class='dijitReset dijitInline dijitIcon iconFilter']";

    //  Click on Search field to enter data
    String searchField = "//input[@id='sf_value_13446436']";

    //  Click on Search Button
    String searchButton = ".iconDone";

    //  Click on Close After searching the Project
    String closeButton = ".iconclose";

    //  Click on the search Project1579594004
    String selectProject1579594004 = "//td[contains(text(), '1579594004')][2]";

    //  Click on 161070481
    String selectProject161070481 = "//div[contains(text(), '161070481')]";

    //  Open Project
    String projectOpen = "//span[contains (text(),'Apri Progetto')]";

    //  Click on Document Management
    String documentManagement = "//span[contains (text(),'Gestione Elaborati')]";

    //  Click to open the project
    String printManagement = "//span[@id='accordionPaneMenu_7_accordionPaneItem_5_ContentPane_button_title']";

    //  Click on CMT Design + Built
    String CMT = "//div[contains(text(), 'Estrai CMT Progettato + Realizzato')]";

    //  Click on Extract
    String extract = "//input[@id='extract']";

    //  Click on Send button to download the Excel file
    String close = "//span[@class='dojoxFloatingCloseIcon']";

    //  Click on fibre
    String fiber = "//span[contains(text(), 'Fibre e giunzioni')]";

    //  click on AsBuilt
    String Asbuilt = "//span[contains(text(), 'Gestione Stampe AsBuilt')]";

    // Click on button to download the cmtfile
    String invia = "//input[@id='btnSubmit']";
}