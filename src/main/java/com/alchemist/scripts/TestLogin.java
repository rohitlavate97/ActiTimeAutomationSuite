package com.alchemist.scripts;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.alchemist.generic.BaseTest;
import com.alchemist.generic.Lib;
import com.alchemist.pompages.LoginPage;

public class TestLogin extends BaseTest{
	@Test
	public void testLogin() {
		LoginPage loginPage = new LoginPage(driver);
		String uname = Lib.getCellValue(EXCEL_PATH, "ValidLogin", 1, 0);
		String password = Lib.getCellValue(EXCEL_PATH, "ValidLogin", 1, 1);
		String expectedTitle = Lib.getCellValue(EXCEL_PATH, "ValidLogin", 1, 2);
		loginPage.setUname(uname);
		loginPage.setPassword(password);
		loginPage.clickLogin();
		String actualTitle = driver.getTitle();
		System.out.println(actualTitle);
		/*
		 * if(actualTitle.contains(expectedTitle)) { Assert.assertTrue(true); }
		 */
		Assert.assertEquals(actualTitle, expectedTitle);
	}
}
