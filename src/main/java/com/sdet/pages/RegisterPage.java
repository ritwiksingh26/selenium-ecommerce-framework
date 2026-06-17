package com.sdet.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

public class RegisterPage extends BasePage{

    @FindBy(css = "input#id_gender1")
    private WebElement mrRadioBtn;

    @FindBy(css = "input#id_gender2")
    private WebElement mrsRadioBtn;

    @FindBy(css = "input[data-qa='password']")
    private WebElement passwordField;

    @FindBy(css = "select#days")
    private WebElement dobDaysDropdown;

    @FindBy(css = "select#months")
    private WebElement dobMonthsDropdown;

    @FindBy(css = "select#years")
    private WebElement dobYearsDropdown;

    @FindBy(css = "input#first_name")
    private WebElement firstNameField;

    @FindBy(css = "input#last_name")
    private WebElement lastNameField;

    @FindBy(css = "input#company")
    private WebElement companyField;

    @FindBy(css = "input#address1")
    private WebElement addressField;

    @FindBy(css = "select#country")
    private WebElement countryField;

    @FindBy(css = "input#state")
    private WebElement stateField;

    @FindBy(css = "input#city")
    private WebElement cityField;

    @FindBy(css = "input#zipcode")
    private WebElement zipcodeField;

    @FindBy(css = "input#mobile_number")
    private WebElement mobileField;

    @FindBy(css = "button[data-qa='create-account']")
    private WebElement createAccountBtn;

    @FindBy(css = "h2[data-qa='account-created']")
    private WebElement accountCreatedHeader;

    public void selectGenderMr(){
        click(mrRadioBtn);
    }

    public void selectGenderMrs(){
        click(mrsRadioBtn);
    }

    public void enterPassword(String password){
        type(passwordField, password);
    }

    public void enterDOB(String day, String month, String year){
        new Select(dobDaysDropdown).selectByValue(day);
        new Select(dobMonthsDropdown).selectByValue(month);
        new Select(dobYearsDropdown).selectByValue(year);
    }

    public void enterAddress(String firstName, String lastName, String company,
                             String address, String state,
                             String city, String zipcode, String mobile){
        type(firstNameField, firstName);
        type(lastNameField, lastName);
        type(companyField, company);
        type(addressField, address);
        type(stateField, state);
        type(cityField,city);
        type(zipcodeField, zipcode);
        type(mobileField, mobile);
    }

    public void selectCountry(String country){
        new Select(countryField).selectByValue(country);
    }

    public void clickCreateAccount(){
        click(createAccountBtn);
    }

    public boolean isAccountCreateDisplayed(){
        return isDisplayed(accountCreatedHeader);
    }

    public void completeRegistration(String password, String day, String month, String year,
                                     String firstName, String lastName, String company, String address,
                                     String state, String city, String zipcode, String mobile, String country){
        selectGenderMr();
        enterPassword(password);
        enterDOB(day, month, year);
        enterAddress(firstName, lastName, company, address, state, city, zipcode, mobile);
        selectCountry(country);
        clickCreateAccount();
    }

}
