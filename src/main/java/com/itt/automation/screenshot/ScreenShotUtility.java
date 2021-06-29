package com.itt.automation.screenshot;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;
import static com.itt.factoryhelper.BrowserHelperFactory.getBrowserDriver;

public class ScreenShotUtility extends TestListenerAdapter {
	private static final Logger LOG = LoggerFactory.getLogger(ScreenShotUtility.class);
	private ITestResult result = null;
	final String homeDir = System.getProperty("user.dir");

	/** 
	 * This method will execute after the test (@Test) failure
	 */
	public void onTestFailure(ITestResult tr) {
		try {
			int randomNum = ThreadLocalRandom.current().nextInt(1, 5000 + 1);
			String nameOfTheTest = tr.getMethod().getRealClass().getSimpleName()+ "_"+randomNum+"_"+ tr.getMethod().getMethodName()+ "_" + tr.getTestContext().getName();
			captureScreenShot(tr, "fail");
			LOG.info("TEST CASE FAILED :" + nameOfTheTest);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/** 
	 * Function to capture screenshot.
	 * @param result
	 * @param status
	 * @throws Exception
	 */
	 public void captureScreenShot(ITestResult result, String status) throws Exception {
		System.setProperty("org.uncommons.reportng.escape-output", "false");
		LOG.info("Taking Screenshot");
		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		String method = result.getMethod().getRealClass().getSimpleName() + "." + result.getMethod().getMethodName();
		String fileName = result.getTestContext().getName();
		String browserName = result.getTestContext().getAttribute("BROWSER_NAME").toString();
		String browserVersion = result.getTestContext().getAttribute("BROWSER_VERSION").toString();
		LOG.info("FILE NAME>.." +fileName);
		String imageFilePath = homeDir + File.separator + "ScreenShots" + File.separator + method + "_" + fileName+ "_" + browserName+ "_" + browserVersion + "_" + timeStamp;
		
        
		LOG.info("IMAGE FILEPATH>.." +imageFilePath);
		final HashMap<String, String> params = new HashMap<String, String>();
		params.put("FilePath", imageFilePath);
		try {
			LOG.info("STARTED CAPTURING SCREENSHOT NOW");
			getBrowserDriver().takeScreenShot(params);
			LOG.info("Screenshot saved at " + imageFilePath);
		} catch (Exception e) {
			LOG.info("Exception while taking device screenshot "+e.getMessage());
		}
	}
}
