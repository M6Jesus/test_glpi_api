package org.test.selenium;

import org.test.selenium.helpers.MyTestDriver;
import org.junit.*;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.chrome.ChromeDriver;
import org.test.selenium.pages.*;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

@FixMethodOrder (MethodSorters.NAME_ASCENDING)
public class TestCreerTicket {
    private TicketPage page = null;
    private ChromeDriver driver = null;

    HomePage homePage;
    LoginPage loginPage;
    TicketPage ticketPage;
    TicketViewPage ticketViewPage;

    TicketListPage ticketListPage;
    String login;
    String password;


    public HomePage login(String login, String password){
        return new LoginPage(new MyTestDriver(driver)).gotoPage().setUser(login).setPassword(password).clickLoginButton();
    }

    public HomePage creerTicket(String titre, String desc) throws InterruptedException {
        Thread.sleep(20000);
        this.ticketPage.enterTitle(titre)
                .enterDate()
                .chooseType();
        Thread.sleep(10000);
        this.ticketPage.enterDesc(desc);
        this.ticketPage.validation();
        Thread.sleep(5000);
        this.ticketPage.userValidation();
        Thread.sleep(5000);
        return this.ticketPage.createTicket();
    }
    @BeforeClass
    public static void setupWebdriverChromeDriver() {
        System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/src/test/resources/chromedriver/chromedriver.exe");
    }
    @Before
    public void setup() {
        this.driver = new ChromeDriver();
    }

    @After
    public void tearDown() {
        Optional.ofNullable(this.homePage).map(GlpiPageObject::close);
    }
    @Test
    public void a_loginTest(){
        this.password = "toto";
        this.login ="RH_01";
        this.homePage = login(this.login, this.password);
        assertEquals("Résultat du test :", homePage.getExpectedUrl(), homePage.getCurrentUrl());
    }

    @Test
    public void b_wrongLoginTest(){
        this.password = "WRONG";
        this.login ="WRONG";
        this.homePage = login(this.login, this.password);
        assertNotEquals("Résultat du test :", homePage.getExpectedUrl(), homePage.getCurrentUrl());
    }
    @Test
    public void c_creerTicketTest() throws InterruptedException {
        this.password = "toto";
        this.login ="RH_01";
        this.homePage = login(this.login, this.password);
        String titre = "test id al\u00E9atoire :"+Math.random();
        String desc = "ma description";
        this.ticketPage = this.homePage.createTicket();
        this.homePage = creerTicket(titre, desc);
        this.ticketListPage = this.homePage.clickTicket();
        this.ticketViewPage = this.ticketListPage.chooseLastTicket();
        Thread.sleep(10000);
        String ticketViewTitre = this.ticketViewPage.ticketViewTitre();
        String ticketViewDesc = this.ticketViewPage.ticketViewDesc();
        assertEquals("TEST Titre",titre,ticketViewTitre);
        assertEquals("TEST Desc",desc,ticketViewDesc);
        assertEquals("TEST 3:", ticketViewPage.getExpectedUrl(), ticketViewPage.getCurrentUrl());
    }
    @Test
    public void d_validationTest() throws InterruptedException {
        this.password = "toto";
        this.login ="MP_02";
        this.homePage = login(this.login, this.password);
        this.ticketListPage = this.homePage.clickTicket();
        this.ticketViewPage = this.ticketListPage.chooseLastTicket();
        Thread.sleep(15000);
        this.ticketViewPage.validationDesc();
        this.ticketViewPage.validation();
        assertEquals("Résultat du test :", this.ticketViewPage.getExpectedUrl(), this.ticketViewPage.getCurrentUrl());
    }
}
