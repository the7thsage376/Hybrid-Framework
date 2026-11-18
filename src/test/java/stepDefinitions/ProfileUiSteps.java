package stepDefinitions;

import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.Assert;
import ui.base.basePage;
import utils.BrowserFactory;
import utils.ConfigLoader;


public class ProfileUiSteps extends basePage {

    @Given("the user is logged into the Ndosi automation test site")
        public void the_user_is_logged_into_the_Ndosi_automation_test_site() {
            loginPage.loginToNdosi(
                    ConfigLoader.getProperty("user.email"),
                    ConfigLoader.getProperty("user.password")
            );
            Assert.assertTrue(dashboardPage.IsWelcomeMessageDisplayed(), "Expected to be on the dashboard page");

    }

    @When("the user navigates to the Edit Profile page via the menu")
    public void the_user_navigates_to_the_Edit_Profile_page_via_the_menu() {
        dashboardPage.navigateToMyProfile();
    };

    @And("the user uploads a new profile picture \"profilePicture.png\"")
    public void the_user_uploads_a_new_profile_picture() {
        myProfilePage.ClickEditProfileButton();
        myProfilePage.uploadProfilePicture("src/test/resources/testData/profilePicture.png");
        myProfilePage.ClickSaveProfileButton();
    }

    @Then("the profile picture should be updated on the profile page")
        public void the_profile_picture_should_be_updated_on_the_profile_page() {
            String alertMessage = myProfilePage.getAlertTextAndAccept();
            Assert.assertEquals(alertMessage, "Profile updated successfully!", "Expected alert message to indicate successful");
        };

    // C:\Users\moloi\Desktop\allure-2.38.1\allure-2.38.1\bin\allure.bat serve allure-results



    @AfterStep("@ui")
    public void addScreenshots(Scenario scenario) {
        if (scenario.isFailed() && BrowserFactory.driver != null) {
            byte[] screenshots = ((TakesScreenshot) BrowserFactory.driver).getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshots, "image/png", "Failure Screenshot");
        }
    }

    @After("@ui")
    public void tearDown() {
        if (BrowserFactory.driver != null) {
            BrowserFactory.quitDriver();
        }
    }
}
