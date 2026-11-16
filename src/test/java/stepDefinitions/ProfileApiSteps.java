package stepDefinitions;

import api.base.BaseUri;
import api.requests.RequestBuilder;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.testng.Assert;

public class ProfileApiSteps extends BaseUri {

    public Response response;
    public String UploadedImageId;

    @Given("the user is authenticated with valid credentials")
    public void UserAuthenticated(){
        response = RequestBuilder.loginProfile(BaseUri.USER_EMAIL,BaseUri.USER_PASSWORD);
        Assert.assertEquals(response.getStatusCode(), 200, "Login failed with status code: " + response.getStatusCode());
        Assert.assertNotNull(RequestBuilder.AuthToken, "Auth token was not generated!");
    }

    @And("the user profile exists on the system")
    public void ProfileVerification(){
        response = RequestBuilder.getProfile();
        Assert.assertEquals(response.getStatusCode(), 200, "Profile retrieval failed with status code: " + response.getStatusCode());
        Assert.assertNotNull(response.jsonPath().getString("data.Id"), "User profile does not exist!");

    }

    @When("the user uploads a valid profile image \"profilePicture.png\" via the multipart endpoint")
    public void UploadProfile(){
        response = RequestBuilder.postProfile();
        UploadedImageId = response.jsonPath().getString("data.ProfileImage");
    }

    @Then("the response status code should be 200")
    public void VerifyResponseStatusCode(){
        Assert.assertEquals(response.getStatusCode(), 200, "Expected status code 200, but got: " + response.getStatusCode());
    }

    @And(("the retrieved profile image path should match the newly uploaded image"))
    public void VerifyProfileIsUploaded(){
        response = RequestBuilder.verifyUpdatedProfile();
        Assert.assertEquals(response.getStatusCode(), 200, "Expected status code 200, but got: " + response.getStatusCode());

        // Extract the image path/ID from the GET /profile response
        String retrievedImageId = response.jsonPath().getString("data.ProfileImage");

        Assert.assertNotNull(UploadedImageId, "Uploaded image path/ID was null in POST response!");
        Assert.assertEquals(retrievedImageId, UploadedImageId, "Retrieved profile image does not match the newly uploaded image!");


    }
}
