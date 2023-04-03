package org.test.selenium.helpers;

import org.openqa.selenium.WebDriver;
import org.test.selenium.pages.HomePage;
import org.test.selenium.pages.LoginPage;
import org.test.selenium.helpers.MyTestDriver;

public class Helpers {

    public static HomePage login(WebDriver driver, String username, String password) {
        return new LoginPage(new MyTestDriver(driver))
                .gotoPage()
                .setUser(username)
                .setPassword(password)
                .clickLoginButton();
    }
}
