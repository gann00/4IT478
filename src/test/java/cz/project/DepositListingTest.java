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
    public void given_userIsLoggedIn_when_userAddsNewDeposit_then_depositRecordIsShownInDepositTableGrid() {
        // GIVEN user is logged in
        loginTest.login(driver);

       // WHEN user adds deposit comment
       driver.get("http://digitalnizena.cz/church/FindDepositSlip.php");

       //Display 100 records

        WebElement recordsLenghtElement = driver.findElement(By.name("depositsTable_length"));
        WebDriverWait wait1 = new WebDriverWait(driver, 5);
        wait1.until(ExpectedConditions.visibilityOf(recordsLenghtElement));
        recordsLenghtElement.click();
        WebElement recordLenghtElements = driver.findElement(By.name("depositsTable_length"));
        Select recordsLenghts = new Select(recordLenghtElements);
        recordsLenghts.selectByValue("100");

        //WebElement recordsLenghtElement = driver.findElement(By.id("depositsTable_length"));
        //WebDriverWait wait1 = new WebDriverWait(driver, 2);
        //wait1.until(ExpectedConditions.visibilityOf(recordsLenghtElement));
        //Select recordsLenght = new Select(recordsLenghtElement);
        //recordsLenght.selectByValue("100");

       // WebElement recordsLenghtElement = driver.findElementByName("depositsTable_length");
       // Select recordsLenght = new Select(recordsLenghtElement);
       // recordsLenght.selectByValue("100");

        //Fill Deposit Comment
        WebElement depositCommentInput = driver.findElement(By.cssSelector("#depositComment"));
        String uuid = UUID.randomUUID().toString();
        String depositComment = "Unique-Deposit-" + uuid;
        depositCommentInput.sendKeys(depositComment);

        //Fill Deposite Date
        WebElement depositDateInput = driver.findElement(By.cssSelector("#depositDate"));
        depositDateInput.click();
        depositDateInput.clear();
        depositDateInput.sendKeys("2021-05-01");

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
        Assert.assertEquals("Unique-Deposit-", depositTableRow.getText());

       // List<WebElement> elements = driver.findElements(By.cssSelector("#depositsTable_wrapper #depositsTable tbody tr"));
       // WebElement depositTableRow = elements.get(0);
       // Assert.assertEquals("Unique-Deposit-", depositTableRow.getText());
        // driver.close();





    }
    //Added Deposit Detail
    //WebElement addedDepositDetail = driver.findElementByClassName("sorting_1");
    //addedDepositDetail.click();



    @Test
    //delete all rows in a grid
    public void deleteDeposits() throws InterruptedException {
        //given
        loginTest.login(driver);

        driver.get("http://digitalnizena.cz/church/FindDepositSlip.php");
        //than
        Thread.sleep(1000);

        List<WebElement> depositRows = driver.findElements(By.cssSelector("#depositsTable tbody tr"));

        for (WebElement row : depositRows) {
            row.click();
        }

        WebElement deleteButton = driver.findElement(By.cssSelector("#deleteSelectedRows"));
        deleteButton.click();

        WebElement confirmDeleteButton = driver.findElement(By.cssSelector(".modal-content > .modal-footer .btn-primary"));
        WebDriverWait wait = new WebDriverWait(driver, 1);
        wait.until(ExpectedConditions.visibilityOf(confirmDeleteButton));
        confirmDeleteButton.click();
        //than
        Assert.assertFalse(deleteButton.isEnabled());
    }
}

