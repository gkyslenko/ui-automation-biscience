package com.biscience.automation.base;

import com.biscience.automation.driver.DriverManager;
import com.biscience.automation.listeners.ScreenshotListener;
import com.biscience.automation.pages.HomePage;
import com.biscience.automation.pages.LoginPage;
import com.biscience.automation.utils.BrowserMonitor;
import com.biscience.automation.utils.CookieManager;
import io.qameta.allure.Step;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;

import static com.biscience.automation.config.ConfigReader.PROPERTIES;

@Listeners(ScreenshotListener.class)
public abstract class BaseTest {

    protected LoginPage loginPage;
    protected HomePage homePage;
    protected BrowserMonitor monitor;

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        DriverManager.init();
        monitor = new BrowserMonitor();
        loginPage = new LoginPage();
        String username = PROPERTIES.getProperty("base.username");
        homePage = loginAndSave(username);
//        homePage = CookieManager.hasSavedCookies(username) ? restoreSession(username) : loginAndSave(username);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        DriverManager.quit();
    }

    @Step("Login via UI and save session cookies for {username}")
    private HomePage loginAndSave(String username) {
        String password = PROPERTIES.getProperty("base.password");

        HomePage home = loginPage
            .open()
            .enterUsername(username)
            .enterPassword(password)
            .submitAndExpectSuccess();

        CookieManager.save(username, DriverManager.getDriver());
        return home;
    }

    @Step("Restore session from saved cookies for {username}")
    private HomePage restoreSession(String username) {
        String baseUrl = PROPERTIES.getProperty("base.url");
        CookieManager.inject(username, DriverManager.getDriver(), baseUrl);

        HomePage home = new HomePage().open();

        if (loginPage.isOnLoginPage()) {
            CookieManager.clear(username);
            return loginAndSave(username);
        }

        return home;
    }
}

