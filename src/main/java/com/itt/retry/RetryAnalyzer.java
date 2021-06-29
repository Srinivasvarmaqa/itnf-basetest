package com.itt.retry;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

import com.itt.basetest.utils.CommonUtils;

public class RetryAnalyzer implements IRetryAnalyzer{

	private int counter = 0;
	private int retryLimit = CommonUtils.getNumberOfRetries();

	public boolean retry(ITestResult result) {
		if (counter < retryLimit) {
			counter++;
			return true;
		}
		return false;
	}
}
