package tests;

import org.testng.annotations.Test;

import restAssuredUtilities.Generic;




public class RegressionTestCase extends BaseTest{

	public static final String postRequestFilePath = Generic.getCurrentDirectory()+"/DataSource/customer/customer.txt";
	public static final String city = "london";		
	
	@Test
	public static void TestCase1() {
		String customerId = CustomerTestStep.createCustomer(postRequestFilePath);
		CustomerTestStep.getCustomerId(customerId);
		CustomerTestStep.deleteCustomerId(customerId);	
	}
		
	@Test
	public static void TestCase2() {
		String customerId = CustomerTestStep.createCustomer(postRequestFilePath);
		CustomerTestStep.getQueryCustomer(city);
		CustomerTestStep.getCustomerId(customerId);
		CustomerTestStep.deleteCustomerId(customerId);			
	}
}