package com.sdet.tests;

import com.sdet.base.BaseTest;
import com.sdet.components.NavBarComponent;
import com.sdet.pages.HomePage;
import com.sdet.pages.LoginPage;
import com.sdet.pages.RegisterPage;
import com.sdet.utils.ExcelUtil;
import com.sdet.utils.LogUtil;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

import static org.openqa.selenium.By.cssSelector;

public class RegisterTest extends BaseTest {

    private static final Logger log = LogUtil.getLogger(RegisterTest.class);
    private HomePage homePage;
    private LoginPage loginPage;
    private RegisterPage registerPage;

    private static final String EXCEL_PATH = "src/test/resources/testdata/users.xlsx";

    @BeforeMethod
    public void setUpPages(){
        homePage = new HomePage();
        loginPage = new LoginPage();
        registerPage = new RegisterPage();
    }

    @DataProvider(name = "registrationData")
    public Object[][] getRegistrationData() {
        List<Map<String, String>> rows = ExcelUtil.readExcelData(EXCEL_PATH, "Sheet1");
        Object[][] data = new Object[rows.size()][1];
        for (int i = 0; i < rows.size(); i++){
            data[i][0] = rows.get(i);
        }
        return data;
    }

    @Test(dataProvider = "registrationData",
    description = "Data-driven registration scenarios from Excel")
    public void registrationScenarios(Map<String, String> data){
        String testCaseId = data.get("testCaseId");
        String name = data.get("name");
        String email = data.get("email").replace("<RUNTIME>", String.valueOf(System.currentTimeMillis()));
        String expectedResult = data.get("expectedResult");

        log.info("Running {} with email: {}", testCaseId, email);

        homePage.goToLoginPage();
        loginPage.fillSignupForm(name, email);

        switch (expectedResult) {
            case "success" -> {
                registerPage.completeRegistration(
                        "Test@1234","10", "10", "1995",
                        "Test", "User", "123 Main Street", "India",
                        "Karnataka", "Bangalore", "560001", "1234567890", "India"
                );
                Assert.assertTrue(registerPage.isAccountCreateDisplayed(),
                        testCaseId + ": Account should be created");
            }

            case "name_required", "email_required" -> {
                Assert.assertTrue(loginPage.isMandatoryFieldValidationTriggered(cssSelector("input:invalid")),
                        testCaseId + ": Mandatory field validation should trigger");
            }
        }
    }

    //TC-01: Valid Registration
    @Test(description = "TC-01: New user should be able to register successfully")
    public void validRegistration(){
        homePage.goToLoginPage();
        String email = "testUser_"+ System.currentTimeMillis() + "@test.com";

        //hard coding the values
        loginPage.fillSignupForm("Test User", email);

        registerPage.completeRegistration("pass@123", "10", "6",
                "2000", "Test", "User", "XYZ", "1st Street",
                "Kerala", "Kochi", "123321", "11223344", "India");
        Assert.assertTrue(registerPage.isAccountCreateDisplayed(), "Account created info should be visible");
    }

    //TC-02: Duplicate Email Registration
    @Test(description = "TC-02: Existing email should show duplicate error")
    public void testDuplicateEmailRegistration(){
        homePage.goToLoginPage();
        String email = "hola@xyz.com";
        loginPage.fillSignupForm("Hola", email);
        Assert.assertTrue(loginPage.isDuplicateEmailErrorDisplayed(), "Duplicate error should be displayed");
    }
}
