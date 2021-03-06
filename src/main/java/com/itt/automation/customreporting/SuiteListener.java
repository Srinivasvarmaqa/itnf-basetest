package com.itt.automation.customreporting;

import java.util.Iterator;

import org.testng.ITestContext;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

public class SuiteListener extends TestListenerAdapter {

    @Override  
   public void onFinish(ITestContext context) {
     Iterator<ITestResult> failedTestCases =context.getFailedTests().getAllResults().iterator();
     Iterator<ITestResult> skippedTestCases =context.getSkippedTests().getAllResults().iterator();
     while (skippedTestCases.hasNext()) {
         System.out.println("skippedTestCases");
         ITestResult skippedTestCase = skippedTestCases.next();
         ITestNGMethod method = skippedTestCase.getMethod();
         if (context.getSkippedTests().getResults(method).size() > 1) {
             System.out.println("skipped test case remove as dup:" + skippedTestCase.getTestClass().toString());
             skippedTestCases.remove();
          
         } 
         else {

             if (context.getPassedTests().getResults(method).size() > 0) {
                 System.out.println("skipped test case remove as pass retry:" + skippedTestCase.getTestClass().toString());
                 skippedTestCases.remove();
             }
             
             if (context.getFailedTests().getResults(method).size() > 0) {
                 System.out.println("skipped test case remove as fail retry:" + skippedTestCase.getTestClass().toString());
                 skippedTestCases.remove();
             }
             
         }
     }
     
    while (failedTestCases.hasNext()) {
        System.out.println("failedTestCases");
        ITestResult failedTestCase = failedTestCases.next();
        ITestNGMethod method = failedTestCase.getMethod();
        if (context.getFailedTests().getResults(method).size() > 1) {
            System.out.println("failed test case remove as dup:" + failedTestCase.getTestClass().toString());
            failedTestCases.remove();
         
        } 
        else {

            if (context.getPassedTests().getResults(method).size() > 0) {
                System.out.println("failed test case remove as pass retry:" + failedTestCase.getTestClass().toString());
                failedTestCases.remove();
            }
        }
    }

    
   }
}