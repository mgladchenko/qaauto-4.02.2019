package test;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import page.HomePage;
import page.LoginPage;
import page.LoginSubmitPage;

public class LinkedinLoginTest extends BaseTest {

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
