package com.sdet.pages;

import com.sdet.components.NavBarComponent;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class HomePage extends BasePage{

    public NavBarComponent navBar = new NavBarComponent();

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
        navBar.goToLogin();
    }

    public void goToProductPage(){
        navBar.goToProducts();
    }

    public void waitUntilOnHome(){
        wait.until(ExpectedConditions.not(ExpectedConditions.urlContains("products")));
    }

    public void setViewport(int width, int height) {
        driver.manage().window().setSize(new Dimension(width, height));
    }

    public boolean hasHorizontalScroll() {
    Boolean overflow = (Boolean) ((JavascriptExecutor) driver).executeScript(
        "return document.documentElement.scrollWidth > document.documentElement.clientWidth;");
        return Boolean.TRUE.equals(overflow);
    }
}
