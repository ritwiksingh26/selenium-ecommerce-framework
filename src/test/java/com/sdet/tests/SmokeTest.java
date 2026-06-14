package com.sdet.tests;

import com.sdet.base.BaseTest;
import com.sdet.base.DriverManager;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SmokeTest extends BaseTest {

    @Test
    public void verifyHomePageTitle(){
        String title = DriverManager.getDriver().getTitle();
        Assert.assertTrue(title.contains("Automation Exercise"), "Home page title mismatch. Got " + title);
    }
}
