package com.AutomationFramework.testBase;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;

import org.apache.maven.surefire.shade.org.apache.maven.shared.utils.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class TestBase {
	public WebDriver driver;
	public Properties p;
	public File f;
	public FileInputStream FIS;
	public static ExtentReports reports;
	public static ITestResult result;
	public static ExtentTest test;

	public void getbrowser(String browser) {
		if (browser.equalsIgnoreCase("firefox")) {
			System.out.println(System.getProperty("user.dir"));
			System.setProperty("webdriver.gecko.driver",
					System.getProperty("user.dir") + "\\src\\main\\java\\drivers\\geckodriver.exe");
			driver = new FirefoxDriver();

		} else {
			System.setProperty("webdriver.chrome.driver",
					System.getProperty("user.dir") + "\\drivers\\chromedriver.exe");
			driver = new ChromeDriver();
		}
	}

	public void loadProperties() throws IOException {
		p = new Properties();
		f = new File(System.getProperty("user.dir")
				+ "\\src\\main\\java\\com\\AutomationFramework\\Config\\config.properties");
		FIS = new FileInputStream(f);
		p.load(FIS);
		f = new File(
				System.getProperty("user.dir") + "\\src\\main\\java\\com\\AutomationFramework\\Config\\OR.properties");
		FIS = new FileInputStream(f);
		p.load(FIS);

		f = new File(System.getProperty("user.dir")
				+ "\\src\\main\\java\\com\\AutomationFramework\\Config\\homePage.properties");
		FIS = new FileInputStream(f);
		p.load(FIS);
	}

	public void getScreenShot(String imagename) throws IOException {
		File img = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		String imageLoc = System.getProperty("user.dir") + "\\src\\main\\java\\com\\AutomationFramework\\Screenshot\\";
		Calendar CalendarEvent = Calendar.getInstance();
		SimpleDateFormat S = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");
		String actualimage = imageLoc + imagename + S.format(CalendarEvent.getTime()) + ".png";
		File desFile = new File(actualimage);
		FileUtils.copyFile(img, desFile);
	}

	static {
		Calendar getCalendar = Calendar.getInstance();
		SimpleDateFormat Simple = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");
		reports = new ExtentReports(
				System.getProperty("user.dir") + "\\src\\main\\java\\com\\AutomationFramework\\Report\\test"
						+ Simple.format(getCalendar.getTime()) + ".html",
				false);
	}

	@AfterMethod
	public void getResult(ITestResult result) {
		if (result.getStatus() == ITestResult.SUCCESS) {
			test.log(LogStatus.PASS, result.getName() + "Test Pass");
		} else if (result.getStatus() == ITestResult.SKIP) {
			test.log(LogStatus.SKIP, result.getThrowable() + "Test Skipped");
		} else if (result.getStatus() == ITestResult.FAILURE) {
			test.log(LogStatus.FAIL, result.getThrowable() + "Test Failed");
		} else if (result.getStatus() == ITestResult.STARTED) {
			test.log(LogStatus.INFO, result.getName() + "Test Started");
		}
	}

	@BeforeMethod
	public void startMethod() {
		reports.startTest(result.getName());
		test.log(LogStatus.INFO, result.getName(), "Test Started");
	}

	@AfterClass
	public void endTest() {
		driver.quit();
		reports.flush();
		reports.endTest(test);
	}

	public WebElement getLocator(String locator) {
		String[] split = locator.split(":");
		String locatorType = split[0];
		String locatorValue = split[1];
		if (locatorType.equalsIgnoreCase("id")) {
			return (driver.findElement(By.id(locatorValue)));
		} else if (locatorType.equalsIgnoreCase("name")) {
			return (driver.findElement(By.name(locatorValue)));
		} else if (locatorType.equalsIgnoreCase("linkText")) {
			return (driver.findElement(By.linkText(locatorValue)));
		} else if (locatorType.equalsIgnoreCase("tagname")) {
			return (driver.findElement(By.tagName(locatorValue)));
		} else if (locatorType.equalsIgnoreCase("xpath")) {
			return (driver.findElement(By.xpath(locatorValue)));

		}
		return null;

	}

	public static void main(String[] args) throws IOException {
		TestBase test = new TestBase();
		// test.getbrowser("firefox");
		test.loadProperties();
		System.out.println(test.p.getProperty("url"));

		test.p.getProperty("test");
	}
}