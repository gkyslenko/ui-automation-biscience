package com.biscience.automation.pages.components;

import com.biscience.automation.utils.WaitUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class ChatBotComponent extends BaseComponent {

    @FindBy(xpath = "//adc-chatbot")
    private WebElement rootElement;

    public ChatBotComponent() {
        super();
    }

    private WebElement history() {
        return getChildElementByXpath(".//chatbot-history");
    }

    private WebElement input() {
        return getChildElementByXpath(".//chatbot-input");
    }

    private WebElement submitBtn() {
        return getChildElementByXpath(".//button[@data-unit='submit-btn']");
    }

    private WebElement closeBtn() {
        return getChildElementByXpath(".//button[@data-unit='close-button']");
    }

    private WebElement userMessage() {
        return getChildElementByXpath(".//chatbot-card[@data-unit='user-message']");
    }

    private WebElement messageThinking() {
        return getChildElementByXpath(".//chatbot-card[@data-message-id='message-thinking']");
    }

    private List<WebElement> suggestions() {
        return getChildElementsByXpath(".//button[@data-unit='prompt-button']");
    }

    @Override
    protected WebElement root() {
        return WaitUtil.waitForVisible(driver, rootElement);
    }

    public boolean isChatbotVisible() {
        return isElementVisible(root());
    }

    public boolean isHistoryVisible() {
        return isElementVisible(history());
    }

    public boolean isInputVisible() {
        return isElementVisible(input());
    }

    public boolean isSubmitButtonVisible() {
        return isElementVisible(submitBtn());
    }

    public boolean isSubmitButtonEnabled(boolean isEnabled) {
        if (isEnabled) {
            return WaitUtil.waitForAttributeContains(driver, submitBtn(), "class", "is-active");
        } else {
            return submitBtn().getAttribute("class").contains("is-active");
        }

    }

    public boolean isCloseButtonVisible() {
        return isElementVisible(closeBtn());
    }

    public boolean isSuggestionsPresent() {
        return !suggestions().isEmpty();
    }

    public boolean isUserMessagePresent() {
        return isElementVisible(userMessage());
    }

    public boolean isAssistantMessagePresent() {
        return isElementVisible(messageThinking());
    }

    public ChatBotComponent clickInput() {
        clickElement(input());
        return this;
    }

    public ChatBotComponent enterText(String text) {
        sendKeys(input().findElement(By.xpath(".//textarea")), text);
        return this;
    }

    public ChatBotComponent clickSubmit() {
        clickElement(submitBtn());
        return this;
    }
}
