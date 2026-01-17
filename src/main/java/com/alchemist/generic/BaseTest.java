package com.alchemist.generic;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class BaseTest implements IAutoConstant {

	// Logger instance
	protected static final Logger log = LogManager.getLogger(BaseTest.class);

	public static WebDriver driver;

	static {
		System.setProperty(CHROME_KEY, CHROME_VALUE);
		System.setProperty(GECKO_KEY, GECKO_VALUE);
		log.info("Browser drivers set successfully.");
	}

	@BeforeMethod
	public void openApp() {
		driver = new ChromeDriver();
		log.info("Chrome browser launched.");

		String url = Lib.getProperty(CONFIG_PATH, "URL");
		driver.get(url);
		log.info("Navigated to URL: " + url);

		String implicitTimeOut = Lib.getProperty(CONFIG_PATH, "ImplicitTimeOut");
		int timeOutPeriod = Integer.parseInt(implicitTimeOut);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(timeOutPeriod));
		log.info("Implicit wait set to " + timeOutPeriod + " seconds.");
	}

	@AfterMethod(alwaysRun = true)
	public void closeApplication() {
		try {
			if (driver != null) {
				driver.quit();
				log.info("Browser closed successfully.");
			}
		} catch (Exception e) {
			log.error("Error while closing browser", e);
		}
	}

	// Take screenshot and log the action
	public void takeScreenshot(String testname) {
		try {
			Date date = new Date();
			String currentDate = date.toString().replaceAll(":", "_");
			File destDir = new File(".\\screenshots\\" + currentDate);
			if (!destDir.exists()) {
				destDir.mkdirs(); // Create directories if not exist
			}

			TakesScreenshot ts = (TakesScreenshot) driver;
			File src = ts.getScreenshotAs(OutputType.FILE);
			File dest = new File(destDir, testname + "_screenshot.png");
			FileUtils.copyFile(src, dest);

			log.info("Screenshot captured for test: " + testname + " at " + dest.getAbsolutePath());
		} catch (IOException e) {
			log.error("Failed to capture screenshot for test: " + testname, e);
		}
	}
}
