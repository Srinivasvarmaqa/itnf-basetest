package com.itt.parser.testdata;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
import org.testng.annotations.DataProvider;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itt.basetest.ITTBaseTest;
import com.itt.basetest.utils.CommonUtils;
import com.itt.datamodelfactoryhelper.ITTDataModelHelperFactory;
import com.itt.itradeorder.datamodelhelper.ItradeOrderDataModelHelperFactory;
import com.itt.spend.datamodelhelper.SPENDDataModelHelperFactory;
import com.itt.oms.datamodelhelper.OMSDataModelHelperFactory;

public class TestDataProviders extends ITTBaseTest {

	private static final Logger LOG = LoggerFactory.getLogger(TestDataProviders.class);
	private static final String FILE_SEPARATOR = System.getProperty("file.separator");
	private final static String testDataDir = System.getProperty("user.dir") + FILE_SEPARATOR + "src" + FILE_SEPARATOR + "test" + FILE_SEPARATOR
			+ "resources" + FILE_SEPARATOR + "TestData" + FILE_SEPARATOR;
	private final static String OMS = "oms";
	private final static String SPEND = "spend";
	private final static String ITRADEORDER = "itradeorder";
	OMSDataModelHelperFactory omsDataModelHelperFactory;
	SPENDDataModelHelperFactory spendDataModelHelperFactory;
	ItradeOrderDataModelHelperFactory itradeOrderDataModelHelperFactory;
	private String testEnvironment;
	private String product;
	private JsonNode testExecutionEnvironment;
	private File jsonFile;

	@DataProvider
	public Object[][] testDataProvider(final ITestContext context,
			final Method m) throws Exception {
		if (m.isAnnotationPresent(TestData.class)) {
			TestData dataSource = m.getAnnotation(TestData.class);

			List<Object[]> result = new ArrayList<Object[]>();
			for (String dataFile : dataSource.dataFilePath()) {

				jsonFile = new File(testDataDir + dataFile);

				ittDataModelHelperFactory = ITTDataModelHelperFactory.getInstance();
				ObjectMapper objectMapper = new ObjectMapper();
				objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
				JsonNode node = objectMapper.readTree(jsonFile);

				testEnvironment = CommonUtils.getExecutionEnvironment();
				if (testEnvironment == null) {
					throw new Exception("Execution environment is not provided");
				}

				if (node.isArray()) {
				     Object[] datamodelobjects = new Object[node.size()];
				     int i = 0;
					for (JsonNode jsonNode : node) {
						product = jsonNode.get("product").asText();
						LOG.info("TEST EXECUTION ENV:" + testEnvironment);
						try {
							testExecutionEnvironment = jsonNode.at("/" + testEnvironment);
						} catch (Exception e) {
							LOG.error("Data file is not found for the test environment" + testEnvironment);
							throw new Exception("Data file is not found for the test environment");
						}
						switch (product.toLowerCase()) {
						case OMS:
							omsDataModelHelperFactory = ittDataModelHelperFactory.getOmsDataModelHelperFactory();
							omsDataModelHelperFactory = objectMapper.treeToValue(testExecutionEnvironment, OMSDataModelHelperFactory.class);
							datamodelobjects[i] = omsDataModelHelperFactory;
							break;
						case SPEND:
							spendDataModelHelperFactory = ittDataModelHelperFactory.getSpendDataModelHelperFactory();
							spendDataModelHelperFactory = objectMapper.treeToValue(testExecutionEnvironment, SPENDDataModelHelperFactory.class);
							datamodelobjects[i] = spendDataModelHelperFactory;
							break;
						case ITRADEORDER:
							itradeOrderDataModelHelperFactory = ittDataModelHelperFactory.getItradeOrderDataModelHelperFactory();
							itradeOrderDataModelHelperFactory = objectMapper.treeToValue(testExecutionEnvironment, ItradeOrderDataModelHelperFactory.class);
							datamodelobjects[i] = itradeOrderDataModelHelperFactory;
							break;
						default:
							LOG.error("Error! unknown product name: " + product);
							throw new Exception("Incorrect product Name");
						}
						i++;
					}
					result.add(datamodelobjects);
				} else {
					product = node.get("product").asText();
					LOG.info("TEST EXECUTION ENV:" + testEnvironment);
					try {
						testExecutionEnvironment = node.at("/" + testEnvironment);
					} catch (Exception e) {
						LOG.error("Data file is not found for the test environment" + testEnvironment);
						throw new Exception("Data file is not found for the test environment");
					}

					switch (product.toLowerCase()) {
					case OMS :
						omsDataModelHelperFactory = ittDataModelHelperFactory.getOmsDataModelHelperFactory();
						omsDataModelHelperFactory = objectMapper.treeToValue(testExecutionEnvironment, OMSDataModelHelperFactory.class);
						result.add(new Object[]{omsDataModelHelperFactory});
						break;
					case SPEND :
						spendDataModelHelperFactory = ittDataModelHelperFactory.getSpendDataModelHelperFactory();
						spendDataModelHelperFactory = objectMapper.treeToValue(testExecutionEnvironment, SPENDDataModelHelperFactory.class);
						result.add(new Object[]{spendDataModelHelperFactory});
						break;

					case ITRADEORDER :
						itradeOrderDataModelHelperFactory = ittDataModelHelperFactory.getItradeOrderDataModelHelperFactory();
						itradeOrderDataModelHelperFactory = objectMapper.treeToValue(testExecutionEnvironment, ItradeOrderDataModelHelperFactory.class);
						result.add(new Object[]{itradeOrderDataModelHelperFactory});
						break;
					default :
						LOG.error("Error! unknown product name: " + product);
						throw new Exception("Incorrect product Name");
					}
			  }
			}
			if (result.size() <= 0) {
				throw new Error("Test Data CSV File is Empty");
			}
			return result.toArray(new Object[result.size()][]);
		} else {
			throw new Exception("Couldn't parse the data file");
		}
	}
}
