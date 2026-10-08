package com.sdet.tests;

import com.sdet.base.BaseTest;
import com.sdet.components.NavBarComponent;
import com.sdet.pages.HomePage;
import com.sdet.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import io.qameta.allure.*;


@Epic("Authentication")
@Feature("Login")
public class LoginTest extends BaseTest {

    private HomePage homePage;
    private LoginPage loginPage;
    private NavBarComponent navBar;

    @BeforeMethod
    public void setUpPages(){
        homePage = new HomePage();
        loginPage = new LoginPage();
        navBar = new NavBarComponent();
    }

    //TC-04: Valid login
    @Story("Valid Login")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that a registered user can log in with valid credentials")
    @Test(description = "TC-04: Valid credentials should log user in")
    public void testValidLogin(){
        homePage.goToLoginPage();
        loginPage.login("hola@xyz.com", "Holaamigo");
        Assert.assertTrue(navBar.isUserLoggedIn(), "user should be logged in");
    }

    //TC-05: Invalid login
    @Story("Invalid Login")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify that invalid credentials show an error message")
    @Test(description = "TC-05: Invalid credentials should show error")
    public void testInvalidLogin(){
        homePage.goToLoginPage();
        loginPage.login("hola@123.com", "gibberish");
        Assert.assertTrue(loginPage.isInvalidLoginErrorDisplayed(), "error message should be visible");
    }

    //TC-06: Logout
    @Story("Logout")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify that a logged-in user can log out successfully")
    @Test(description = "TC-06: Logged-in user should be able to logout",
    dependsOnMethods = "testValidLogin")
    public void testLogout(){
        homePage.goToLoginPage();
        loginPage.login("hola@xyz.com", "Holaamigo");
        navBar.clickLogout();
        Assert.assertFalse(navBar.isUserLoggedIn(), "user should be logged out");
    }
}
