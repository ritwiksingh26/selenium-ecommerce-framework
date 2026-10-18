package com.sdet.tests;

import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.sdet.base.BaseTest;
import com.sdet.pages.HomePage;
import com.sdet.pages.ProductsPage;
import com.sdet.utils.JsonUtil;
import com.sdet.utils.TestGroups;

public class SearchTest extends BaseTest{

    private HomePage homePage;
    private ProductsPage productsPage;

    @BeforeMethod(alwaysRun = true) 
    public void setUpPages(){
        homePage = new HomePage();
        productsPage = new ProductsPage();
        homePage.goToProductPage();
    }

    @DataProvider(name = "searchData")
    public Object[][] searchData(){
        List<Map<String, String>> rows = JsonUtil.readJsonData("src/test/resources/testdata/products.json");
        Object[][] data = new Object[rows.size()][1];
        for (int i = 0; i < rows.size(); i++){
            data[i][0] = rows.get(i);
        }
        return data;
    }

    // TC-08
    @Test(dataProvider = "searchData", groups = TestGroups.REGRESSION, 
    description = "TC-07/08: Search valid and non-existing product")
    public void testSearch(Map<String, String> data){
        productsPage.searchFor(data.get("searchTerm"));

        Assert.assertEquals(productsPage.getHeadString().toUpperCase(), "SEARCHED PRODUCTS");

        if (data.get("expectedResult").equals("found")) {
            Assert.assertTrue(productsPage.getProductCount() > 0, data.get("testCaseId") + ": expected no results");
        } else {
            Assert.assertEquals(productsPage.getProductCount(), 0, data.get("testCaseId") + ": expected no results");
        }
    }

    // TC-09
    @Test(groups = TestGroups.REGRESSION,
    description = "TC-09: Search results match the search term")
    public void testSearchResultsAccuracy(){
        String term = "Blue Top";
        productsPage.searchFor(term);

        List<String> names = productsPage.getProductNames();
        Assert.assertFalse(names.isEmpty(), "Expected at least one result");
        names.forEach(name -> Assert.assertTrue(name.toLowerCase().contains(term.toLowerCase()),
        "Result '" + name + "' does not match '" + term + "'"));
    }
}
