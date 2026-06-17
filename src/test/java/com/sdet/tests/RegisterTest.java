package com.sdet.tests;

import com.sdet.base.BaseTest;
import com.sdet.components.NavBarComponent;
import com.sdet.pages.HomePage;
import com.sdet.pages.LoginPage;
import com.sdet.pages.RegisterPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class RegisterTest extends BaseTest {

    private HomePage homePage;
    private LoginPage loginPage;
    private RegisterPage registerPage;

    @BeforeMethod
    public void setUpPages(){
        homePage = new HomePage();
        loginPage = new LoginPage();
        registerPage = new RegisterPage();
    }

    //TC-01: Valid Registration
    @Test(description = "TC-01: New user should be able to register successfully")
    public void validRegistration(){
        homePage.goToLoginPage();
        String email = "testUser_"+ System.currentTimeMillis() + "@test.com";

        //hard coding the values
        loginPage.fillSignupForm("Test User", email);

        registerPage.completeRegistration("pass@123", "10", "6",
                "2000", "Test", "User", "XYZ", "1st Street",
                "Kerala", "Kochi", "123321", "11223344", "India");
        Assert.assertTrue(registerPage.isAccountCreateDisplayed(), "Account created info should be visible");
    }

    //TC-02: Duplicate Email Registration
    @Test(description = "TC-02: Existing email should show duplicate error")
    public void testDuplicateEmailRegistration(){
        homePage.goToLoginPage();
        String email = "hola@xyz.com";
        loginPage.fillSignupForm("Hola", email);
        Assert.assertTrue(loginPage.isDuplicateEmailErrorDisplayed(), "Duplicate error should be displayed");
    }
}
