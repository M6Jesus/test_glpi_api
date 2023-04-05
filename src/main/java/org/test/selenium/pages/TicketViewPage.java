package org.test.selenium.pages;

import org.test.selenium.helpers.TestDriver;
import org.openqa.selenium.By;

public class TicketViewPage extends GlpiPageObject{
    static String page_url = "http://pamelaanou.infinityfreeapp.com/glpi_10_0_6/front/ticket.form.php?id=";
    //static String page_url = "http://localhost/glpi_10_0_6/front/ticket.form.php?id=";
    public TicketViewPage(TestDriver driver, String id) {
        super(driver, page_url+id);
    }

    public String ticketViewTitre(){
        return driver.getText(By.xpath("/html/body/div[2]/div/div/main/div/div/div[2]/div[2]/div/div[1]/div/div[1]/div[1]/div/div[1]/div/div[2]/div/div/div[2]/div[1]"));
    }

    public String ticketViewDesc(){
        return driver.getText(By.xpath("/html/body/div[2]/div/div/main/div/div/div[2]/div[2]/div/div[1]/div/div[1]/div[1]/div/div[1]/div/div[2]/div/div/div[2]/div[2]/p"));
    }

    public TicketViewPage validationDesc(){
        driver.switchTo();
        driver.findElement(By.tagName("p")).click().sendKeys("ok");
        driver.switchBack();
        return this;
    }

    public TicketViewPage validation(){
        driver.findElement(By.xpath("/html/body/div[2]/div/div/main/div/div/div[2]/div[2]/div/div[1]/div/div[1]/div[1]/div/div[7]/div/div[3]/div/div/div[2]/form/div[2]/button[1]")).click();
        return this;
    }
}
