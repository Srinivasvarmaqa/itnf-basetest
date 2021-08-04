package com.itt.basetest;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;

import com.itt.automation.customreporting.CustomEmailableReport;
import com.itt.automation.customreporting.SuiteListener;
import com.itt.automation.screenshot.ScreenShotUtility;
import com.itt.businesshelperfactory.ITTBusinessHelperFactory;
import com.itt.common.BrowserInfo;
import com.itt.datamodelfactoryhelper.ITTDataModelHelperFactory;
import com.itt.factoryhelper.BrowserHelperFactory;
import com.itt.itradeorder.datamodelhelper.ItradeOrderDataModelHelperFactory;
import com.itt.itradeorder.helper.ItradeOrderHelperFactory;
import com.itt.oms.datamodelhelper.OMSDataModelHelperFactory;
import com.itt.oms.helper.OMSHelperFactory;
import com.itt.omsrewrite.helper.OMSRewriteHelperFactory;
import com.itt.spend.datamodelhelper.SPENDDataModelHelperFactory;
import com.itt.spend.helper.SPENDHelperFactory;

@Listeners({ScreenShotUtility.class, CustomEmailableReport.class , SuiteListener.class})
public class ITTBaseTest {
	private static final Logger LOG = LoggerFactory.getLogger(ITTBaseTest.class);

    public static BrowserInfo browserInfo;
    public ITTBusinessHelperFactory ittBusinessHelperFactory;
    protected ITTDataModelHelperFactory ittDataModelHelperFactory;
	protected ItradeOrderHelperFactory itradeOrderHelperFactory;
	protected ItradeOrderDataModelHelperFactory itradeOrderDataModelHelperFactory;
	protected OMSDataModelHelperFactory omsDataModelHelperFactory;
	protected OMSHelperFactory oMSHelperFactory;
	protected SPENDDataModelHelperFactory spendDataModelHelperFactory;
	protected SPENDHelperFactory sPENDHelperFactory;

	protected OMSRewriteHelperFactory oMSRewriteHelperFactory;
    protected String APP_URL;
    protected String HUB_URL;
    protected String BROWSER;
    protected String TEST_EXECUTION_ENVIRONMENT;

   
    /**
	 * BeforeSuite operations. Add operations that need to be done at global level
	 * or once per suite or site level.
	 *
	 * @param context
	 * @throws Exception
	 */
	@BeforeSuite(alwaysRun = true)
	public synchronized void baseBeforeSuite(final ITestContext context) throws Exception {

		// Delete and Create ScreenShots directory //
		final String dirPath = System.getProperty("user.dir") + File.separator + "ScreenShots";
		File theDir = new File(dirPath);
		try {
			FileUtils.deleteDirectory(theDir);
		} catch (Exception e) {
			LOG.info("Failed to delete the screenshots directory");
		}
		try {
			theDir.mkdir();
		} catch (Exception se) {
				LOG.info("Failed to create the screenshots directory");
		}
		LOG.info("----baseBeforeSuite execution completed----");
	}

    @BeforeTest
    public void setupDriver(final ITestContext context) throws Exception {
    	LOG.info("Initiate the setup");
    	String propFileName = "config.properties";
    	String hubUrl;
    	Properties prop = new Properties();
    	InputStream inputStream = ITTBaseTest.class.getClassLoader().getResourceAsStream(propFileName);

    	if (inputStream != null) {
    		prop.load(inputStream);
    	} else {
    		throw new FileNotFoundException("property file" + propFileName + "is not found at src/main/resources in test project");
    	}
		if (System.getProperty("APP_URL") != null) {
			APP_URL = System.getProperty("APP_URL");
		} else if (null != prop.getProperty("APP_URL")) {
			APP_URL = prop.getProperty("APP_URL");
		} else {
			throw new Exception("APP URL IS NOT SET");
		}

		LOG.info("APP_URL: " + APP_URL);

		if (System.getProperty("HUB_URL") != null) {
			HUB_URL = System.getProperty("HUB_URL");
		} else if (null != prop.getProperty("HUB_URL")) {
			HUB_URL = prop.getProperty("HUB_URL");
		}

		if (HUB_URL != null) {
			hubUrl = HUB_URL + "/wd/hub";
			LOG.info("HUB_URL: " + hubUrl);
		} else {
			hubUrl = null;
		}
		
		if (System.getProperty("BROWSER") != null) {
			BROWSER = System.getProperty("BROWSER");
		} else if (null != prop.getProperty("BROWSER")) {
			BROWSER = prop.getProperty("BROWSER");
		}

		LOG.info("BROWSER: " + BROWSER);

		if (null != prop.getProperty("TEST_EXECUTION_ENVIRONMENT")) {
			TEST_EXECUTION_ENVIRONMENT = prop.getProperty("TEST_EXECUTION_ENVIRONMENT");
		} else {
			throw new Exception("TEST_EXECUTION_ENVIRONMENT IS NOT SPECIFIED");
		}
		LOG.info("TEST_EXECUTION_ENVIRONMENT: " + TEST_EXECUTION_ENVIRONMENT);

		ittBusinessHelperFactory = ITTBusinessHelperFactory.getInstance();
		ittDataModelHelperFactory = ITTDataModelHelperFactory.getInstance();

		browserInfo = new BrowserInfo() {
			{
				setBrowser(BROWSER);
				setSeleniumHubURL(hubUrl);
			}
		};
		BrowserHelperFactory.initBrowserDriver(browserInfo);
		BrowserHelperFactory.getBrowserDriver().invokeDriver();
		itradeOrderHelperFactory = ittBusinessHelperFactory.getItradeOrderHelperFactory();
		omsDataModelHelperFactory = ittDataModelHelperFactory.getOmsDataModelHelperFactory();
		oMSHelperFactory = ittBusinessHelperFactory.getOMSHelperFactory();
		spendDataModelHelperFactory = ittDataModelHelperFactory.getSpendDataModelHelperFactory();
		sPENDHelperFactory = ittBusinessHelperFactory.getSPENDHelperFactory();

		oMSRewriteHelperFactory = ittBusinessHelperFactory.getOMSRewriteHelperFactory();

		LOG.info("BROWSER VERSION:" + browserInfo.getBrowserVersion());
		context.setAttribute("BROWSER_NAME", browserInfo.getBrowser());
		context.setAttribute("BROWSER_VERSION", browserInfo.getBrowserVersion());
		context.setAttribute("APP_URL", APP_URL);
		context.setAttribute("TEST_EXECUTION_ENVIRONMENT", TEST_EXECUTION_ENVIRONMENT);
	}
    
   // @AfterTest
    public void afterTestITTBaseTest() throws Exception {
    	BrowserHelperFactory.getBrowserDriver().close();
    	BrowserHelperFactory.getBrowserDriver().quit();
    }

}
