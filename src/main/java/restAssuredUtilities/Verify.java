package restAssuredUtilities;

import io.restassured.response.Response;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;

import static com.jayway.jsonpath.matchers.JsonPathMatchers.*;

public class Verify {

public static boolean statusCode(int expectedStatusCode, Response resp) {
		
	int actualStatusCode = 0;
		try {

			actualStatusCode = resp.getStatusCode();
			assertThat(actualStatusCode, is(expectedStatusCode));
			Generic.getPrintStream().println("Status Code matched : PASS");
			Reporting.getTestCaseReference().info("Validating the status code");
			Reporting.getTestCaseReference().log(Status.PASS, 
					MarkupHelper.createLabel("Status Code " + actualStatusCode + " matched expected status code " + expectedStatusCode + " : PASS", ExtentColor.GREEN));
			System.out.println("Status Code matched : PASS");
			return true;
		}catch(AssertionError e) {
			System.out.println("Status code mismatch");
			Reporting.getTestCaseReference().log(Status.FAIL, 
					MarkupHelper.createLabel("Status Code " + actualStatusCode + " does not match " + expectedStatusCode + " : FAIL", ExtentColor.RED));
			return false;
		}			
	}
	
	public static boolean jsonPathExistence(Response resp, String jsonpath) {
		try {
			String responseBody = resp.getBody().asString();
			assertThat(responseBody, hasJsonPath(jsonpath));
			Reporting.getTestCaseReference().info("providing information");
			Reporting.getTestCaseReference().log(Status.PASS, 
					MarkupHelper.createLabel("Status Code matched : PASS", ExtentColor.GREEN));
			System.out.println("jsonPath exists : PASS");
			return true;
		}catch(AssertionError e) {
			Reporting.getTestCaseReference().log(Status.FAIL, 
					MarkupHelper.createLabel("Status Code matched : FAIL", ExtentColor.RED));
			System.out.println("jsonPath does not exist");
			return false;
		}
	}
	
	public static boolean jsonPathContentMatch(Response resp, String jsonpath, String expectedJsonPathContent) {
		String responseBody = null;
		try {
		 responseBody = resp.getBody().asString();
			assertThat(responseBody, hasJsonPath(jsonpath,equalTo(expectedJsonPathContent)));
			System.out.println("jsonPath content : PASS");
			return true;
		}catch(AssertionError e) {
			Reporting.getTestCaseReference().log(Status.FAIL, 
					MarkupHelper.createLabel("Json path content " + responseBody +  " does not match matched " + expectedJsonPathContent + ": FAIL", ExtentColor.RED));
			System.out.println("jsonPath path content Fail");
			return false;
		}
	}	
}