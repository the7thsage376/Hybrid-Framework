package ui.base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import ui.pages.DashboardPage;
import ui.pages.LoginPage;
import ui.pages.MyProfilePage;
import utils.BrowserFactory;

public class basePage {
    BrowserFactory browserFactory = new BrowserFactory();

    final WebDriver driver = browserFactory.startBrowser(
        "chrome",
        "https://ndosisimplifiedautomation.vercel.app/#practice"
    );

    LoginPage loginPage = PageFactory.initElements(driver, LoginPage.class);
    DashboardPage dashboardPage = PageFactory.initElements(driver, DashboardPage.class);
    MyProfilePage myProfilePage = PageFactory.initElements(driver, MyProfilePage.class);
}
