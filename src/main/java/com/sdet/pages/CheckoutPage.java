package com.sdet.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CheckoutPage extends BasePage {
    
    @FindBy(id = "address_delivery")
    private WebElement deliveryAddress;

    @FindBy(id = "address_invoice")
    private WebElement billingAddress;

    @FindBy(css = "textarea[name='message']")
    private WebElement commentBox;

    @FindBy(css = "a[href='/payment']")
    private WebElement placeOrderBtn;

    public String getDeliveryAddress(){
        return getText(deliveryAddress);
    }

    public String getBillingAddress(){
        return getText(billingAddress);
    }

    public void addComment(String comment){
        type(commentBox, comment);
    }

    public void placeOrder(){
        click(placeOrderBtn);
        waitForUrl("payment");
    }
}
