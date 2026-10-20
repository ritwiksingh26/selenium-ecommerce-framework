package com.sdet.components;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.sdet.pages.BasePage;

public class CartModalComponent extends BasePage{
    
    @FindBy(css = "#cartModal .modal-content")
    private WebElement modal;

    @FindBy(css = "#cartModal button.close-modal")
    private WebElement continueShoppingBtn;

    @FindBy(css = "#cartModal a[href='/view_cart']")
    private WebElement viewCartLink;

    public boolean isDisplayed(){ 
        return isDisplayed(continueShoppingBtn);
    }

    public void continueShopping(){
        click(continueShoppingBtn);
        //wait until the modal is gone
        wait.until(d -> !modal.isDisplayed());
    }

    public void viewCart(){
        click(viewCartLink);
        waitForUrl("view_cart");
    }
    
}
