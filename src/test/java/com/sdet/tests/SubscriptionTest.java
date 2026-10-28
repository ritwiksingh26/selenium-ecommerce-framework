package com.sdet.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.sdet.base.BaseTest;
import com.sdet.components.FooterComponent;
import com.sdet.utils.TestGroups;

public class SubscriptionTest extends BaseTest {
    
     // TC-26
    @Test(groups = TestGroups.REGRESSION, description = "TC-26: Newsletter subscription")
    public void testNewsletterSubscription() {
        FooterComponent footer = new FooterComponent();
        footer.subscribe("subscriber_" + System.currentTimeMillis() + "@test.com");

        Assert.assertTrue(footer.isSubscriptionSuccessDisplayed(),
            "Subscription success message should appear");
    }
}
