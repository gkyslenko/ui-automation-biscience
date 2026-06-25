package com.biscience.automation.pages.components;

import com.biscience.automation.pages.brands.BrandPage;
import com.biscience.automation.utils.WaitUtil;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static java.net.HttpURLConnection.HTTP_OK;
import static org.testng.Assert.assertEquals;

public class SidebarComponent extends BaseComponent {

    @FindBy(xpath = "//mat-drawer")
    private WebElement rootElement;

    @FindBy(xpath = "//mat-drawer//div[@class = 'title' and text()='Brands']")
    private WebElement brands;

    @FindBy(xpath = "//mat-drawer//a[contains(@class, 'activate-chatbot-button')]")
    private WebElement askAnything;

    public SidebarComponent() {
        super();
    }

    @Override
    protected WebElement root() {
        return WaitUtil.waitForVisible(driver, rootElement);
    }

    public BrandPage goToBrands() {
        int status = waitForApiResponse("/api/rest/v2/datasets/search?entityType=BRAND",
                () -> clickElement(brands));
        assertEquals(status, HTTP_OK);
        return new BrandPage();
    }

    public ChatBotComponent openChatbot() {
        clickElement(askAnything);
        return new ChatBotComponent();
    }
}
