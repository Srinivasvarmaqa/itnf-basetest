package com.itt.basetest.utils;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
 
public class TestResultsParser  {
 
	public static void main(String argv[]) {
		int total_tests_chrome = 0;
		int total_passed_tests_chrome = 0;
                int total_skipped_tests_chrome = 0;
		int total_failed_tests_chrome = 0;
		int total_tests_firefox = 0;
		int total_passed_tests_firefox = 0;
		int total_failed_tests_firefox = 0;
		int total_skipped_tests_firefox = 0;
		int total_tests_edge = 0;
		int total_passed_tests_edge = 0;
		int total_failed_tests_edge = 0;
		int total_skipped_tests_edge = 0;
		try {
			File fXmlFile = new File(
					"output_codebase_chrome/test-output/testng-results.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();
 
			total_tests_chrome = Integer.parseInt(doc.getDocumentElement().getAttribute("total"));
			total_passed_tests_chrome = Integer.parseInt(doc.getDocumentElement().getAttribute("passed"));
			total_failed_tests_chrome = Integer.parseInt(doc.getDocumentElement().getAttribute("failed"));
			total_skipped_tests_chrome = Integer.parseInt(doc.getDocumentElement().getAttribute("skipped"));
 
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			File fXmlFile = new File(
					"output_codebase_firefox/test-output/testng-results.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();
 
			total_tests_firefox = Integer.parseInt(doc.getDocumentElement().getAttribute("total"));
			total_passed_tests_firefox = Integer.parseInt(doc.getDocumentElement().getAttribute("passed"));
			total_failed_tests_firefox = Integer.parseInt(doc.getDocumentElement().getAttribute("failed"));
			total_skipped_tests_firefox = Integer.parseInt(doc.getDocumentElement().getAttribute("skipped"));
 
		} catch (Exception e) {
			e.printStackTrace();
		}
		
                try {
                        File fXmlFile = new File(
                                        "output_codebase_edge/test-output/testng-results.xml");
                        DocumentBuilderFactory dbFactory = DocumentBuilderFactory
                                        .newInstance();
                        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                        Document doc = dBuilder.parse(fXmlFile);
                        doc.getDocumentElement().normalize();
 
			total_tests_edge = Integer.parseInt(doc.getDocumentElement().getAttribute("total"));
			total_passed_tests_edge = Integer.parseInt(doc.getDocumentElement().getAttribute("passed"));
			total_failed_tests_edge = Integer.parseInt(doc.getDocumentElement().getAttribute("failed"));
			total_skipped_tests_edge = Integer.parseInt(doc.getDocumentElement().getAttribute("skipped"));
 
                } catch (Exception e) {
                        e.printStackTrace();
                }
 
		System.out.println("Total_chrome: " + total_tests_chrome);
		System.out.println("Passed_chrome: " + total_passed_tests_chrome);
		System.out.println("Failed_chrome: " + total_failed_tests_chrome);
		System.out.println("Skipped_chrome: " + total_skipped_tests_chrome);
		
		System.out.println("Total_firefox: " + total_tests_firefox);
		System.out.println("Passed_firefox: " + total_passed_tests_firefox);
		System.out.println("Failed_firefox: " + total_failed_tests_firefox);
		System.out.println("Skipped_firefox: " + total_skipped_tests_firefox);
		
		System.out.println("Total_edge: " + total_tests_edge);
		System.out.println("Passed_edge: " + total_passed_tests_edge);
		System.out.println("Failed_edge: " + total_failed_tests_edge);
		System.out.println("Skipped_edge: " + total_skipped_tests_edge);
 
	}
}

