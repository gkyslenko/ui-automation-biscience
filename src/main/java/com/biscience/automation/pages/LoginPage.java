package com.biscience.automation.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static com.biscience.automation.enums.Endpoints.LOGIN;
import static java.net.HttpURLConnection.HTTP_OK;
import static org.testng.Assert.assertEquals;

public class LoginPage extends BasePage<LoginPage> {

    @Override
    protected String getEndpoint() {
        return LOGIN.getPath();
    }

    @FindBy(id = "email")
    private WebElement usernameInput;

    @FindBy(id = "password")
    private WebElement passwordInput;

    @FindBy(xpath = "//button[@type='submit']")
    private WebElement submitButton;

    public LoginPage enterUsername(String username) {
        sendKeys(usernameInput, username);
        return this;
    }

    public LoginPage enterPassword(String password) {
        passwordInput.clear();
        passwordInput.sendKeys(password);
        return this;
    }

    public HomePage submitAndExpectSuccess() {
        int status = waitForApiResponse(
                "/api/rest/v2/security/tokens/access",
                submitButton::click
        );
        assertEquals(status, HTTP_OK);
        return new HomePage();
    }

    public boolean isOnLoginPage() {
        return currentUrl().contains(LOGIN.getPath());
    }
}

