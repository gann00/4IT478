package cz.project;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class EventTest {
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
//        driver.close();
    }

    @Test
    public void addingEventAndTextInRichTextEditor() {
        // GIVEN
        loginTest.login(driver);


        // WHEN
        driver.get("http://digitalnizena.cz/church/EventEditor.php");

        // Event type selection
        WebElement eventButton = driver.findElement(By.id("event_type_id"));
        eventButton.click();

        // Filling in details
        WebElement eventType = driver.findElement(By.cssSelector("#event_type_id > option:nth-child(5)"));
        eventType.click();

        WebElement eventTitle = driver.findElement(By.name("EventTitle"));
        eventTitle.clear();
        eventTitle.sendKeys("VŠE");

        WebElement eventDesc = driver.findElement(By.name("EventDesc"));
        eventDesc.sendKeys("Vysoká škola ekonomická");


        // Filling in Rich text editor
//        driver.findElement(By.xpath("/html/body")).sendKeys("Check");


        // Save Changes
        WebElement saveChanges = driver.findElement(By.name("SaveChanges"));
        saveChanges.click();

        // Search vše
        WebElement search = driver.findElement(By.cssSelector("#listEvents_filter > label > input[type=search]"));
        search.sendKeys("vše");

        // Edit Church Event
        WebElement edit = driver.findElement(By.name("Action"));
        edit.click();


        // THEN
        // Check Filling in Rich text editor



    }

}
