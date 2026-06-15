package com.sdet.components;

import com.sdet.pages.BasePage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class NavBarComponent extends BasePage {

    @FindBy(css = "a[href='/login']")
    private WebElement signupLoginLink;

    @FindBy(css = "a[href='/logout']")
    private WebElement logoutLink;

    @FindBy(css = "a[href='/']")
    private WebElement homeLink;

    @FindBy(xpath = "//a[contains(.,'Logged in as')]")
    private WebElement loggedInAsLabel;

    public void clickSignupLogin(){
        click(signupLoginLink);
    }

    public void clickLogout(){
        click(logoutLink);
    }

    public void clickHome(){
        click(homeLink);
    }

    public boolean isUserLoggedIn(){
        return isDisplayed(loggedInAsLabel);
    }

    public String getLoggedInUsername(){
        return getText(loggedInAsLabel).replace("Logged in as ","").trim();
    }
}
