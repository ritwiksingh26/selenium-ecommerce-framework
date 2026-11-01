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
import com.sdet.pages.RegisterPage;
import com.sdet.utils.TestGroups;

public class EndToEndTest extends BaseTest {
    
    private final UserApiClient userApi = new UserApiClient();
    private TestUser user;

    private HomePage homePage;
    private LoginPage loginPage;
    private RegisterPage registerPage;
    private ProductsPage productsPage;
    private CartPage cartPage;
    private CheckoutPage checkoutPage;
    private PayementsPage paymentPage;

    @BeforeMethod(alwaysRun = true)
    public void setUpPages() {
        homePage = new HomePage();
        loginPage = new LoginPage();
        registerPage = new RegisterPage();
        productsPage = new ProductsPage();
        cartPage = new CartPage();
        checkoutPage = new CheckoutPage();
        paymentPage = new PayementsPage();
    }

    @AfterMethod(alwaysRun = true)
    public void deleteTestUser() {
        if (user != null) {
            userApi.deleteUser(user);
            user = null;
        }
    }

    // TC-30
    @Test(groups = TestGroups.REGRESSION,
          description = "TC-30: Register > Login > Search > Add to Cart > Checkout")
    public void testRegisterLoginSearchCartCheckout() {
        user = TestUser.random();

        // 1. Register through the UI
        homePage.goToLoginPage();
        loginPage.fillSignupForm(user.fullName(), user.email());
        registerPage.completeRegistration(
            user.password(), "10", "10", "1995",
            user.firstName(), user.lastName(), "Google", user.address(),
            user.state(), user.city(), user.zipcode(), user.mobile(), user.country());
        Assert.assertTrue(registerPage.isAccountCreateDisplayed(), "Account should be created");
        registerPage.clickContinue();

        // 2. Log out, then log in with the new account
        homePage.navBar.clickLogout();
        loginPage.login(user.email(), user.password());
        Assert.assertTrue(homePage.navBar.isUserLoggedIn(), "User should be logged in");

        // 3. Search and add to cart
        homePage.goToProductPage();
        productsPage.searchFor("Top");
        Assert.assertTrue(productsPage.getProductCount() > 0, "Search should return results");
        productsPage.addToCart(0);
        productsPage.cartModal.viewCart();
        Assert.assertEquals(cartPage.getItemCount(), 1, "Cart should contain one item");

        // 4. Checkout and pay
        cartPage.proceedToCheckoutLoggedIn();
        checkoutPage.placeOrder();
        paymentPage.payWithTestCard(user.fullName());

        Assert.assertTrue(paymentPage.isOrderPlaced(), "Order should be placed");
    }
    
}
