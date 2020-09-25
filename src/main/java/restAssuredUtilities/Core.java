package restAssuredUtilities;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;
import io.restassured.RestAssured;
import io.restassured.config.LogConfig;

import java.io.File;
import java.io.PrintStream;

import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;

public class Core extends Verify{
	private static String baseuri;
	private static final String contentType = "application/json";
	private static PrintStream ps;
	private static String headerToken = "";
	
	public static void setBaseUri() {
		baseuri = "http://localhost:8899/mysql";
	}
	
	public static void setPrintStream() {
		ps = Generic.getPrintStream();
	}
	
	public static void tokenGeneration() {
		Response response = given()
				.config(RestAssured.config.logConfig(new LogConfig(ps, true)))
				.baseUri(baseuri)
				.body(new File(Generic.getCurrentDirectory()+"/DataSource/request.txt"))
				.contentType(contentType)
				.log().all()
				.when()
				.post("/token")
				.then()
				.log().all()
				.extract().response();

		if(Core.statusCode(201, response)) {
			JsonPath json = new JsonPath(response.body().asString());
			String accessToken = json.getString("accessToken");
			Reporting.getTestCaseReference().log(Status.PASS, 
					MarkupHelper.createLabel("Access Token: " + accessToken +  " : PASS", ExtentColor.GREEN));
			headerToken = "Bearer " +accessToken;
		}
	}	

	public static Response post(String requestFilePath, String postResource) {
	Response response = given()
			.config(RestAssured.config.logConfig(new LogConfig(ps, true)))
			.baseUri(baseuri)
			.header("Authorization", headerToken)
			.body(new File(requestFilePath))
			.contentType(contentType)
			.log().all()
			.when()
			.post(postResource).andReturn();
	System.out.println("--------------------------");
	response.then().log().all();
	Reporting.getTestCaseReference().log(Status.INFO, MarkupHelper.createLabel("Post Response: " + response.prettyPrint(), ExtentColor.BLUE));
	
	return response;
	}
	
	public static Response get(String pathParamName, String pathParamValue, String getResource) {
	Response response = given()
			.baseUri(baseuri)
			.config(RestAssured.config.logConfig(new LogConfig(ps, true)))
			.header("Authorization", headerToken)
			.pathParam(pathParamName, pathParamValue)
			.log().all()		
			.when()
			.get(getResource).andReturn();
	System.out.println("--------------------------");
	response.then().log().all();
	Reporting.getTestCaseReference().log(Status.INFO, MarkupHelper.createLabel("Get Response: " + response.prettyPrint(), ExtentColor.BLUE));
	Reporting.getTestCaseReference().log(Status.INFO, MarkupHelper.createLabel("--------------------------", ExtentColor.BLUE));
	
	return response;
	}
	
	public static Response put(String pathParamName, String pathParamValue, String putResource, String requestFilePath) {
	Response response = given()
			.baseUri(baseuri)
			.config(RestAssured.config.logConfig(new LogConfig(ps, true)))
			.header("Authorization", headerToken)
			.pathParam(pathParamName, pathParamValue)
			.body(new File(requestFilePath))
			.contentType(contentType)
			.log().all()
			.when()
			.get(putResource).andReturn();
	System.out.println("--------------------------");
	response.then().log().all();
	Reporting.getTestCaseReference().log(Status.INFO, MarkupHelper.createLabel("Put Response: " + response.prettyPrint(), ExtentColor.BLUE));
	
	return response;
	}
	
	public static Response delete(String pathParamName, String pathParamValue, String deleteResource) {
	Response response = given()
			.baseUri(baseuri)
			.config(RestAssured.config.logConfig(new LogConfig(ps, true)))
			.header("Authorization", headerToken)
			.pathParam(pathParamName, pathParamValue)
			.log().all()
			.when()
			.delete(deleteResource).andReturn();
	System.out.println("--------------------------");
	response.then().log().all();
	Reporting.getTestCaseReference().log(Status.INFO, MarkupHelper.createLabel("Delete Response: " + response.prettyPrint(), ExtentColor.BLUE));
	
	return response;
	}
		
	public static Response getQuery(String pathParamName, String pathParamValue, String getResource) {
	Response response = given()
			.baseUri(baseuri)
			.config(RestAssured.config.logConfig(new LogConfig(ps, true)))
			.header("Authorization", headerToken)
			.queryParam(pathParamName, pathParamValue)
			.log().all()
			.when()
			.get(getResource).andReturn();
	System.out.println("--------------------------");
	response.then().log().all();
	Reporting.getTestCaseReference().log(Status.INFO, MarkupHelper.createLabel("Get Qurery Response: " + response.prettyPrint(), ExtentColor.BLUE));
	
	return response;
	}
}
