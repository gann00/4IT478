package cz.project;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Select;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class EventTest {
    private ChromeDriver driver;

    @Before
    public void setup() {
        ChromeOptions cho = new ChromeOptions();

        System.setProperty("webdriver.chrome.driver", "src/test/resources/drivers/chromedriver.exe");
//        ChromeDriverService service = new ChromeDriverService()
        driver = new ChromeDriver(cho);
        driver.manage().window().maximize();
    }

    @After
    public void tearDown() {
//        driver.close();
    }

    @Test
    public void shouldLoginUsingValidCredentials() {
        // given
        driver.get("http://digitalnizena.cz/church/");

        // when
        WebElement usernameInput = driver.findElement(By.id("UserBox"));
        usernameInput.sendKeys("church");
        WebElement passwordInput = driver.findElement(By.id("PasswordBox"));
        passwordInput.sendKeys("church12345");
        WebElement loginButton = driver.findElement(By.className("btn-primary"));
        loginButton.click();
        // Then
        assertEquals("http://digitalnizena.cz/church/Menu.php", driver.getCurrentUrl());
        assertEquals("ChurchCRM: Welcome to", driver.getTitle());
        assertTrue(driver.findElements(By.id("Login")).isEmpty());

    }

    @Test
    public void event() {
        // GIVEN
        shouldLoginUsingValidCredentials();

        // WHEN
        driver.get("http://digitalnizena.cz/church/EventEditor.php");


        // Event type selection
        WebElement eventButton = driver.findElement(By.id("event_type_id"));
        eventButton.click();

        // Filling in details
        WebElement eventType = driver.findElement(By.cssSelector("#event_type_id > option:nth-child(2)"));
        eventType.click();

        WebElement eventTitle = driver.findElement(By.name("EventTitle"));
        eventTitle.click();
        eventTitle.clear();
        eventTitle.sendKeys("VŠE");

        WebElement eventDesc = driver.findElement(By.name("EventDesc"));
        eventDesc.click();
        eventDesc.sendKeys("Vysoká škola ekonomická");

        // vyplnit něco do rich text edotoru,


        // v gridu ověřit přidání záznamu,

        // rozklidknout editaci nově přidaného záznamu

        // a ověřit že v richtext editoru je hodnota
    }

}
