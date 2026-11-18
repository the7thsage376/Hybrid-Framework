package ui.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;

public class MyProfilePage {

    WebDriver driver;
    WebDriverWait wait;

    public MyProfilePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//button[contains(text(), 'Edit Profile')]")
    WebElement editProfileButton;

    @FindBy(id = "profilePicture")
    WebElement profilePictureInput;

    @FindBy(xpath = "//button[contains(text(), 'Save')]")
    WebElement saveProfileButton;



    public void ClickEditProfileButton() {
        wait.until(ExpectedConditions.elementToBeClickable(editProfileButton)).click();

    }

    public void ClickSaveProfileButton() {
        wait.until(ExpectedConditions.elementToBeClickable(saveProfileButton)).click();
    }

    public void uploadProfilePicture(String relativeFilePath) {
        String absolutePath = new File(relativeFilePath).getAbsolutePath();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("profilePicture")));
        profilePictureInput.sendKeys(absolutePath);
    }

    public String getAlertTextAndAccept() {
        org.openqa.selenium.Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        String alertMessage = alert.getText();
        alert.accept();
        return alertMessage;
    }


}
