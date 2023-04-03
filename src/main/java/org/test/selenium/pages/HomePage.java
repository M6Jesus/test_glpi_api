package org.test.selenium.pages;

import org.test.selenium.helpers.TestDriver;
import org.openqa.selenium.By;

public class HomePage extends GlpiPageObject {

    private static String PAGE_URL = "http://pamelaanou.infinityfreeapp.com/glpi_10_0_6/front/central.php";

    public TicketPage createTicket() {
        driver.findElement(By.xpath("//*[@title='Assistance']")).click();
        driver.findElement(By.xpath("//*[@title='Cr\u00E9er un ticket']")).click();
        return new TicketPage(driver);
    }

    public TicketListPage clickTicket() throws InterruptedException {
        driver.findElement(By.xpath("//*[@title='Assistance']")).click();
        driver.findElement(By.xpath("//*[@title='Tickets']")).click();
        return new TicketListPage(driver);
    }

    public HomePage(TestDriver driver) {
        super(driver, PAGE_URL);
    }

}
