package com.sdet.tests;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.sdet.base.BaseTest;
import com.sdet.pages.CartPage;
import com.sdet.pages.HomePage;
import com.sdet.pages.ProductDetailsPage;
import com.sdet.pages.ProductsPage;
import com.sdet.utils.TestGroups;

public class CartTest extends BaseTest{
    
    private ProductsPage productsPage;
    private ProductDetailsPage detailsPage;
    private CartPage cartPage;

    @BeforeMethod(alwaysRun = true)
    public void setUpPages(){
        productsPage = new ProductsPage();
        detailsPage = new ProductDetailsPage();
        cartPage = new CartPage();
        new HomePage().goToProductPage();
    }

    //TC-12
    @Test(groups = {TestGroups.SMOKE, TestGroups.REGRESSION}, 
        description = "TC-12: Add single product to cart")
    public void testAddSingleProduct(){
        String name = productsPage.getProductNames().get(0);
        productsPage.addToCart(0);
        productsPage.cartMondal.viewCart();

        Assert.assertEquals(cartPage.getItemCount(), 1);
        Assert.assertEquals(cartPage.getProductNames().get(0), name);
    }

    //TC-13
    @Test(groups = {TestGroups.SMOKE, TestGroups.REGRESSION},
        description = "TC-13: Add multiple products to cart")
    public void testAddMultipleProducts(){
        List<String> names = productsPage.getProductNames();
        productsPage.addToCart(0);
        productsPage.cartMondal.continueShopping();
        productsPage.addToCart(4);
        productsPage.cartMondal.continueShopping();
        productsPage.addToCart(7);
        productsPage.cartMondal.viewCart();

        Assert.assertEquals(cartPage.getItemCount(), 3);
        Assert.assertTrue(cartPage.getProductNames().containsAll(List.of(names.get(0), names.get(4), names.get(7))));
    }

    //TC-14
    @Test(groups = {TestGroups.SMOKE, TestGroups.REGRESSION},
        description = "TC-14: Remove product from cart")
    public void testRemoveProduct(){
        productsPage.addToCart(0);
        productsPage.cartMondal.viewCart();
        cartPage.removeItem(0);

        Assert.assertTrue(cartPage.isCartEmpty(), "Cart should be empty after removal");
    }

    //TC-15
    @Test(groups = {TestGroups.SMOKE, TestGroups.REGRESSION},
        description = "TC-15: Verify cart totals")
    public void testCartTotal(){
        productsPage.addToCart(0);
        productsPage.cartMondal.continueShopping();
        productsPage.addToCart(3);
        productsPage.cartMondal.viewCart();

        for( int row = 0; row < cartPage.getItemCount(); row++){
            Assert.assertEquals(cartPage.getTotal(row), cartPage.getPrice(row) * cartPage.getQuantity(row), "Row " + row + ": total should be equal price x quantity"); 
        }
    }

    //TC-16
    @Test(groups = {TestGroups.SMOKE, TestGroups.REGRESSION},
        description = "TC-16: Update Quantity")
    public void testQuantityUpdate(){
        productsPage.openProductDetails(0);
        int unitPrice = detailsPage.getPriceValue();

        detailsPage.setQuantity(4);
        detailsPage.addToCart();
        detailsPage.cartModal.viewCart();

        Assert.assertEquals(cartPage.getQuantity(0), 4);
        Assert.assertEquals(cartPage.getTotal(0), unitPrice * 4);
    }
}
