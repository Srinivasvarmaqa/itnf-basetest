package com.itt.basetest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;

import com.itt.businesshelperfactory.ITTBusinessHelperFactory;
import com.itt.oms.datamodelhelper.OMSDataModelHelperFactory;
import com.itt.oms.datamodelhelper.OMSDataModelLoginUsers;
import com.itt.oms.helper.OMSHelperFactory;
import com.itt.oms.pages.common.MainWindowPage;
import com.itt.oms.pages.login.OMSLoginPage;


public class OMSBaseTest extends ITTBaseTest {
	private static final Logger LOG = LoggerFactory.getLogger(OMSBaseTest.class);

	protected OMSLoginPage oMSLoginPage;
	protected OMSDataModelLoginUsers oMSLobLaws;
	protected MainWindowPage mainWindowPage;
	protected OMSHelperFactory oMSHelperFactory;
	protected OMSDataModelHelperFactory omsDataModelHelperFactory;

	@BeforeClass(alwaysRun = true)
	public void setupBeforeClass(ITestContext ctx) throws Exception {
		LOG.info(" ---- OMSBaseTest Before Class ---- ");
		ittBusinessHelperFactory = ITTBusinessHelperFactory.getInstance();
		oMSHelperFactory = ittBusinessHelperFactory.getOMSHelperFactory();
		oMSLoginPage = oMSHelperFactory.getOMSLoginPage();
		mainWindowPage = oMSHelperFactory.getmainWindowPage();
		omsDataModelHelperFactory = ittDataModelHelperFactory.getOmsDataModelHelperFactory();
		oMSLobLaws = omsDataModelHelperFactory.getOmsDataModelLoginUsers();
	}
	
	@AfterMethod(alwaysRun = true)
	public void afterMethodOMSBaseTest() throws Exception {
		try {
			oMSHelperFactory.logout();
		} catch (Exception e) {
			LOG.debug("User might have logged out already");
		}
	}
}
