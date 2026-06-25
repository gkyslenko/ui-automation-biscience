# ui-automation-biscience

Selenium-based UI smoke / healthcheck suite for **AdClarity** (BiScience).  
Tests are written in Java with TestNG, results are reported via Allure.

---

## Tech Stack

| Tool | Version |
|---|---|
| Java | 17 |
| Selenium | 4.21.0 |
| TestNG | 7.10.2 |
| Allure TestNG | 2.27.0 |
| AspectJ Weaver | 1.9.22 |
| Awaitility | 4.2.2 |
| Apache Commons Lang3 | 3.14.0 |
| Lombok | 1.18.42 |
| Maven | 3.x |

---

## Project Structure

```
src/
├── main/java/com/biscience/automation/
│   ├── config/         # ConfigReader – loads environment .properties
│   ├── driver/         # DriverFactory, DriverManager – WebDriver lifecycle
│   ├── enums/          # Endpoints, Environment
│   ├── listeners/      # ScreenshotListener – auto-captures on failure
│   ├── pages/          # Page Objects (BasePage, LoginPage, HomePage, …)
│   │   ├── brands/     # BrandPage
│   │   └── components/ # BaseComponent, SidebarComponent, ChatBotComponent
│   └── utils/          # WaitUtil, ScreenshotUtil, BrowserMonitor, CookieManager, RandomUtil
│
└── test/
    ├── java/com/biscience/automation/
    │   ├── base/       # BaseTest – setup, teardown, login
    │   └── smoke/
    │       ├── brands/ # BrandsTest
    │       └── chatbot/# ChatbotTest
    └── resources/
        ├── config/     
        ├── testng.xml
        ├── allure.properties
        └── logback-test.xml
```

---

## Configuration

Create a `passwords.properties` file alongside the environment config file (git-ignored):

```properties
# src/test/resources/config/passwords.properties
base.password=your_password_here
```

Environment config (`staging.properties` example):

```properties
base.url=https://stg-ui.adcint.com
base.username=user@example.com
browser=chrome
headless=false
explicit.wait=15
screenshot.dir=test-output/screenshots
```

---

## Running Tests

### Run full smoke suite (staging, headed)
```bash
mvn clean test
```

### Run headless
```bash
mvn clean test -Pheadless
```

### Run against production
```bash
mvn clean test -Pproduction
```

### Run specific browser
```bash
mvn clean test -Dbrowser=firefox
mvn clean test -Dbrowser=edge
```

### Run a specific test class
```bash
mvn clean test -Dtest=BrandsTest
```

---

## Test Suite

Defined in `src/test/resources/testng.xml`.

### Brands Module (`BrandsTest`)

| ID | Description |
|---|---|
| TC-BRANDS-01 | Clicking 'Brands' in sidebar navigates to the Brands landing page |
| TC-BRANDS-02 | Brand search field is functional and returns suggestions |

### AI Chatbot (`ChatbotTest`)

| ID | Description |
|---|---|
| TC-CHAT-01 | Chatbot panel opens from top navigation |
| TC-CHAT-02 | Submitting a question triggers a response |

---

## Allure Report

### Serve report from latest results
```bash
mvn allure:serve
```

### Generate static report
```bash
mvn allure:report
# open target/site/allure-maven-plugin/index.html
```

Screenshots are automatically attached to failed tests in the report.

---

## Key Features

- **Page Object Model** with `@FindBy` annotations and `PageFactory`
- **Auto-screenshot on failure** via `ScreenshotListener` → attached to Allure report and saved to `test-output/screenshots/`
- **Browser console / HTTP error monitoring** via `BrowserMonitor` — each test calls `monitor.assertClean()` to catch JS errors and 4xx/5xx responses
- **Session cookie reuse** via `CookieManager` to skip login on repeat runs (opt-in)
- **Explicit waits** throughout via `WaitUtil` — no `Thread.sleep()`
- **Multi-environment** support via Maven profiles (`staging` default, `production`, `headless`)
- **Multi-browser** support: Chrome, Firefox, Edge
