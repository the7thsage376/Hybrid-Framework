@api @profile
Feature: Profile Picture API Management

  @smoke @regression
  Scenario: Successfully upload and verify user profile image
    Given the user is authenticated with valid credentials
    And the user profile exists on the system
    When the user uploads a valid profile image "profilePicture.png" via the multipart endpoint
    Then the response status code should be 200
    And the retrieved profile image path should match the newly uploaded image