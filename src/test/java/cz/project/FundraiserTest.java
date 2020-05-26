package cz.project;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class FundraiserTest {
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

        //driver.close();
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
    public void creatingFundRaiser() {
        // GIVEN
        shouldLoginUsingValidCredentials();

        // WHEN
        driver.get("http://digitalnizena.cz/church/FundRaiserEditor.php?FundRaiserID=-1");

        // THEN
        // Create Fund Raiser
        WebElement donationDateInput = driver.findElement(By.name("Date"));
        donationDateInput.click();
        donationDateInput.clear();
        donationDateInput.sendKeys("2020-05-27");

        WebElement inputTitle = driver.findElement(By.id("Title"));
        String uuid = UUID.randomUUID().toString();
        inputTitle.sendKeys("Oblečení " + uuid);

        WebElement inputDescription = driver.findElement(By.id("Description"));
        inputDescription.sendKeys("Fund raiser oblečení");

        WebElement clickSaveFundRaiser = driver.findElement(By.name("FundRaiserSubmit"));
        clickSaveFundRaiser.click();


    }



}