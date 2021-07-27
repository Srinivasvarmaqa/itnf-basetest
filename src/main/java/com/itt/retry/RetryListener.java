package com.itt.retry;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import org.testng.IAnnotationTransformer2;
import org.testng.annotations.IConfigurationAnnotation;
import org.testng.annotations.IDataProviderAnnotation;
import org.testng.annotations.IFactoryAnnotation;
import org.testng.annotations.ITestAnnotation;

import com.itt.basetest.utils.CommonUtils;

public class RetryListener implements IAnnotationTransformer2 {

	public void transform(ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod) {

		if (annotation.getRetryAnalyzer() == null && CommonUtils.isRetryFailedTestEnabled()) {
			annotation.setRetryAnalyzer(RetryAnalyzer.class);
		} else if (annotation.getRetryAnalyzer() == null && CommonUtils.isFailureRecoveryForTimeoutEnabled())
				annotation.setRetryAnalyzer(RetryAnalyzerOnTimeoutFailure.class);
	}

	@Override
	public void transform(IConfigurationAnnotation annotation, Class testClass,
			Constructor testConstructor, Method testMethod) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void transform(IDataProviderAnnotation annotation, Method method) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void transform(IFactoryAnnotation annotation, Method method) {
		// TODO Auto-generated method stub
		
	}
}
