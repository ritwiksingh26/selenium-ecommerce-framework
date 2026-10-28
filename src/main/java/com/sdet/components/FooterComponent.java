package com.sdet.components;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.sdet.pages.BasePage;

public class FooterComponent extends BasePage{
    
    @FindBy(id = "susbscribe_email")      // sic: the site's own spelling
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
}
