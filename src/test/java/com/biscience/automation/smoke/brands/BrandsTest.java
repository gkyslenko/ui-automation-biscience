package com.biscience.automation.smoke.brands;

import com.biscience.automation.base.BaseTest;
import com.biscience.automation.pages.brands.BrandPage;
import io.qameta.allure.*;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

@Feature("Brands Module")
public class BrandsTest extends BaseTest {

    @Test(description = "TC-BRANDS-01: Clicking 'Brands' in sidebar navigates to the Brands landing page")
    public void tc_brands_01_brandsAccessibleViaSidebar() {
        BrandPage brands = homePage.sidebar.goToBrands();

        assertTrue(brands.isOnBrandsPage(),
            "URL should contain /ad-intelligence/brand after clicking sidebar Brands item");
        assertTrue(brands.isSearchInputVisible(),
            "'Search brands' input should be visible on the Brands landing page");
        assertTrue(brands.isRecentBrandsPresent(),
            "There should be at least one report card displayed on the Brands landing page");

        monitor.assertClean();
    }

    @Test(description = "TC-BRANDS-02: Brand search field is functional and returns suggestions")
    public void tc_brands_02_brandsSearchReturnsResult() {
        String searchText = "PepsiCo";

        BrandPage brands = new BrandPage().open();
        brands.clickOnSearchButton().enterSearchText(searchText);

        assertTrue(brands.searchOptionsContains(searchText),
            "Search options should contain the text '" + searchText + "' after entering it in the search field");

        monitor.assertClean();
    }
}
