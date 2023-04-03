package org.test.selenium.helpers;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public interface TestDriver {

    public TestElement findElement(By by);

    public WebElement getElement(By by);

    void get(String url);

    String getCurrentUrl();

    void quit();

    void switchTo();

    void switchBack();

    WebDriver getDriver();

    String getText(By by);
}
