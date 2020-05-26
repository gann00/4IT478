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
    private LoginTest loginTest = new LoginTest();

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
    public void addingPersonFail() {
        //Test for adding Person with empty Family and empty Last name
        // Given
        loginTest.login(driver);

        // When
        // Go through menu to People
        WebElement peopleMenu = driver.findElement(By.className("fa-users"));
        peopleMenu.click();
        //WebElement addPeopleMenu = driver.findElement(By.cssSelector("li.menu-open ul > li:nth-child(2)"));
        //addPeopleMenu.click();
        driver.get("http://digitalnizena.cz/church/PersonEditor.php");

        //Add new person
        WebElement genderSelectElementa = driver.findElement(By.name("Gender"));
        Select gendersSelect = new Select(genderSelectElementa);
        gendersSelect.selectByVisibleText("Male");

        WebElement firstName = driver.findElement(By.id("FirstName"));
        firstName.sendKeys("Kate");

        WebElement personSaveButton = driver.findElement(By.id("PersonSaveButton"));
        personSaveButton.click();

        // Then
        //validation error
        WebElement errorMessageSpan = driver.findElement(By.cssSelector(".alert-danger"));
        Assert.assertEquals("×\n" +
                "Invalid fields or selections. Changes not saved! Please correct and try again!", errorMessageSpan.getText());
        WebElement errorMessageSpan1 = driver.findElementByCssSelector(".col-md-4 > font");
        Assert.assertEquals("You must enter a Last Name if no Family is selected.", errorMessageSpan1.getText());
        driver.close();
    }

    @Test
    public void addingFamily() {
        // Given
        loginTest.login(driver);
        // When
        // Adding Family
        driver.get("http://digitalnizena.cz/church/FamilyEditor.php");
        WebElement familyName = driver.findElement(By.id("FamilyName"));
        familyName.sendKeys("Smith");

        WebElement cityInput = driver.findElement(By.name("City"));
        cityInput.sendKeys("Prague");

        WebElement personSaveButton = driver.findElement(By.name("FamilySubmit"));
        personSaveButton.click();
    }

    @Test
    public void addingPerson() {
        // Given
        loginTest.login(driver);

        // When
        //Adding Person with family and with empty Last name
        driver.get("http://digitalnizena.cz/church/PersonEditor.php");

        WebElement genderSelectElement = driver.findElement(By.name("Gender"));
        Select genderSelect = new Select(genderSelectElement);
        genderSelect.selectByVisibleText("Female");

        WebElement firstName = driver.findElement(By.id("FirstName"));
        firstName.sendKeys("Jane");

        WebElement familyNameSelectElement = driver.findElement(By.id("famailyId"));
        Select familyNameSelect = new Select(familyNameSelectElement);
        familyNameSelect.selectByValue("12");

        WebElement personSave = driver.findElement(By.id("PersonSaveButton"));
        personSave.click();

        //Check detail of added person
        WebElement nameTask = driver.findElement(By.className("profile-username"));
        Assert.assertTrue(nameTask.getText().equals("Jane Smith"));

        //Edit information of added person
        WebElement personEditButton = driver.findElement(By.id("EditPerson"));
        personEditButton.click();

        WebElement familyRole = driver.findElement(By.name("FamilyRole"));
        Select familyRoleSelect = new Select(familyRole);
        familyRoleSelect.selectByValue("1");

        WebElement email = driver.findElement(By.name("Email"));
        email.sendKeys("janesmith@email.com");

        WebElement personEditSave = driver.findElement(By.id("PersonSaveButton"));
        personEditSave.click();
        driver.close();
    }
    @Test
    public void editsSaved() {
        // Given
        loginTest.login(driver);
        //Check if edits were saved
        driver.get("http://digitalnizena.cz/church/PersonView.php?PersonID=144");
        WebElement emailSaved= driver.findElement(By.cssSelector("body > div > div.content-wrapper > section.content > div.row > div.col-lg-3.col-md-3.col-sm-3 > div:nth-child(2) > div.box-body > ul > li:nth-child(4) > span > a"));
        Assert.assertEquals("janesmith@email.com", emailSaved.getText());
        WebElement roleSaved = driver.findElement(By.className("text-muted"));
        Assert.assertEquals("Head of Household  ", roleSaved.getText());
        driver.close();
    }

    @Test
    public void checkEditHistory() {
        loginTest.login(driver);
        //Check edit history
        driver.get("http://digitalnizena.cz/church/PersonView.php?PersonID=129");

        WebElement history = driver.findElement(By.className("timeline-header"));
        Assert.assertEquals("Updated by Church Admin", history.getText());
        driver.close();
    }

    @Test
    public void personListings() {
        loginTest.login(driver);
        //Person Listings - check if person where added
        driver.get("http://digitalnizena.cz/church/v2/people");

        WebElement searchInput = driver.findElement(By.cssSelector("#members_filter input"));
        searchInput.sendKeys("Jane Smith");

        List<WebElement> elements = driver.findElements(By.cssSelector("table#members tr"));
        WebElement personTableRow = elements.get(7);
        Assert.assertEquals("Smith Jane janesmith@email.com Female Unassigned Head of Household", personTableRow.getText());
       // driver.close();
    }
}

