package cz.project;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Unit test for simple App.
 */
public class PeopleTest {
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
    public void AddingPersonFail_EmptyFamilyNameAndLastName() {
        // Given
        shouldLoginUsingValidCredentials();

        // When
        // Go through menu to People
        WebElement peopleMenu = driver.findElement(By.className("fa-users"));
        peopleMenu.click();
        //WebElement addPeopleMenu = driver.findElement(By.xpath("/html/body/div/aside[1]/section/ul/li[3]/ul/li[2]"));
        //addPeopleMenu.click();
        driver.get("http://digitalnizena.cz/church/PersonEditor.php");

        //Add new person
        WebElement genderSelectElement = driver.findElement(By.name("Gender"));
        Select genderSelect = new Select(genderSelectElement);
        genderSelect.selectByVisibleText("Male");

        WebElement firstNameInput = driver.findElement(By.id("FirstName"));
        firstNameInput.sendKeys("Jan");

        WebElement personSaveButton = driver.findElement(By.id("PersonSaveButton"));
        personSaveButton.click();

        // Then
        //validation error

        WebElement errorMessageSpan = driver.findElement(By.cssSelector(".alert-danger"));
        Assert.assertEquals("×\n" +
                "Invalid fields or selections. Changes not saved! Please correct and try again!", errorMessageSpan.getText());

      //  WebElement errorMessageSpan1 = driver.findElement(By.cssSelector(".col-md-4 .font"));
       // Assert.assertEquals("red", errorMessageSpan1.getText());
    }
    @Test
    public void AddingPerson() {
        // Given
        shouldLoginUsingValidCredentials();

        // When
        // Go through menu to People
        WebElement peopleMenu = driver.findElement(By.className("fa-users"));
        peopleMenu.click();
        //WebElement addPeopleMenu = driver.findElement(By.xpath("/html/body/div/aside[1]/section/ul/li[3]/ul/li[2]"));
        //addPeopleMenu.click();
        driver.get("http://digitalnizena.cz/church/PersonEditor.php");

        //Add new person
        WebElement genderSelectElement = driver.findElement(By.name("Gender"));
        Select genderSelect = new Select(genderSelectElement);
        genderSelect.selectByVisibleText("Male");

        WebElement firstNameInput = driver.findElement(By.id("FirstName"));
        firstNameInput.sendKeys("Jan");

        WebElement personSaveButton = driver.findElement(By.id("PersonSaveButton"));
        personSaveButton.click();

        // Then


    }
}

