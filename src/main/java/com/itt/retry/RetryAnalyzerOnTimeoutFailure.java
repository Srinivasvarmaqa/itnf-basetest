package com.itt.retry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

import com.itt.basetest.utils.CommonUtils;

public class RetryAnalyzerOnTimeoutFailure implements IRetryAnalyzer{
	private static final Logger LOG = LoggerFactory.getLogger(RetryAnalyzerOnTimeoutFailure.class);

	private int counter = 0;
	private int retryLimit = CommonUtils.getNumberOfRetries();

	public boolean retry(ITestResult result) {
		
		if (result.getThrowable().toString().contains("concurrent.TimeoutException")) {
			if (counter < retryLimit) {
				counter++;
				LOG.info("Retrying the execution due to concurrent timeout exception");
				return true;
			}
		}
		return false;
	}
}
