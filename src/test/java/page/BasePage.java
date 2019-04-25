package page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Parent class for every page object class.
 */
public abstract class BasePage {
    protected WebDriver driver;

    /**
     * Abstract method to check if page was loaded.
     * @return true/false if page loaded or not.
     */
    public abstract boolean isPageLoaded();

    public void waitUntilElementIsVisible(WebElement webElement) {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOf(webElement));
    }
}
