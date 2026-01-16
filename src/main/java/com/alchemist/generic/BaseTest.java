package com.alchemist.generic;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.BeforeMethod;

public class BaseTest implements IAutoConstant{
	public static WebDriver driver;
	static {
		System.setProperty(CHROME_KEY, CHROME_VALUE);
		System.setProperty(GECKO_KEY, GECKO_VALUE);
	}
	
	@BeforeMethod
	public void openApp() {
		driver = new ChromeDriver();
	}
}
