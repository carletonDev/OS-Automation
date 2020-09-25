package tests;

import restAssuredUtilities.Core;
import io.restassured.response.Response;

public class CustomerTestStep {

	/**
	 * Create Customer
	 * @param requestFilePath
	 * @return customerID
	 */
	public static String createCustomer(String requestFilePath) {
		String customerID = null;
		Response createCustomerResponse = Core.post(requestFilePath, CustomerResourse.customer);
		boolean createCustomerStatus = Core.statusCode(201, createCustomerResponse);
		if(createCustomerStatus) {
			customerID = createCustomerResponse.getHeader("CustomerID");
		}
		return customerID;	
	}
	
	/**
	 * get individual customer by customerID
	 */
	public static void getCustomerId(String customerId) {
		
		Response getCustomerIdResp = Core.get(CustomerResourse.customerIDParamName, customerId, CustomerResourse.customerID);
		Core.statusCode(200, getCustomerIdResp);
		Core.jsonPathContentMatch(getCustomerIdResp, "$.customerID", customerId);		
	}
		
	public static void deleteCustomerId(String customerId) {

		Response deleteCustomerIdResp = Core.delete(CustomerResourse.customerIDParamName, 
				customerId, CustomerResourse.customerID);
		Core.statusCode(204, deleteCustomerIdResp);
	}
		
	public static void getQueryCustomer(String city) {
		Response queryResponse = Core.getQuery("city", city, CustomerResourse.customer);
		Core.statusCode(200, queryResponse);
	}
}
