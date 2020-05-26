package cz.project;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Unit tests for simple App ChurchCRM.
 */
public class LoginTest {
    private ChromeDriver driver;

    public String churchUrl = "http://digitalnizena.cz/church/";
    public String userName = "church";
    public String validPassword = "church12345";

    @Before
    public void setup() {
        ChromeOptions cho = new ChromeOptions();

        System.setProperty("webdriver.chrome.driver", "src/test/resources/drivers/chromedriver.exe");
        driver = new ChromeDriver(cho);
        driver.manage().window().maximize();
    }

    @After
    public void tearDown() {
        driver.close();


    }
    @Test
    public void shouldLoginUsingValidCredentials() {
        // given
        driver.get(churchUrl);

        // when
        WebElement usernameInput = driver.findElement(By.id("UserBox"));
        usernameInput.sendKeys(userName);
        WebElement passwordInput = driver.findElement(By.id("PasswordBox"));
        passwordInput.sendKeys(validPassword);
        WebElement loginButton = driver.findElement(By.className("btn-primary"));
        loginButton.click();

        // Then
        assertEquals("http://digitalnizena.cz/church/Menu.php", driver.getCurrentUrl());
        assertEquals("ChurchCRM: Welcome to", driver.getTitle());
        assertTrue(driver.findElements(By.id("Login")).isEmpty());

    }



    @Test
    public void shouldNotLoginInvalidCredentials_userStaysAtLoginPage_Fail() {
        // given
        driver.get(churchUrl);

        // when
        WebElement usernameInput = driver.findElement(By.id("UserBox"));
        usernameInput.sendKeys("invalidlogin");
        WebElement passwordInput = driver.findElement(By.id("PasswordBox"));
        passwordInput.sendKeys("invalidpass");
        WebElement loginButton = driver.findElement(By.className("btn-primary"));
        loginButton.click();

        // then
        // validation error exists
        WebElement errorMessageSpan = driver.findElement(By.id("spanMessage"));
        Assert.assertEquals("Invalid credentials", errorMessageSpan.getText());
    }




    @Test
    public void shouldNotLoginInvalidCredentials_userStaysAtLoginPage_Pass() {
        // given
        driver.get(churchUrl);

        // when
        WebElement usernameInput = driver.findElement(By.id("UserBox"));
        usernameInput.sendKeys("invalidlogin");
        WebElement passwordInput = driver.findElement(By.id("PasswordBox"));
        passwordInput.sendKeys("invalidpass");
        WebElement loginButton = driver.findElement(By.className("btn-primary"));
        loginButton.click();

        // then
        // validation error exists
        Assert.assertTrue(driver.getTitle().contentEquals("Login"));
        WebElement loginForm = driver.findElement(By.className("login-box-body"));
        Assert.assertTrue(loginForm.isDisplayed());



    }


    public void login(ChromeDriver driver) {
        // given
        driver.get(churchUrl);

        // when
        WebElement usernameInput = driver.findElement(By.id("UserBox"));
        usernameInput.sendKeys(userName);
        WebElement passwordInput = driver.findElement(By.id("PasswordBox"));
        passwordInput.sendKeys(validPassword);
        WebElement loginButton = driver.findElement(By.className("btn-primary"));
        loginButton.click();
    }

}
