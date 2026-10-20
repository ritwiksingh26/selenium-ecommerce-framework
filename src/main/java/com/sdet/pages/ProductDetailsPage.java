package com.sdet.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.sdet.components.CartModalComponent;

public class ProductDetailsPage extends BasePage{

    public CartModalComponent cartModal = new CartModalComponent();

    @FindBy(id = "quantity")
    private WebElement quantityInput;

    @FindBy(css = ".cart")
    private WebElement addToCartButton;
    
    @FindBy(css = ".product-information h2")
    private WebElement productName;

    @FindBy(css = ".product-information span span")
    private WebElement productPrice;

    @FindBy(css = ".product-information")
    private WebElement informationBlock;

    public String getProductName(){
        return getText(productName);
    }

    public String getProductPrice(){
        return getText(productPrice);
    }

    public String getInformationText(){
        return getText(informationBlock);
    }

    public void setQuantity(int quantity){
        type(quantityInput, String.valueOf(quantity));
    }

    public void addToCart(){
        click(addToCartButton);
    }

    public int getPriceValue(){
        return Integer.parseInt(getProductPrice().replaceAll("[^0-9]", ""));
    }
}
