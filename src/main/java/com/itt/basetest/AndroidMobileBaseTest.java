package com.itt.basetest;

import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.itt.common.BrowserInfo;
import com.itt.common.DeviceInfo;
import com.itt.factoryhelper.MobileHelperFactory;
import com.itt.mobile.common.android.AndroidLocator;

public class AndroidMobileBaseTest {

	private static final Logger LOG = LoggerFactory.getLogger(AndroidMobileBaseTest.class);

	protected WebDriver driver;
	public static BrowserInfo browserInfo;

	@BeforeTest
	public void setupDriver(ITestContext ctx) throws Exception {

		DeviceInfo device = new DeviceInfo();
		device.setDeviceID("09281FDD4004WS");
		device.setPlatformName("Android");
		MobileHelperFactory.ITTInitDriver(device, null);

	}

	@Test
	public void verifyPageTitle() throws Exception

	{
		MobileHelperFactory.getMobileDriver().navigateToUrl("https://appium.io/docs/en/commands/web/navigation/refresh/");
		MobileHelperFactory.getMobileDriver().click(AndroidLocator.byXpath("//a[contains(text(),'W3C Specification')]"));
		System.out.println(MobileHelperFactory.getMobileDriver().getPageSource());
	}

	@AfterTest
	public void quitDriver() throws Exception {

	}

}
