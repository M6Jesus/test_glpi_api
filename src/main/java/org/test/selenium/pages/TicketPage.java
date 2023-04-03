package org.test.selenium.pages;

import org.test.selenium.helpers.TestDriver;
import org.openqa.selenium.By;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TicketPage extends GlpiPageObject{
    private static final String PAGE_URL = "http://pamelaanou.infinityfreeapp.com/glpi_10_0_6/front/ticket.form.php";

    public TicketPage(TestDriver driver) {
        super(driver, PAGE_URL);
    }

    public TicketPage enterTitle(String titre){
        this.driver.findElement(By.cssSelector("input[name='name']")).click().sendKeys(titre);
        return this;
    }

    public TicketPage enterDesc(String desc){
        driver.switchTo();
        driver.findElement(By.tagName("p")).click().sendKeys(desc);
        driver.switchBack();
        return this;
    }
    public TicketPage enterDate(){
        driver.findElement(By.cssSelector("i[role='button']")).click();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("uuuu-MM-dd hh:mm:ss");
        driver.findElement(By.xpath("/html/body/div[5]/div[2]/div[2]/div[2]/div/span[26]['" + dtf.format(LocalDateTime.now()) + "']")).click();
        return this;
    }
    public TicketPage chooseType(){
        this.driver.findElement(By.xpath("/html/body/div[2]/div/div/main/div/div/div[2]/div/div/div/div/form/div/div[2]/div/div/div/div/div[3]/div/select/option[2]")).click();
        return this;
    }

    public TicketPage validation(){
        this.driver.findElement(By.xpath("/html/body/div[2]/div/div/main/div/div/div[2]/div/div/div/div/form/div[1]/div[2]/div/div[1]/div/div/div[11]/div/select/option[2]")).click();
        return this;
    }

    public TicketPage userValidation() throws InterruptedException {
        this.driver.findElement(By.xpath("/html/body/div[2]/div/div/main/div/div/div[2]/div/div/div/div/form/div[1]/div[2]/div/div[1]/div/div/div[11]/div/span[2]/div/span/span[1]/span/span[1]/span")).click();
        Thread.sleep(15000);
        this.driver.findElement(By.xpath("/html/body/span/span/span[2]/ul/li[4]")).click();
        return this;
    }

    public HomePage createTicket(){
        this.driver.findElement(By.xpath("/html/body/div[2]/div/div/main/div/div/div[2]/div/div/div/div/form/div[2]/div/div[2]/span[2]/button/span")).click();
        return new HomePage(this.driver);
    }
}
