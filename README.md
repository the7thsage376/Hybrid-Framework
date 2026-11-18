[![Hybrid API & UI Test Automation Pipeline](https://github.com/the7thsage376/Hybrid-Framework/actions/workflows/hybrid-test-pipeline.yml/badge.svg)](https://github.com/the7thsage376/Hybrid-Framework/actions/workflows/hybrid-test-pipeline.yml)

# Hybrid-Framework

A Java-based hybrid test automation framework for testing both REST API and browser UI flows. It combines **Cucumber BDD**, **TestNG**, **REST Assured**, **Selenium WebDriver**, and **Allure reporting** in a Maven project.

The sample scenarios exercise profile-picture management on the Ndosi Automation test site:

- API authentication and profile retrieval
- Multipart profile-image upload through REST endpoints
- UI login and profile-picture update
- Headless Chrome or Edge execution
- Automated test execution and Allure reports through GitHub Actions

## Technology Stack

| Technology | Purpose |
|---|---|
| Java 21 | Primary implementation language |
| Apache Maven | Dependency management and test execution |
| Cucumber 7 | Gherkin BDD scenarios and step definitions |
| TestNG / Cucumber TestNG | Test runner and scenario execution |
| REST Assured 6 | REST API requests and response assertions |
| Selenium 4 | Browser automation and UI interactions |
| JSON-Simple / Gson | JSON payload and data handling |
| Allure 3 | Test reports and failure screenshots |
| GitHub Actions | CI/CD scheduling, execution, reports, and email notifications |

## Repository Structure

```text
Hybrid-Framework/
├── .github/
│   └── workflows/
│       └── hybrid-test-pipeline.yml   # CI build, Allure report, artifact, and email setup
├── src/
│   ├── main/
│   │   └── java/
│   │       ├── api/
│   │       │   ├── base/
│   │       │   │   └── BaseUri.java       # API/credential configuration and base constants
│   │       │   ├── payloads/
│   │       │   │   └── PayloadBuilder.java# JSON login payload creation
│   │       │   └── requests/
│   │       │       └── RequestBuilder.java# Login, profile GET, and image upload requests
│   │       ├── ui/
│   │       │   ├── base/
│   │       │   │   └── basePage.java      # Shared WebDriver and page-object initialization
│   │       │   └── pages/
│   │       │       ├── LoginPage.java     # Login-page interactions
│   │       │       ├── DashboardPage.java # Dashboard navigation
│   │       │       └── MyProfilePage.java # Profile edit and image-upload interactions
│   │       └── utils/
│   │           ├── BrowserFactory.java    # Chrome/Edge setup and WebDriver lifecycle
│   │           └── ConfigLoader.java      # Loads properties and system properties
│   └── test/
│       ├── java/
│       │   ├── runners/
│       │   │   └── TestRunner.java        # Cucumber + TestNG runner configuration
│       │   └── stepDefinitions/
│       │       ├── ProfileApiSteps.java   # API scenario bindings and assertions
│       │       └── ProfileUiSteps.java    # UI scenario bindings, screenshot, and teardown hooks
│       └── resources/
│           ├── features/
│           │   ├── api/
│           │   │   └── ValidateProfileEndpoints.feature
│           │   └── ui/
│           │       └── UpdateProfilePicture.feature
│           └── testData/
│               └── profilePicture.png     # Image used by API and UI upload scenarios
├── .gitignore
├── pom.xml
└── README.md
```

## How the Framework Works

### API Test Flow

1. `ProfileApiSteps` receives credentials from `BaseUri`.
2. `RequestBuilder.loginProfile(...)` posts credentials to `/APIDEV/login`.
3. The bearer token returned as `data.token` is stored in `RequestBuilder.AuthToken`.
4. `getProfile()` retrieves `/APIDEV/profile` using the bearer token.
5. `postProfile()` uploads `src/test/resources/testData/profilePicture.png` as a PNG multipart file to `/APIDEV/profile/image`.
6. `verifyUpdatedProfile()` retrieves the profile again and confirms that `data.ProfileImage` matches the uploaded image value.

### UI Test Flow

1. `basePage` initializes Selenium and the page objects.
2. `BrowserFactory` opens the practice site in a headless Chrome browser with a 1920 × 1080 window.
3. `LoginPage` enters the configured email and password.
4. `DashboardPage` verifies the welcome message and navigates to My Profile.
5. `MyProfilePage` opens Edit Profile, selects the profile image, and saves it.
6. The scenario accepts the browser alert and verifies `Profile updated successfully!`.
7. Cucumber's UI teardown closes the WebDriver; failed UI steps can attach screenshots to Allure.

## Cucumber Scenarios

### API Scenario

`src/test/resources/features/api/ValidateProfileEndpoints.feature`

- Tagged with `@api` and `@profile`
- Tagged `@smoke` and `@regression`
- Authenticates with valid credentials
- Verifies that the profile exists
- Uploads `profilePicture.png` using the multipart endpoint
- Confirms HTTP 200 responses
- Confirms the returned profile-image path matches the newly uploaded image

### UI Scenario

`src/test/resources/features/ui/UpdateProfilePicture.feature`

- Tagged with `@ui` and `@profile`
- Tagged `@smoke` and `@regression`
- Logs into the Ndosi automation site
- Opens My Profile through the dashboard menu
- Uploads a new profile picture
- Saves the profile and verifies the success alert

## Configuration

Create this local file:

```text
src/test/resources/config/config.properties
```

```properties
base.url=https://www.ndosiautomation.co.za
user.email=YOUR_TEST_EMAIL
user.password=YOUR_TEST_PASSWORD
```

`ConfigLoader` checks Java system properties first and then falls back to `config.properties`. The file is intentionally excluded by `.gitignore` so credentials are not committed.

The following system-property overrides are also supported:

```bash
-Dbase.url=https://www.ndosiautomation.co.za \
-Duser.email=YOUR_TEST_EMAIL \
-Duser.password=YOUR_TEST_PASSWORD
```

> `BaseUri` also declares a fixed UI-adjacent base URL constant, while `basePage` opens `https://ndosisimplifiedautomation.vercel.app/#practice` directly.

## Prerequisites

- JDK 21
- Apache Maven 3.8 or later
- Chrome or Edge installed
- Internet access to the configured application endpoints
- Valid test account credentials

Verify the required tools:

```bash
java -version
mvn -version
```

## Setup and Running Locally

1. Clone the repository:

```bash
git clone https://github.com/the7thsage376/Hybrid-Framework.git
cd Hybrid-Framework
```

2. Create the configuration file:

```bash
mkdir -p src/test/resources/config
```

Add `src/test/resources/config/config.properties` with your test URL and credentials.

3. Run API and UI scenarios:

```bash
mvn clean test
```

The default runner in `TestRunner.java` selects:

```gherkin
@api or @ui
```

### Run Only API Tests

Change the `tags` value in `src/test/java/runners/TestRunner.java`:

```java
tags = "@api"
```

Then run:

```bash
mvn clean test
```

### Run Only UI Tests

Change the runner tag to:

```java
tags = "@ui"
```

Then run:

```bash
mvn clean test
```

### Run a Specific Feature

```bash
mvn clean test -Dcucumber.features=src/test/resources/features/api/ValidateProfileEndpoints.feature
```

## Allure Reporting

Cucumber results are captured through the Allure Cucumber JVM plugin. A local Allure command-line installation can generate and open a report:

```bash
mvn clean test
allure serve allure-results
```

On Windows, the Allure executable can also be run directly, for example:

```powershell
C:\Users\moloi\Desktop\allure-2.38.1\allure-2.38.1\bin\allure.bat serve allure-results
```

Generated files are placed outside version control:

- `allure-results/` — raw test results
- `allure-report/` — generated HTML report
- `target/` — Maven build output

## GitHub Actions Pipeline

The workflow at `.github/workflows/hybrid-test-pipeline.yml` runs:

- On pushes to `main`
- On pull requests targeting `main`
- Daily at `22:00 UTC` (documented in the workflow as 00:00 UTC+2)
- Manually with **workflow_dispatch**

The pipeline:

1. Checks out the repository.
2. Installs Java 21 using Temurin.
3. Creates `src/test/resources/config/config.properties` from GitHub Actions secrets.
4. Runs `mvn clean test`.
5. Generates an Allure HTML report even when tests fail.
6. Archives `allure-report/` as a workflow artifact for seven days.
7. Zips the report and emails the report owner with the run status.

### Required GitHub Secrets

| Secret | Use |
|---|---|
| `BASE_URL` | API base URL |
| `USER_EMAIL` | Test account email |
| `USER_PASSWORD` | Test account password |
| `MAIL_USERNAME` | Gmail account used to send reports |
| `MAIL_PASSWORD` | Gmail application password |

## Browser Behavior

`BrowserFactory` supports `chrome` and `edge`. Both start in headless mode and set the browser window to 1920 × 1080.

Chrome uses Selenium Manager/driver discovery and enables the following environment-oriented options:

- `--no-sandbox`
- `--disable-dev-shm-usage`
- `--headless=new`
- `--remote-allow-origins=*`
- Background networking and update components disabled

The UI base page currently instantiates Chrome by default:

```java
browserFactory.startBrowser("chrome", "https://ndosisimplifiedautomation.vercel.app/#practice");
```

## Framework Components

| Component | Responsibility |
|---|---|
| `BaseUri` | Supplies API base URL and credentials |
| `PayloadBuilder` | Builds JSON login payloads |
| `RequestBuilder` | Centralizes login, profile, and multipart upload calls |
| `basePage` | Initializes the WebDriver and shared page objects |
| `BrowserFactory` | Creates and tears down browser sessions |
| `LoginPage` | Encapsulates login controls |
| `DashboardPage` | Encapsulates dashboard navigation |
| `MyProfilePage` | Encapsulates profile-edit controls and file upload |
| `TestRunner` | Configures Cucumber features, glue code, tags, and Allure plugin |
| `ProfileApiSteps` | Implements API BDD steps |
| `ProfileUiSteps` | Implements UI BDD steps and lifecycle hooks |

## Maven Dependencies

Key dependencies declared in `pom.xml` include:

- `org.testng:testng:7.10.2`
- `org.seleniumhq.selenium:selenium-java:4.51.0`
- `io.rest-assured:rest-assured:6.0.1`
- `io.cucumber:cucumber-java:7.34.3`
- `io.cucumber:cucumber-testng:7.31.0`
- `io.qameta.allure:allure-java-commons:3.1.1`
- `io.qameta.allure:allure-cucumber7-jvm:3.1.1`
- `com.googlecode.json-simple:json-simple:1.1.1`
- `com.google.code.gson:gson:2.13.2`
- `org.apache.maven.plugins:maven-surefire-plugin:3.5.4`

The project compiles with Java 21 source and target settings.

## .gitignore

The repository ignores:

- `.idea/` — IDE metadata
- `src/test/resources/config/config.properties` — local credentials
- `allure-results/` — raw report output
- `target/` — Maven build output

## Maintenance Notes

- `RequestBuilder` is a hybrid API helper, but its name and structure reflect the profile-upload flow implemented today.
- UI and API tests use the same credentials but retrieve them through `ConfigLoader` separately.
- The UI page object currently starts the browser in the base-page constructor, so extending `basePage` immediately creates a browser session.
- The UI test closes the browser only after scenarios tagged `@ui`.
- API test cleanup is not currently handled with a separate Cucumber hook.
- The pipeline's Gmail credentials and target recipient should be reviewed before use in another account.
- Test run state is not reset explicitly before each scenario; this may matter if scenarios are expanded or run in parallel.
