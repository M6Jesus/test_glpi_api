package org.test.selenium.helpers;

import org.openqa.selenium.WebElement;

public class TestElement {

    private final WebElement element;

    public TestElement(WebElement element) {
        this.element = element;
    }

    public WebElement click() {
        this.element.click();
        return this.element;
    }

    public WebElement sendKeys(String keys) {
        this.element.sendKeys(keys);
        return this.element;
    }
}
