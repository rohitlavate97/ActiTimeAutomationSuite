package com.alchemist.scripts;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.alchemist.generic.BaseTest;
import com.alchemist.generic.Lib;
import com.alchemist.pompages.LoginPage;

public class TestLogin extends BaseTest {

    // Logger instance
    private static final Logger log = LogManager.getLogger(TestLogin.class);

    @Test
    public void testLogin() {
        log.info("====== Starting Test: testLogin ======");

        try {
            LoginPage loginPage = new LoginPage(driver);

            String uname = Lib.getCellValue(EXCEL_PATH, "ValidLogin", 1, 0);
            String password = Lib.getCellValue(EXCEL_PATH, "ValidLogin", 1, 1);
            String expectedTitle = Lib.getCellValue(EXCEL_PATH, "ValidLogin", 1, 2);

            log.info("Logging in with username: " + uname);

            loginPage.setUname(uname);
            log.info("Entered username.");

            loginPage.setPassword(password);
            log.info("Entered password.");

            loginPage.clickLogin();
            log.info("Clicked on Login button.");

            String actualTitle = driver.getTitle();
            log.info("Actual page title after login: " + actualTitle);

            Assert.assertEquals(actualTitle, expectedTitle);
            log.info("Login test passed. Page title matches expected: " + expectedTitle);

        } catch (Exception e) {
            log.error("Exception occurred during login test", e);
            takeScreenshot("testLogin"); // Take screenshot on exception
            throw e; // Re-throw to fail the test
        }

        log.info("====== Ending Test: testLogin ======");
    }
}
