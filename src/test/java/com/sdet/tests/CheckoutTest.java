package com.sdet.tests;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.sdet.api.clients.UserApiClient;
import com.sdet.api.models.TestUser;
import com.sdet.base.BaseTest;
import com.sdet.pages.CartPage;
import com.sdet.pages.CheckoutPage;
import com.sdet.pages.HomePage;
import com.sdet.pages.LoginPage;
import com.sdet.pages.PayementsPage;
import com.sdet.pages.ProductsPage;
import com.sdet.utils.TestGroups;


public class CheckoutTest extends BaseTest{
    
    private final UserApiClient userApi = new UserApiClient();
    private TestUser user;

    private HomePage homepage;
    private LoginPage loginPage;
    private ProductsPage productsPage;
    private CartPage cartPage;
    private CheckoutPage checkoutPage;
    private PayementsPage payementsPage;

    @BeforeMethod(alwaysRun = true)
    public void setUpPages(){
        homepage = new HomePage();
        loginPage = new LoginPage();
        productsPage = new ProductsPage();
        cartPage = new CartPage();
        checkoutPage = new CheckoutPage();
        payementsPage = new PayementsPage();
    }

    @AfterMethod(alwaysRun = true)
    public void deleteTestUser(){
        if (user != null) {
            userApi.deleteUser(user);
            user = null;
        }
    }

    // -helpers-
    private void loginAsNewUser(){
        user = TestUser.random();
        userApi.createUser(user);
        homepage.goToLoginPage();
        loginPage.login(user.email(), user.password());
        Assert.assertTrue(homepage.navBar.isUserLoggedIn(), "Test user should be logged in");
    }

    private void addProductAndGoToCheckout(){
        homepage.goToProductPage();
        productsPage.addToCart(0);
        productsPage.cartModal.viewCart();
        cartPage.proceedToCheckoutLoggedIn();
    }

    //TC-17
    @Test(groups = TestGroups.REGRESSION, 
        description = "TC-17: Guest is prompted to register or login at checkout")
    public void testGuestCheckoutRequiresLogin(){
        homepage.goToProductPage();
        productsPage.addToCart(0);
        productsPage.cartModal.viewCart();
        cartPage.proceedToCheckout();

        Assert.assertTrue(cartPage.isLoginPromptDisplayed(), "Guest should see the Register/Login prompt");
    }
    
    //TC-18
    @Test(groups = TestGroups.REGRESSION, 
        description = "TC-18: Registered user completed checkout")
    public void testRegisteredUserCheckout(){
        loginAsNewUser();
        addProductAndGoToCheckout();
        checkoutPage.addComment("Automated order");
        checkoutPage.placeOrder();
        payementsPage.payWithTestCard(user.fullName());

        Assert.assertTrue(payementsPage.isOrderPlaced(), "Order should be placed");
    }

    //TC-19
    @Test(groups = TestGroups.REGRESSION,
        description = "TC-19: Checkout shows the registered delivery and billing address")
    public void testCheckoutAddress(){
        loginAsNewUser();
        addProductAndGoToCheckout();

        String delivery = checkoutPage.getDeliveryAddress();
        String billing = checkoutPage.getBillingAddress();

        for(String expected : new String[]{user.fullName(), user.address(),
            user.city(), user.state(), user.zipcode(), user.country()}){
                Assert.assertTrue(delivery.contains(expected), "Delivery address missing: " + expected);
                Assert.assertTrue(billing.contains(expected), "Billing address missing: " + expected);
            }
    }

    //TC-20
    @Test(groups = {TestGroups.SANITY, TestGroups.REGRESSION}, 
        description = "TC-20: Order confirmation is displayed after payment")
    public void testOrderConfirmation(){
        loginAsNewUser();
        addProductAndGoToCheckout();
        checkoutPage.placeOrder();
        payementsPage.payWithTestCard(user.fullName());

        Assert.assertEquals(payementsPage.getOrderPlacedHeading().toUpperCase(), "ORDER PLACED!");
    }
    
}
