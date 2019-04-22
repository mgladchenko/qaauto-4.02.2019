package page;

import org.openqa.selenium.WebDriver;

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
}
