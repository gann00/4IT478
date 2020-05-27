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

import java.util.List;
import java.util.UUID;

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
        driver.close();
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
        String uuid = UUID.randomUUID().toString();
        eventTitle.sendKeys("VŠE " + uuid);

        WebElement eventDesc = driver.findElement(By.name("EventDesc"));
        eventDesc.sendKeys("Vysoká škola ekonomická");

        //Filling in Rich text editor
        // Option 1 Working
        WebElement iframe = driver.findElement(By.cssSelector("#cke_1_contents > iframe"));
        driver.switchTo().frame(iframe);
        WebElement inputRichText = driver.findElement(By.cssSelector("body"));
        inputRichText.click();
        inputRichText.sendKeys("Check rich text editor");
        driver.switchTo().parentFrame();

        // Option 2 Not working
        //((JavascriptExecutor)driver).executeScript("document.getElementsByName('EventText')[0].style.display='inline'");
        //driver.findElement(By.name("EventText")).sendKeys("Check rich text editor");


        // Save Changes
        WebElement saveChanges = driver.findElement(By.name("SaveChanges"));
        saveChanges.click();

        // Search VSE and Compare
        WebElement search = driver.findElement(By.cssSelector("#listEvents_filter > label > input[type=search]"));
        search.sendKeys("VŠE " + uuid);

        WebElement titleSaved = driver.findElement(By.cssSelector("#listEvents > tbody > tr > td:nth-child(2)"));
        Assert.assertEquals("VŠE " + uuid + " Vysoká škola ekonomická\nSermon Text", titleSaved.getText());

        // Edit Church Event
        WebElement editButton = driver.findElement(By.cssSelector("[data-original-title='Edit']"));
        editButton.click();


        // THEN
        // Check Filling in Rich text editor



    }

}
