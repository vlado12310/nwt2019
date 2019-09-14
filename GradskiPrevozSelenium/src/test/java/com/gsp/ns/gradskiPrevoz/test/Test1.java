package com.gsp.ns.gradskiPrevoz.test;

import static org.testng.Assert.assertEquals;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pages.HomePage;

public class Test1 {
	private WebDriver browser;
	
	HomePage homePage;

	private final String SORT_URL = "http://localhost:4200/";
	@BeforeMethod
	public void setupSelenium() {
		//instantiate browser
		System.setProperty("webdriver.chrome.driver", "D:/4.godina/kst/chromedriver.exe");
		browser = new ChromeDriver();
		//maximize window
		browser.manage().window().maximize();
		//navigate
		browser.navigate().to("http://localhost:4200/");
		
		homePage = new HomePage(browser);

	}

		@Test
		public void test() {
			homePage.ensureUsernameFieldIsVisible();
			homePage.ensurePasswordFieldIsVisible();
			homePage.ensureLoginBtnIsClickable();
			homePage.getUsernameField().sendKeys("student");
			homePage.getPasswordField().sendKeys("admin");

			homePage.getLoginBtn().click();
			homePage.ensureMenuBtnIsClickable();
			homePage.getMenuBtn().click();
			
			homePage.ensurePricelistBtnIsClickable();
			homePage.getPricelistBtn().click();
			
			homePage.ensureBuyBtnIsClickable();
			homePage.getBuyBtn().click();
			
			homePage.ensureConfirmBtnIsClickable();
			homePage.getConfirmBtn().click();
			
			homePage.ensureSuccessNotificationIsVisible();
			assertEquals("Uspešno ste kupili mesecna kartu.", homePage.getSuccessNotification().getText());
			
			/*
			contactUsPage.ensureEmailInputIsVisible();
			contactUsPage.ensureOrderReferenceInputIsVisible();
			contactUsPage.ensureSendButtonIsClickable();
			
			contactUsPage.getEmailInput().sendKeys("invalidEmail");
			contactUsPage.getOrderReferenceInput().sendKeys("Random value");
			contactUsPage.getSubjectHeading().selectByVisibleText("Customer service");
			
			contactUsPage.getSendButton().click();
			//Provera da li postoji poruka o invalidnoj email adresi
			contactUsPage.ensureErrorMessageIsVisible();
			assertEquals("Invalid email address.", contactUsPage.getErrorMessage().getText());
			
			//valid email
			contactUsPage.getEmailInput().clear();
			contactUsPage.getEmailInput().sendKeys("a@gmail.com");
			
			contactUsPage.getSendButton().click();
			assertEquals("The message cannot be blank.", contactUsPage.getErrorMessage().getText());
			//set select to default 
			contactUsPage.getMessageInput().sendKeys("Random Message.");
			contactUsPage.getSendButton().click();
			
			
			
			contactUsPage.ensureSuccessMessageIsVisible();
			assertEquals("Your message has been successfully sent to our team.", contactUsPage.getSuccessMessage().getText());
			
			browser.navigate().to(homePage.getAPP_URL());*/
			
			
			
		
	}
}
