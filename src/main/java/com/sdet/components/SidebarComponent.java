package com.sdet.components;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.sdet.pages.BasePage;

public class SidebarComponent extends BasePage {
    
    @FindBy(css = "a[href='#Women']")
    private WebElement womenCategory;

    @FindBy(css = "#Women a[href='/category_products/1']")
    private WebElement womenDressSubCategory;

    @FindBy(css = ".brands-name a[href='/brand_products/Polo']")
    private WebElement poloBrand;

    @FindBy(css = ".features_items h2.title")
    private WebElement pageHeading;

    public void openWomenDress() {
        click(womenCategory);                 // expands the accordion
        click(womenDressSubCategory);
        waitForUrl("category_products");
    }

    public void openPoloBrand() {
        click(poloBrand);
        waitForUrl("brand_products");
    }

    public String getHeading() {
        return getText(pageHeading);
    }
}
