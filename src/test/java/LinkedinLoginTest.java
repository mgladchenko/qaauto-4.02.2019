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
                //{ "linkedin.tst.yanina@gmail.com", "Test123!" },
                //{ "linkedin.TST.yanina@gmail.com", "Test123!" },
                { " linkedin.TST.yanina@gmail.com ", "Test123!" }
        };
    }

    @Test(dataProvider = "validDataProvider")
    public void successfulLoginTest(String userEmail, String userPassword) {
        Assert.assertTrue(loginPage.isPageLoaded(),
                "Login page was not loaded.");

        HomePage homePage = loginPage.login(userEmail, userPassword);

        Assert.assertTrue(homePage.isPageLoaded(),
                "Home page is not loaded.");
    }

    @DataProvider
    public Object[][] emptyValuesProvider() {
        return new Object[][]{
                { "", "" },
                { "linkedin.TST.yanina@gmail.com", "" },
                { "", "Test123!" }
        };
    }

    @Test(dataProvider = "emptyValuesProvider")
    public void negativeWithEmptyValuesTest(String userEmail, String userPassword) {
        Assert.assertTrue(loginPage.isPageLoaded(),
                "Login page was not loaded.");

        LoginPage newLoginPage = loginPage.login(userEmail, userPassword);

        Assert.assertTrue(newLoginPage.isPageLoaded(), "Login page is not loaded.");
    }

    @DataProvider
    public Object[][] invalidDataProvider() {
        return new Object[][]{
                { "linkedin.tst.yanina@gmail.com", "12345", "", "Hmm, that's not the right password. Please try again or request a new one."},
                { "linkedin.tst.yanina@@gmail.com", "Test123!", "Hmm, we don't recognize that email. Please try again.", ""},
        };
    }

    @Test(dataProvider = "invalidDataProvider")
    public void negativeNavigatesToLoginSubmitTest(String userEmail,
                                                   String userPassword,
                                                   String expectedEmailValidation,
                                                   String expectedPasswordValidation) {
        Assert.assertTrue(loginPage.isPageLoaded(),
                "Login page was not loaded.");

        LoginSubmitPage loginSubmitPage = loginPage.login(userEmail, userPassword);

        Assert.assertTrue(loginSubmitPage.isPageLoaded(), "LoginSubmit page is not loaded.");

        Assert.assertEquals(loginSubmitPage.getUserEmailValidationMessage(), expectedEmailValidation,
                "userEmail validation message is incorrect.");
        Assert.assertEquals(loginSubmitPage.getUserPasswordValidationMessage(), expectedPasswordValidation,
                "userPassword validation message is incorrect.");
    }
}
