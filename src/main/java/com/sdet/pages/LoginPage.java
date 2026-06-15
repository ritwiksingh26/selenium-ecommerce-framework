package com.sdet.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BasePage{

    @FindBy(css = "input[data-qa='login-email']")
    private WebElement loginEmailField;

    @FindBy(css = "input[data-qa='login-password']")
    private WebElement loginPasswordField;

    @FindBy(css = "button[data-qa='login-button']")
    private WebElement loginBtn;

    @FindBy(xpath = "//p[contains(.,'Your email or password is incorrect!')]")
    private WebElement invalidLoginError;

    @FindBy(css = "input[data-qa='signup-name']")
    private WebElement signupNameField;

    @FindBy(css = "input[data-qa='signup-email']")
    private WebElement signupEmailField;

    @FindBy(css = "button[data-qa='signup-button']")
    private WebElement signupBtn;

    @FindBy(xpath = "//p[contains(.,'Email Address already exist!')]")
    private WebElement duplicateEmailError;

    public void enterLoginEmail(String email){
        type(loginEmailField, email);
    }

    public void enterLoginPassword(String password){
        type(loginPasswordField, password);
    }

    public void clickLogin(){
        click(loginBtn);
    }

    public void login(String email, String password){
        enterLoginEmail(email);
        enterLoginPassword(password);
        clickLogin();
    }

    public boolean isInvalidLoginErrorDisplayed(){
        return isDisplayed(invalidLoginError);
    }

}
