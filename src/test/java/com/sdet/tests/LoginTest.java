package com.sdet.tests;

import com.sdet.base.BaseTest;
import com.sdet.components.NavBarComponent;
import com.sdet.pages.HomePage;
import com.sdet.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

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
    @Test(description = "TC-04: Valid credentials should log user in")
    public void testValidLogin(){
        homePage.goToLoginPage();
        loginPage.login("hola@xyz.com", "Holaamigo");
        Assert.assertTrue(navBar.isUserLoggedIn(), "user should be logged in");
    }

    //TC-05: Invalid login
    @Test(description = "TC-05: Invalid credentials should show error")
    public void testInvalidLogin(){
        homePage.goToLoginPage();
        loginPage.login("hola@123.com", "gibberish");
        Assert.assertTrue(loginPage.isInvalidLoginErrorDisplayed(), "error message should be visible");
    }

    //TC-06: Logout
    @Test(description = "TC-06: Logged-in user should be able to logout",
    dependsOnMethods = "testValidLogin")
    public void testLogout(){
        homePage.goToLoginPage();
        loginPage.login("hola@xyz.com", "Holaamigo");
        navBar.clickLogout();
        Assert.assertFalse(navBar.isUserLoggedIn(), "user should be logged out");
    }
}
