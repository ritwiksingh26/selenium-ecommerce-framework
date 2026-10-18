package com.sdet.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.sdet.base.BaseTest;
import com.sdet.pages.HomePage;
import com.sdet.pages.ProductDetailsPage;
import com.sdet.pages.ProductsPage;
import com.sdet.utils.TestGroups;

public class ProductTest extends BaseTest{
    
    private ProductsPage productsPage;
    private ProductDetailsPage detailsPage;

    @BeforeMethod(alwaysRun = true)
    public void setUpPages(){
        HomePage homePage = new HomePage();
        productsPage = new ProductsPage();
        detailsPage = new ProductDetailsPage();
        homePage.goToProductPage();
    }

    // TC - 10
    @Test(groups = {TestGroups.SMOKE, TestGroups.REGRESSION},
          description = "TC-10: Open product details page")
    public void testOpenProductDetails() {
        String listName = productsPage.getProductNames().get(0);
        productsPage.openProductDetails(0);

        Assert.assertEquals(detailsPage.getProductName(), listName,
            "Details page should show the product that was clicked");
    }

    // TC-11
    @Test(groups = TestGroups.REGRESSION,
          description = "TC-11: Verify product information fields")
    public void testProductInformation() {
        productsPage.openProductDetails(0);
        String info = detailsPage.getInformationText();

        Assert.assertFalse(detailsPage.getProductPrice().isEmpty(), "Price missing");
        for (String field : new String[]{"Category", "Availability", "Condition", "Brand"}) {
            Assert.assertTrue(info.contains(field), "Missing field: " + field);
        }
    }
}
