package org.test.selenium.helpers;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class MyTestDriver implements TestDriver {

    private final WebDriver driver;

    public MyTestDriver(WebDriver driver) {
        this.driver = driver;
    }

    public WebDriver getDriver(){
        return this.driver;
    }

    @Override
    public TestElement findElement(By by) {
        WebElement webElt = this.driver.findElement(by);
        scrollToElement(webElt);
        var elt = new TestElement(webElt);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException("Sleep in findElement decorator interrupted", e);
        }
        return elt;
    }

    public WebElement getElement(By by){
        return this.driver.findElement(by);
    }

    @Override
    public void get(String url) {
        this.driver.get(url);
    }

    @Override
    public String getCurrentUrl() {
        return this.driver.getCurrentUrl();
    }

    @Override
    public void quit() {
        this.driver.quit();
    }


    @Override
    public void switchTo() {
        // A modifier pour permettre de choisir l'iframe via un argument passé dans la fonction
        WebElement iframe = driver.findElement(By.cssSelector("iframe[title='Zone de Texte Riche']"));
        this.driver.switchTo().frame(iframe);
    }

    public void switchBack(){
        driver.switchTo().defaultContent();
    }

    public String getText(By by) {
        return driver.findElement(by).getText();
    }
    private void scrollToElement(WebElement element) {
        JavascriptExecutor jse = (JavascriptExecutor)this.driver;
        jse.executeScript("arguments[0].scrollIntoView(true);", element);
    }
}
