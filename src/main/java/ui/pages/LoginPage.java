package ui.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {

    WebDriver driver;
    WebDriverWait wait;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }


    @FindBy(xpath = "//span[contains(text(), 'Login')]")
    WebElement loginButton;

    @FindBy(css = "#login-email")
    WebElement loginEmail;

    @FindBy(css = "#login-password")
    WebElement loginPassword;

    @FindBy(css ="#login-submit")
    WebElement loginSubmit;

    public void ClickLoginButton() {
        wait.until(ExpectedConditions.elementToBeClickable(loginButton)).click();
    }

    public void EnterLoginEmail(String email) {
        wait.until(ExpectedConditions.elementToBeClickable(loginEmail)).sendKeys(email);
    }

    public void EnterLoginPassword(String password) {
        wait.until(ExpectedConditions.elementToBeClickable(loginPassword)).sendKeys(password);
    }

    public void ClickLoginSubmit() {
        wait.until(ExpectedConditions.elementToBeClickable(loginSubmit)).click();
    }

    public void loginToNdosi(String email, String password){
        ClickLoginButton();
        EnterLoginEmail(email);
        EnterLoginPassword(password);
        ClickLoginSubmit();

    }

}
