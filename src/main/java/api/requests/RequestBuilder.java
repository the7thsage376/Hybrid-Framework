package api.requests;

import api.base.BaseUri;
import api.payloads.PayloadBuilder;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.io.File;

public class RequestBuilder extends BaseUri {

    public static String AuthToken;

    // 1. post request of logging in.
    public static Response loginProfile(String email, String password) {

        String apiPath = "/APIDEV/login";
// add a body after payload builder is done
        Response response = RestAssured.given()
                .baseUri(BASE_URL)
                .basePath(apiPath)
                .header("content-type", "application/json")
                .body(PayloadBuilder.loginUserPayload(email, password))
                .log().all()
                .post().prettyPeek()
                .then().extract().response();

        AuthToken = response.jsonPath().getString("data.token");

        return response;

    }

    // 2. get request of getting profile.
    public static Response getProfile() {

        String apiPath = "/APIDEV/profile";

        Response response = RestAssured.given()
                .baseUri(BASE_URL)
                .basePath(apiPath)
                .header("Authorization", "Bearer " + AuthToken)
                .header("content-type", "application/json")
                .log().all()
                .get().prettyPeek()
                .then().extract().response();

        return response;

    }

    // 3. post request of changing the profile picture.
    public static Response postProfile() {

        String apiPath = "/APIDEV/profile/image";

        Response response = RestAssured.given()
                .baseUri(BASE_URL)
                .basePath(apiPath)
                .header("Authorization", "Bearer " + AuthToken)
                .multiPart("profileImage", new File("src/test/resources/testData/profilePicture.png"), "image/png")
                .post().prettyPeek()
                .then().extract().response();

        return response;

    }

    // 4. get request of getting profile to verify the profile picture has changed.
    public static Response verifyUpdatedProfile() {
        return getProfile();
    }
}