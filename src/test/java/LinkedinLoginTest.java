import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class LinkedinLoginTest {

    WebDriver driver;
    LoginPage loginPage;

    @BeforeMethod
    public void beforeMethod() {
        driver = new ChromeDriver();
        driver.get("https://www.linkedin.com/");
        loginPage = new LoginPage(driver);
    }

    @AfterMethod
    public void afterMethod() {
        driver.quit();
    }

    @DataProvider
    public Object[][] validDataProvider() {
        return new Object[][]{
                { "linkedin.tst.yanina@gmail.com", "Test123!" },
                { "linkedin.TST.yanina@gmail.com", "Test123!" }
        };
    }

    @Test(dataProvider = "validDataProvider")
    public void successfulLoginTest(String userEmail, String userPassword) {
        Assert.assertTrue(loginPage.isPageLoaded(),
                "Login page was not loaded.");

        loginPage.login(userEmail, userPassword);

        HomePage homePage = new HomePage(driver);
        Assert.assertTrue(homePage.isPageLoaded(),
                "Home page is not loaded.");
    }

    @Test
    public void negativeWithEmptyValuesTest() {
        Assert.assertTrue(loginPage.isPageLoaded(),
                "Login page was not loaded.");

        loginPage.login("", "");

        Assert.assertTrue(loginPage.isPageLoaded(), "Login page is not loaded.");
    }

    @Test
    public void negativeNavigatesToLoginSubmitTest() {
        Assert.assertTrue(loginPage.isPageLoaded(),
                "Login page was not loaded.");

        loginPage.login("linkedin.tst.yanina@gmail.com", "12345");

        LoginSubmitPage loginSubmitPage = new LoginSubmitPage(driver);
        Assert.assertTrue(loginSubmitPage.isPageLoaded(), "LoginSubmit page is not loaded.");

        Assert.assertEquals(loginSubmitPage.getUserEmailValidationMessage(), "",
                "userEmail validation message is incorrect.");
        Assert.assertEquals(loginSubmitPage.getUserPasswordValidationMessage(),
                "Hmm, that's not the right password. Please try again or request a new one.",
                "userPassword validation message is incorrect.");
    }
}
