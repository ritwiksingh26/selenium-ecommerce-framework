package com.sdet.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.sdet.base.BaseTest;
import com.sdet.components.SidebarComponent;
import com.sdet.pages.HomePage;
import com.sdet.utils.TestGroups;

public class NavigationTest extends BaseTest {
    private HomePage homePage;
    private SidebarComponent sidebar;

    @BeforeMethod(alwaysRun = true)
    public void setUpPages() {
        homePage = new HomePage();
        sidebar = new SidebarComponent();
    }

    // TC-21
    @Test(groups = {TestGroups.SMOKE, TestGroups.REGRESSION},
          description = "TC-21: Navigate to Products and back to Home")
    public void testHomePageNavigation() {
        homePage.goToProductPage();                       // your own method name if it differs
        homePage.navBar.clickHome();

        Assert.assertTrue(homePage.isCarouselDisplayed(),
            "Home page carousel should be visible after navigating back");
    }

    // TC-22
    @Test(groups = TestGroups.REGRESSION, description = "TC-22: Category navigation")
    public void testCategoryNavigation() {
        sidebar.openWomenDress();

        Assert.assertEquals(sidebar.getHeading().toUpperCase(), "WOMEN - DRESS PRODUCTS");
    }

    // TC-23
    @Test(groups = TestGroups.REGRESSION, description = "TC-23: Brand navigation")
    public void testBrandNavigation() {
        sidebar.openPoloBrand();

        Assert.assertEquals(sidebar.getHeading().toUpperCase(), "BRAND - POLO PRODUCTS");
    }
}

