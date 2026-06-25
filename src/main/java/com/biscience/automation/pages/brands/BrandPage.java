package com.biscience.automation.pages.brands;

import com.biscience.automation.pages.BasePage;
import com.biscience.automation.utils.WaitUtil;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

import static com.biscience.automation.enums.Endpoints.BRAND_PAGE;

public class BrandPage extends BasePage<BrandPage> {

    @Override
    protected String getEndpoint() {
        return BRAND_PAGE.getPath();
    }

    @FindBy(xpath = "//report-card")
    private List<WebElement> reportCards;

    @FindBy(xpath = "//mat-option[@data-unit='search-popup-item']")
    private List<WebElement> searchOptions;

    @FindBy(xpath = "//input[@data-unit='search-input']")
    private WebElement searchInput;

    public boolean isOnBrandsPage() {
        return currentUrl().contains(BRAND_PAGE.getPath());
    }

    public boolean isSearchInputVisible() {
        return isElementVisible(searchInput);
    }

    public boolean isRecentBrandsPresent() {
        return !reportCards.isEmpty();
    }

    public boolean searchOptionsContains(String searchText) {
        return WaitUtil.awaitUntilTrue(() ->
                searchOptions.stream().anyMatch(option -> option.getText().contains(searchText)));
    }

    public BrandPage clickOnSearchButton() {
        clickElement(searchInput);
        return this;
    }

    public BrandPage enterSearchText(String text) {
        sendKeys(searchInput, text);
        return this;
    }
}
