package com.sdet.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.sdet.components.CartModalComponent;

public class ProductsPage extends BasePage{

    public CartModalComponent cartModal = new CartModalComponent();
    
    @FindBy(id="search_product")
    private WebElement searchInput;

    @FindBy(id="submit_search")
    private WebElement searchButton;

    @FindBy(css=".features_items .title.text-center")
    private WebElement sectionHeading;

    private static final By PRODUCT_NAMES = By.cssSelector(".features_items .productinfo p");
    private static final By VIEW_PRODUCT_LINKS = By.xpath("//a[contains(text(),'View Product')]");

    private static final By ADD_TO_CART_BUTTONS = By.cssSelector(".features_items .productinfo a.add-to-cart");
    private static final By PRODUCT_PRICES = By.cssSelector(".features_items .productinfo h2");

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

    public void openProductDetails(int index){
        WebElement link = driver.findElements(VIEW_PRODUCT_LINKS).get(index);
        String href = link.getAttribute("href");

        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", link);

        try {
            waitForUrl("product_details");
        } catch (org.openqa.selenium.TimeoutException e) {
            driver.get(href);
            waitForUrl("product_details");
        }
    }

    public void addToCart(int index){
        WebElement button = driver.findElements(ADD_TO_CART_BUTTONS).get(index);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", button);
    }

    public int getProductPrice(int index){
        String text = driver.findElements(PRODUCT_PRICES).get(index).getText();
        return Integer.parseInt(text.replaceAll("[^0-9]", ""));
    }

}
