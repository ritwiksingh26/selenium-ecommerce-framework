package com.sdet.tests;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.sdet.base.BaseTest;
import com.sdet.components.FooterComponent;
import com.sdet.pages.HomePage;
import com.sdet.utils.TestGroups;

public class UiValidationTest extends BaseTest{
    
    private HomePage homePage;

    @BeforeMethod(alwaysRun = true)
    public void setUpPages() {
        homePage = new HomePage();
    }

    // TC-27
    @Test(groups = {TestGroups.SMOKE, TestGroups.REGRESSION},
          description = "TC-27: Header shows logo and all navigation links")
    public void testHeaderElements() {
        Assert.assertTrue(homePage.isLogoDisplayed(), "Logo should be visible");

        List<String> links = homePage.navBar.getHeaderLinkTexts();
        for (String expected : new String[]{"home", "products", "cart", "signup / login",
                "test cases", "api testing", "video tutorials", "contact us"}) {
            Assert.assertTrue(links.stream().anyMatch(t -> t.contains(expected)),
                "Header link missing: " + expected + " (found: " + links + ")");
        }
    }

    // TC-28
    @Test(groups = TestGroups.REGRESSION,
          description = "TC-28: Footer shows subscription block and copyright")
    public void testFooterElements() {
        FooterComponent footer = new FooterComponent();

        Assert.assertEquals(footer.getSubscriptionHeading().toUpperCase(), "SUBSCRIPTION");
        Assert.assertTrue(footer.getCopyrightText().toLowerCase().contains("copyright"),
            "Footer should show a copyright notice");
    }

    @DataProvider(name = "viewports")
    public Object[][] viewports() {
        return new Object[][]{
            {"mobile", 375, 812},
            {"tablet", 768, 1024},
            {"desktop", 1920, 1080}
        };
    }

    // TC-29
    @Test(dataProvider = "viewports", groups = TestGroups.REGRESSION,
          description = "TC-29: Home page renders without horizontal scroll at common viewports")
    public void testResponsiveLayout(String device, int width, int height) {
        homePage.setViewport(width, height);

        Assert.assertTrue(homePage.isLogoDisplayed(), device + ": logo should be visible");
        Assert.assertFalse(homePage.hasHorizontalScroll(),
            device + " (" + width + "px): page should not scroll horizontally");
    }
}

