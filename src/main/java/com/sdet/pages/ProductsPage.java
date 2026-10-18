package com.sdet.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ProductsPage extends BasePage{
    
    @FindBy(id="search_product")
    private WebElement searchInput;

    @FindBy(id="submit_search")
    private WebElement searchButton;

    @FindBy(css=".features_items .title.text-center")
    private WebElement sectionHeading;

    private static final By PRODUCT_NAMES = By.cssSelector(".features_items .productinfo p");
    private static final By VIEW_PRODUCT_LINKS = By.xpath("//a[contains(text(),'View Product')]");

    public void searchFor(String term){
        type(searchInput, term);
        click(searchButton);
    }

    public String getHeadString(){
        return getText(sectionHeading);
    }

    public List<String> getProductNames(){
        return driver.findElements(PRODUCT_NAMES).stream().map(e -> e.getText().trim()).toList();
    }

    public int getProductCount(){
        return driver.findElements(PRODUCT_NAMES).size();
    }

    // //a[contains(text(),'View Product')]

    public void openProductDetails(int index){
        click(driver.findElements(VIEW_PRODUCT_LINKS).get(index));
        waitForUrl("product_details");
    }


}
