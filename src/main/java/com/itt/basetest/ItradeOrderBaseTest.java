package com.itt.basetest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;

import com.itt.businesshelperfactory.ITTBusinessHelperFactory;
import com.itt.itradeorder.datamodelhelper.ItradeOrderDataModelHelperFactory;
import com.itt.itradeorder.datamodelhelper.ItradeOrderDataModelLoginUsers;
import com.itt.itradeorder.helper.ItradeOrderHelperFactory;
import com.itt.itradeorder.pages.ItradeOrderLoginPage;


public class ItradeOrderBaseTest extends ITTBaseTest {
	private static final Logger LOG = LoggerFactory.getLogger(ItradeOrderBaseTest.class);

	protected ItradeOrderLoginPage itradeOrderLoginPage;
	protected ItradeOrderDataModelLoginUsers itradeOrderUsers;
	protected ItradeOrderHelperFactory itradeOrderHelperFactory;
	protected ItradeOrderDataModelHelperFactory itradeOrderDataModelHelperFactory;

	@BeforeClass(alwaysRun = true)
	public void setupBeforeClass(ITestContext ctx) throws Exception {
		LOG.info(" ---- ItradeOrderBaseTest Before Class ---- ");
		ittBusinessHelperFactory = ITTBusinessHelperFactory.getInstance();
		itradeOrderHelperFactory = ittBusinessHelperFactory.getItradeOrderHelperFactory();
		itradeOrderLoginPage = itradeOrderHelperFactory.getItradeOrderLoginPage();
		itradeOrderDataModelHelperFactory = ittDataModelHelperFactory.getItradeOrderDataModelHelperFactory();
		itradeOrderUsers = itradeOrderDataModelHelperFactory.getItradeOrderDataModelLoginUsers();
	}
	

	@AfterMethod(alwaysRun = true)
	public void afterMethodItradeOrderBaseTest() throws Exception {
		try {
			itradeOrderHelperFactory.logout();
		} catch (Exception e) {
			LOG.info("User might have logged out already");
		}
	}
}
