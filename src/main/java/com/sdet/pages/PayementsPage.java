package com.sdet.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class PayementsPage extends BasePage {
    
    @FindBy(css = "input[data-qa='name-on-card']")
    private WebElement nameOnCard;

    @FindBy(css = "input[data-qa='card-number']")
    private WebElement cardNumber;

    @FindBy(css = "input[data-qa='cvc']")
    private WebElement cvc;

    @FindBy(css = "input[data-qa='expiry-month']")
    private WebElement expiryMonth;

    @FindBy(css = "input[data-qa='expiry-year']")
    private WebElement expiryYear;

    @FindBy(css = "button[data-qa='pay-button']")
    private WebElement payButton;

    @FindBy(css = "h2[data-qa='order-placed']")
    private WebElement orderPlacedHeading;

    public void payWithTestCard(String name){
        type(nameOnCard, name);
        type(cardNumber, "4444333322221111");
        type(cvc, "123");
        type(expiryMonth, "08");
        type(expiryYear, "2030");
        click(payButton);
        waitForUrl("payment_done");
    }

    public boolean isOrderPlaced(){
        return isDisplayed(orderPlacedHeading);
    }

    public String getOrderPlacedHeading(){
        return getText(orderPlacedHeading);
    }

}
