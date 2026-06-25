package com.biscience.automation.pages.components;

import com.biscience.automation.pages.BaseElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public abstract class BaseComponent extends BaseElement {

    protected BaseComponent() {
    }

    protected abstract WebElement root();

    protected WebElement getChildElementByXpath(String xpath)  {
        return root().findElement(By.xpath(xpath));
    }

    protected List<WebElement> getChildElementsByXpath(String xpath) {
        return root().findElements(By.xpath(xpath));
    }
}
