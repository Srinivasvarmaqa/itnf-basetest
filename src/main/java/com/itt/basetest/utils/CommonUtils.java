package com.itt.basetest.utils;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itt.basetest.ITTBaseTest;

public class CommonUtils {
	private static final Logger LOG = LoggerFactory.getLogger(ITTBaseTest.class);
	private static String propFileName = "config.properties";
	
	/** To Read the config.properties file
	 * @return Properties Object
	 * @throws Exception
	 */
	public static Properties getConfigProperties() throws Exception {
		Properties prop = new Properties();
		InputStream inputStream = ITTBaseTest.class.getClassLoader().getResourceAsStream(propFileName);
    	if (inputStream != null) {
    		prop.load(inputStream);
    	} else {
    		throw new FileNotFoundException("property file" + propFileName + "is not found at src/main/resources in test project");
    	}
    	return prop;
	}
	
	/**
	 * @return true if the Retry Failed Test in Config.properties
	 */
	public static boolean isRetryFailedTestEnabled() {
		boolean isretryFailedTestEnabledFlag = false;
		if (System.getProperty("RETRY_FAILED_TEST") != null) {
			if (System.getProperty("RETRY_FAILED_TEST").toLowerCase() == "true")
				isretryFailedTestEnabledFlag = true;
			else 
				isretryFailedTestEnabledFlag = false;
		} else {
			try {
				Properties prop = getConfigProperties();
				if (null != prop.getProperty("retryFailedTest") && 0 != prop.getProperty("retryFailedTest").length()
						&& "true".equalsIgnoreCase(prop.getProperty("retryFailedTest").trim())) {
					isretryFailedTestEnabledFlag = true;
				} else {
					isretryFailedTestEnabledFlag = false;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return isretryFailedTestEnabledFlag;
	}

	/**
	 * @return true if the Retry Failed Test in Config.properties
	 */
	public static boolean isFailureRecoveryForTimeoutEnabled() {
		boolean isretryFailureRecoveryForTimeoutEnabledFlag = true;
		if (System.getProperty("RETRY_ON_FAILURE_RECOVERY") != null) {
			if (System.getProperty("RETRY_ON_FAILURE_RECOVERY").toLowerCase() == "false")
				isretryFailureRecoveryForTimeoutEnabledFlag = false;
			else
				isretryFailureRecoveryForTimeoutEnabledFlag = true;
		}  else {
			try {
				Properties prop = getConfigProperties();
				if (null != prop.getProperty("retryFailedTestOnTimeout") && 0 != prop.getProperty("retryFailedTestOnTimeout").length()
						&& "false".equalsIgnoreCase(prop.getProperty("retryFailedTestOnTimeout").trim())) {
					isretryFailureRecoveryForTimeoutEnabledFlag = false;
				} else {
					isretryFailureRecoveryForTimeoutEnabledFlag = true;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return isretryFailureRecoveryForTimeoutEnabledFlag;
	}

	/**
	 * @return true if the Retry Failed Test in Config.properties
	 */
	public static int getNumberOfRetries() {
		int numberOfRetries = 1;
		if (System.getProperty("NUMBER_OF_RETRIES") != null) {
			numberOfRetries = Integer.parseInt(System.getProperty("NUMBER_OF_RETRIES"));
		} else {
			try {
				Properties prop = getConfigProperties();
				if (null != prop.getProperty("numberOfRetries")) {
					numberOfRetries = Integer.parseInt(prop.getProperty("numberOfRetries").trim());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return numberOfRetries;
	}
	
	/**
	 * @return true if the Retry Failed Test in Config.properties
	 */
	public static String getExecutionEnvironment() {
		String executionEnvironment = null;
		try {
			Properties prop = getConfigProperties();
			executionEnvironment = prop.getProperty("TEST_EXECUTION_ENVIRONMENT");
		} catch (Exception e) {
			LOG.error("Failed to get the executoin environment details" + e.getLocalizedMessage());
		}
		return executionEnvironment;
	}
}
