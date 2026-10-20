package com.sdet.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CartPage extends BasePage {
    
    private static final By ROWS = By.cssSelector("#cart_info_table tbody tr");
    private static final By NAME = By.cssSelector(".cart_description h4 a");
    private static final By PRICE = By.cssSelector(".cart_price p");
    private static final By QUANTITY = By.cssSelector(".cart_quantity button");
    private static final By TOTAL_PRICE = By.cssSelector(".cart_total p");
    private static final By DELETE = By.cssSelector(".cart_delete a");

    @FindBy(id = "empty_cart")
    private WebElement emptyCartMessage;

    public int getItemCount(){
        return driver.findElements(ROWS).size();
    }

    public List<String> getProductNames(){
        return driver.findElements(NAME).stream().map(e -> e.getText().trim()).toList();
    }

    public int getPrice(int row){
        return number(ROWS, PRICE, row);
    }

    public int getQuantity(int row){
        return number(ROWS, QUANTITY, row);
    }

    public int getTotal(int row){
        return number(ROWS, TOTAL_PRICE, row);
    }

    public void removeItem(int row){
        WebElement delete = driver.findElements(DELETE).get(row);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", delete);
    }

    public boolean isCartEmpty(){
        return isDisplayed(emptyCartMessage);
    }

    private int number(By rows, By cell, int row){
        String text = driver.findElements(rows).get(row).findElement(cell).getText();
        return Integer.parseInt(text.replaceAll("[^0-9]", ""));
    }

}
