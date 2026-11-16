@ui @profile
Feature: User Profile Picture UI Management

  @smoke @regression
  Scenario: Successfully upload and verify a new profile picture via UI
    Given the user is logged into the Ndosi automation test site
    When the user navigates to the Edit Profile page via the menu
    And the user uploads a new profile picture "profilePicture.png"
    Then the profile picture should be updated on the profile page