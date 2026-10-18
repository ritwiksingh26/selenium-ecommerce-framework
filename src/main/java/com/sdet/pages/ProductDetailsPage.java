package com.sdet.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ProductDetailsPage extends BasePage{
    
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
}
