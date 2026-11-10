@api @profile
Feature: Profile Picture API Management

  Background:
    Given the API base request specification is configured

  @smoke @regression
  Scenario: Successfully upload and verify user profile image
    Given the user is authenticated with valid credentials:
      | email                | password      |
      | testing007@gmail.com | @password1234 |
    And the user profile exists on the system
    When the user uploads a valid profile image "avatar.png" via the multipart endpoint
    Then the response status code should be 200
    And the response body message should be "Profile image uploaded successfully"
    And the retrieved profile image path should match the newly uploaded image