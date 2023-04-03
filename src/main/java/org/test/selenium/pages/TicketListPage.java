package org.test.selenium.pages;

import org.test.selenium.helpers.TestDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;

public class TicketListPage extends GlpiPageObject{

    private static String PAGE_URL = "http://pamelaanou.infinityfreeapp.com/glpi_10_0_6/front/ticket.php";
    public TicketListPage(TestDriver driver) {
        super(driver, PAGE_URL);
    }

    public TicketViewPage chooseLastTicket(){
        String id = driver.getText(By.xpath("/html/body/div[2]/div/div/main/div/div[2]/div/form/div/div[2]/table/tbody/tr[1]/td[2]/span"));
        //driver.findElement(By.xpath("//a[@id='Ticket"+id+"']")).click();
        try {
            JavascriptExecutor executor = (JavascriptExecutor) driver.getDriver();
            executor.executeScript("arguments[0].click();", driver.getElement(By.xpath("//a[@id='Ticket" + id + "']")));
        } catch (org.openqa.selenium.StaleElementReferenceException ex){
            JavascriptExecutor executor = (JavascriptExecutor) driver.getDriver();
            executor.executeScript("arguments[0].click();", driver.getElement(By.xpath("//a[@id='Ticket" + id + "']")));
        }
        return new TicketViewPage(driver,id);
    }
}
