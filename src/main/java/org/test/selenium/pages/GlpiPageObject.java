package org.test.selenium.pages;

import org.test.selenium.helpers.TestDriver;

public abstract class GlpiPageObject {

    protected final TestDriver driver;
    private final String page_url;

    protected GlpiPageObject(TestDriver driver, String page_url) {
        this.driver = driver;
        this.page_url = page_url;
    }

    public final String getCurrentUrl() {
        return this.driver.getCurrentUrl();
    }

    public final String getExpectedUrl() {
        return this.page_url;
    }

    public final GlpiPageObject close() {
        this.driver.quit();
        return this;
    }
}
