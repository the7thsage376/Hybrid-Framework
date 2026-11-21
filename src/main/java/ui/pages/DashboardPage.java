package ui.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class DashboardPage {

    WebDriver driver;
    WebDriverWait wait;

    public DashboardPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }
// Add the locator for asserting we're on the dashboard page here
    @FindBy(xpath = "//*[contains(., 'Welcome')]")
    WebElement welcomeMessage;

    @FindBy(xpath = "//button//span[text() = 'Menu']")
    WebElement menuButton;

    @FindBy(xpath = "//button//span[text() = 'My Profile']")
    WebElement myProfileButton;

    public boolean IsWelcomeMessageDisplayed() {
        return wait.until(ExpectedConditions.visibilityOf(welcomeMessage)).isDisplayed();
    }

    public void ClickMenuButton() {
        wait.until(ExpectedConditions.elementToBeClickable(menuButton)).click();
    }

    public void ClickMyProfileButton() {
        wait.until(ExpectedConditions.elementToBeClickable(myProfileButton)).click();
    }

    public void navigateToMyProfile() {
        ClickMenuButton();
        ClickMyProfileButton();
    }

}
