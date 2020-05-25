package cz.churchcrm.testframework;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;

public class Grid {
    private ChromeDriver driver;
    private String cssSelector;

    public Grid(ChromeDriver driver, String cssSelector) {
        this.driver = driver;
        this.cssSelector = cssSelector;        
    }

    public List<GridRow> search(String query) {
        WebElement gridWrapper = driver.findElement(By.cssSelector(cssSelector));
        WebElement searchInput = gridWrapper.findElement(By.cssSelector(".dataTables_filter input"));
        searchInput.sendKeys(query);



        return null;
    }
}
