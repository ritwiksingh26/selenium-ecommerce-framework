package com.sdet.pages;

import com.sdet.components.NavBarComponent;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage extends BasePage{

    NavBarComponent navBar = new NavBarComponent();

    @FindBy(css = "img[src='/static/images/home/logo.png']")
    private WebElement logo;

    @FindBy(css = "div#slider-carousel")
    private WebElement heroCarousel;

    public boolean isLogoDisplayed(){
        return isDisplayed(logo);
    }

    public boolean isCarouselDisplayed(){
        return isDisplayed(heroCarousel);
    }

    public void goToLoginPage(){
        navBar.clickSignupLogin();
        waitForUrl("login");
    }
}
