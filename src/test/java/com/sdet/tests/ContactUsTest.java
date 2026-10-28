package com.sdet.tests;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.sdet.base.BaseTest;
import com.sdet.pages.ContactUsPage;
import com.sdet.pages.HomePage;
import com.sdet.utils.TestGroups;

public class ContactUsTest extends BaseTest {
    
    private ContactUsPage contactPage;

    @BeforeMethod(alwaysRun = true)
    public void setUpPages() {
        contactPage = new ContactUsPage();
        new HomePage().navBar.goToContactUs();
    }

    // TC-24
    @Test(groups = TestGroups.REGRESSION, description = "TC-24: Submit valid contact query")
    public void testSubmitValidQuery() throws IOException {
        Path upload = Files.createTempFile("upload-", ".txt");
        Files.writeString(upload, "Sample attachment for automated test");

        contactPage.fillForm("QA Tester", "qa_tester@test.com",
            "Automation test", "This message was sent by an automated test.");
        contactPage.attachFile(upload.toAbsolutePath().toString());
        contactPage.submitAndAcceptAlert();

        Assert.assertTrue(contactPage.isSuccessDisplayed(), "Success message should appear");
        Assert.assertTrue(contactPage.getSuccessText().toLowerCase().contains("success"));
    }

    // TC-25
    @Test(groups = TestGroups.REGRESSION, description = "TC-25: Mandatory field validation")
    public void testMandatoryFieldValidation() {
        contactPage.fillForm("QA Tester", "", "Automation test", "Message without an email");
        contactPage.submit();

        Assert.assertFalse(contactPage.getEmailFieldValidationMessage().isEmpty(),
            "Browser should flag the empty required Email field");
        Assert.assertFalse(contactPage.isSuccessDisplayed(),
            "Form must not be submitted without an email");
    }
}

