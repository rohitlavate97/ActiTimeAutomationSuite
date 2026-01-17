Selenium + TestNG + Log4j Automation Framework Documentation

---

# 1. Project Overview

This framework automates web application testing using:

* Selenium WebDriver
* TestNG
* Log4j 2
* Apache POI for Excel
* TestNG Listeners for screenshots

Features:

* POM design for maintainable page classes
* Centralized configuration (config.properties)
* Data-driven tests using Excel
* Thread-safe WebDriver for parallel execution
* Log4j logging for debugging & reporting
* Screenshots on test failures

---

# 2. Project Structure

```
src
├─ main/java/com/alchemist/generic
│   ├─ BaseTest.java
│   ├─ Lib.java
│   ├─ IAutoConstant.java
│   └─ TestNgListeners.java
├─ main/java/com/alchemist/pompages
│   └─ LoginPage.java
├─ test/java/com/alchemist/scripts
│   └─ TestLogin.java
resources
│   ├─ config.properties
│   └─ log4j2.xml
screenshots
logs
pom.xml
testng.xml
```

---

# 3. Configuration Files

## 3.1 config.properties

```
URL=https://online.actitime.com/mstar/login.do
ImplicitTimeOut=10
```

## 3.2 log4j2.xml

```
<?xml version="1.0" encoding="UTF-8"?>
<Configuration status="WARN">
    <Appenders>
        <Console name="Console" target="SYSTEM_OUT">
            <PatternLayout pattern="%d{HH:mm:ss.SSS} [%t] %-5level %logger{36} - %msg%n"/>
        </Console>
        <File name="FileLogger" fileName="logs/automation.log">
            <PatternLayout pattern="%d{yyyy-MM-dd HH:mm:ss} [%t] %-5level %logger{36} - %msg%n"/>
        </File>
    </Appenders>
    <Loggers>
        <Root level="info">
            <AppenderRef ref="Console"/>
            <AppenderRef ref="FileLogger"/>
        </Root>
    </Loggers>
</Configuration>
```

---

# 4. BaseTest.java

```
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

        int timeOutPeriod = Integer.parseInt(Lib.getProperty(CONFIG_PATH, "ImplicitTimeOut"));
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

    public void takeScreenshot(String testname) {
        try {
            Date date = new Date();
            String currentDate = date.toString().replaceAll(":", "_");
            File destDir = new File(".\\screenshots\\" + currentDate);
            if (!destDir.exists()) destDir.mkdirs();

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
```

---

# 5. TestNgListeners.java

```
package com.alchemist.generic;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TestNgListeners implements ITestListener {
    private static final Logger log = LogManager.getLogger(TestNgListeners.class);

    @Override
    public void onTestStart(ITestResult result) {
        log.info("===== Test Started: " + result.getName() + " =====");
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        log.info("===== Test Passed: " + result.getName() + " =====");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        log.error("===== Test Failed: " + result.getName() + " =====");
        BaseTest base = (BaseTest) result.getInstance();
        base.takeScreenshot(result.getName());
        log.info("Screenshot captured for failed test: " + result.getName());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        log.warn("===== Test Skipped: " + result.getName() + " =====");
    }

    @Override
    public void onTestFailedWithTimeout(ITestResult result) {
        onTestFailure(result);
    }

    @Override
    public void onStart(ITestContext context) {
        log.info("===== Test Suite Started: " + context.getName() + " =====");
    }

    @Override
    public void onFinish(ITestContext context) {
        log.info("===== Test Suite Finished: " + context.getName() + " =====");
    }
}
```

---

# 6. TestLogin.java

```
package com.alchemist.scripts;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.alchemist.generic.BaseTest;
import com.alchemist.generic.Lib;
import com.alchemist.pompages.LoginPage;

public class TestLogin extends BaseTest {
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
            log.info("Actual page title: " + actualTitle);
            Assert.assertEquals(actualTitle, expectedTitle);

            log.info("Login test passed.");

        } catch (Exception e) {
            log.error("Exception occurred during login test", e);
            takeScreenshot("testLogin");
            throw e;
        }

        log.info("====== Ending Test: testLogin ======");
    }
}
```

---

# 7. testng.xml

```
<suite name="Suite">
    <listeners>
        <listener class-name="com.alchemist.generic.TestNgListeners"/>
    </listeners>
    <test thread-count="5" name="Test">
        <classes>
            <class name="com.alchemist.scripts.TestLogin"/>
        </classes>
    </test>
</suite>
```

---

# 8. Notes / Best Practices

1. Use ThreadLocal<WebDriver> for true parallel execution.
2. Store all test data in Excel and configs in config.properties.
3. Use Log4j for logging instead of System.out.println.
4. Screenshots are automatically taken on test failures.
5. Use POM to keep tests maintainable and readable.

---

*End of Framework Documentation*
