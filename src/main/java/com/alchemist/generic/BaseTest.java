package com.alchemist.generic;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
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
		String url = Lib.getProperty(CONFIG_PATH, "URL");
		driver.get(url);
		String implicitTimeOut = Lib.getProperty(CONFIG_PATH, "ImplicitTimeOut");
		int timeOutPeriod = Integer.parseInt(implicitTimeOut);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(timeOutPeriod));
	}
	
	@AfterMethod
	public void closeApplication() {
		driver.close();
	}
	
	public void takeScreenshot(String testname) {
		Date date = new Date();
		String currentDate = date.toString().replaceAll(":", "_");
		TakesScreenshot ts = (TakesScreenshot)driver;
		File src = ts.getScreenshotAs(OutputType.FILE);
		File dest = new File(".\\screenshots\\"+currentDate+"\\"+testname+"_screenshot.png");
		try {
			FileUtils.copyFile(src, dest);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
