package com.alchemist.pompages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {
	private WebDriver driver;
	
	@FindBy(xpath="//input[@id='username']")
	private WebElement uname;
	@FindBy(xpath="//input[@name='pwd']")
	private WebElement password;
	@FindBy(xpath="//div[text()='Login ']")
	private WebElement loginBtn;
	
	public LoginPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	public void setUname(String username) {
		uname.sendKeys(username);
	}

	public void setPassword(String pwd) {
		password.sendKeys(pwd);
	}
	
	public void clickLogin() {
		loginBtn.click();
	}
}
