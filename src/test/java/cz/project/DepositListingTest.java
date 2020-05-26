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
        shouldLoginUsingValidCredentials();




        // WHEN user adds deposit comment
        driver.get("http://digitalnizena.cz/church/FindDepositSlip.php");

        //100 zázmaov
       WebElement recordsLenghtElement = driver.findElement(By.name("depositsTable_length"));
       Select recordsLenght = new Select(recordsLenghtElement);
       recordsLenght.selectByValue("100");


        WebElement depositCommentInput = driver.findElement(By.cssSelector("#depositComment"));
        String uuid = UUID.randomUUID().toString();
        String depositComment = "deposit-AAA-" + uuid;
        depositCommentInput.sendKeys(depositComment);

        WebElement depositDateInput = driver.findElement(By.cssSelector("#depositDate"));
        depositDateInput.click();
        depositDateInput.clear();
        depositDateInput.sendKeys("2020-05-27");

        WebElement addDepositButton = driver.findElement(By.cssSelector("#addNewDeposit"));
        addDepositButton.click();

        // THEN newly added deposit should be shown in deposits table grid

        // option2 - use custom "expected condition" of WebDriver framework
        WebDriverWait wait = new WebDriverWait(driver, 2);     // timeout after 2 seconds
        wait.until((ExpectedCondition<Boolean>) webDriver -> {
                    // each time, we try to get the very first row from table grid and check, if contains the last record

                    List<WebElement> depositRows = driver.findElements(By.cssSelector("#depositsTable_wrapper #depositsTable tbody tr"));
                    WebElement firstRow = depositRows.get(0);
                    String innerHTML = firstRow.getAttribute("innerHTML");

                    if (innerHTML.contains(uuid)) {
                        assertTrue(innerHTML.contains("10-30-18"));    // beware, different date format in table grid vs. input field
                        assertTrue(innerHTML.contains(depositComment));
                        return true;     // expected condition is met
                    } else {
                        return false;    // selenium webdriver will continue polling the DOM each 500ms and check the expected condition by calling method apply(webDriver) again
                    }
                }
        );}

    public void deleteDeposits() throws InterruptedException {
        shouldLoginUsingValidCredentials();

        driver.get("http://digitalnizena.cz/church/FindDepositSlip.php");

        Thread.sleep(1000);

        List<WebElement> depositRows = driver.findElements(By.cssSelector("#depositsTable tbody tr"));

        for (WebElement row : depositRows) {
            row.click();
        }

        WebElement deleteButton = driver.findElement(By.cssSelector("#deleteSelectedRows"));
        deleteButton.click();

        //TODO compare this WebElement confirmDeleteButton = driver.findElement(By.cssSelector(".modal-dialog .btn-primary"));
        WebElement confirmDeleteButton = driver.findElement(By.cssSelector(".modal-content > .modal-footer .btn-primary"));
        WebDriverWait wait = new WebDriverWait(driver, 1);
        wait.until(ExpectedConditions.visibilityOf(confirmDeleteButton));
        confirmDeleteButton.click();

        // actually the application behaves incorrect => when delete all rows, Delete button should be disabled
        // we have our test correct, so it good that test fails!
        Assert.assertFalse(deleteButton.isEnabled());
    }

    private void shouldLoginUsingValidCredentials() {

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


}
