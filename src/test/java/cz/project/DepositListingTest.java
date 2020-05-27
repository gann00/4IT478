package cz.project;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class DepositListingTest {
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
    public void given_userIsLoggedIn_when_userAddsNewDeposit_then_depositRecordIsShownInDepositTableGrid() throws InterruptedException {
        // GIVEN user is logged in
        loginTest.login(driver);

       // WHEN user adds deposit comment
       driver.get("http://digitalnizena.cz/church/FindDepositSlip.php");

       //Display 100 records
        Thread.sleep(2000);

       WebElement recordsLenghtElement = driver.findElementByName("depositsTable_length");
       Select recordsLenght = new Select(recordsLenghtElement);
       recordsLenght.selectByValue("100");

        //Fill Deposit Comment
        WebElement depositCommentInput = driver.findElement(By.cssSelector("#depositComment"));
        String uuid = UUID.randomUUID().toString();
        String depositComment = "Unique-Deposit-" + uuid;
        depositCommentInput.sendKeys(depositComment);

        //Fill Deposite Date
        WebElement depositDateInput = driver.findElement(By.cssSelector("#depositDate"));
        depositDateInput.click();
        depositDateInput.clear();
        depositDateInput.sendKeys("2020-05-27");

        //Button click Add New Deposit
        WebElement addDepositButton = driver.findElement(By.cssSelector("#addNewDeposit"));
        addDepositButton.click();

        // THEN newly added deposit should be shown in deposits table grid

        WebDriverWait wait = new WebDriverWait(driver, 2);
        WebElement searchDepositComment = driver.findElementByCssSelector("#depositsTable_wrapper #depositsTable tbody tr");

        WebElement searchInput = driver.findElement(By.cssSelector("#depositsTable_filter input"));
        searchInput.sendKeys("Unique-Deposit-");

        List<WebElement> elements = driver.findElements(By.cssSelector("#depositsTable_wrapper #depositsTable tbody tr td:nth-child(4)"));
        WebElement depositTableRow = elements.get(0);
        Assert.assertEquals("Unique-Deposit-" + uuid, depositTableRow.getText());
        driver.close();
    }

}

