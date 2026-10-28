package com.sdet.pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class ContactUsPage extends BasePage {
    
    @FindBy(css = "input[data-qa='name']")
    private WebElement nameField;

    @FindBy(css = "input[data-qa='email']")
    private WebElement emailField;

    @FindBy(css = "input[data-qa='subject']")
    private WebElement subjectField;

    @FindBy(css = "textarea[data-qa='message']")
    private WebElement messageField;

    @FindBy(css = "input[name='upload_file']")
    private WebElement uploadInput;

    @FindBy(css = "input[data-qa='submit-button']")
    private WebElement submitButton;

    @FindBy(css = "div.status.alert-success")
    private WebElement successMessage;

    public void fillForm(String name, String email, String subject, String message) {
        type(nameField, name);
        type(emailField, email);
        type(subjectField, subject);
        type(messageField, message);
    }

    public void attachFile(String absolutePath) {
        uploadInput.sendKeys(absolutePath);   // file inputs take the path directly
    }

    public void submit() {
        click(submitButton);
    }

    // the site raises a JS confirm dialog on submit
    public void submitAndAcceptAlert() {
        click(submitButton);
        wait.until(ExpectedConditions.alertIsPresent()).accept();
    }

    public boolean isSuccessDisplayed() {
        return isDisplayed(successMessage);
    }

    public String getSuccessText() {
        return getText(successMessage);
    }

    // HTML5 validation: the browser's own message for an empty required field
    public String getEmailFieldValidationMessage() {
        return (String) ((JavascriptExecutor) driver)
            .executeScript("return arguments[0].validationMessage;", emailField);
    }
}

