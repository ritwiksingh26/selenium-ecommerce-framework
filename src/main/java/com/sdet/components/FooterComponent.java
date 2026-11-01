package com.sdet.components;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.sdet.pages.BasePage;

public class FooterComponent extends BasePage{

    @FindBy(css = ".footer-widget h2")
    private WebElement subscriptionHeading;

    @FindBy(css = ".footer-bottom p")
    private WebElement copyrightText;
    
    @FindBy(id = "susbscribe_email")      
    private WebElement subscribeEmail;

    @FindBy(id = "subscribe")
    private WebElement subscribeButton;

    @FindBy(id = "success-subscribe")
    private WebElement successMessage;

    public void subscribe(String email) {
        type(subscribeEmail, email);
        click(subscribeButton);
    }

    public boolean isSubscriptionSuccessDisplayed() {
        return isDisplayed(successMessage);
    }

    public String getSuccessText() {
        return getText(successMessage);
    }

    public String getSubscriptionHeading() { 
        return getText(subscriptionHeading); 
    }

    public String getCopyrightText() { 
        return getText(copyrightText); 
    }
}
