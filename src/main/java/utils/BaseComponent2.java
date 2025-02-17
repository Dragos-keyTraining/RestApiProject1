package utils;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import reportutils.ExtentManager;
import testdata.DataBuilder;

public class BaseComponent2 {
	
	String token;
	public static RequestSpecification requestSpec;
	public static ResponseSpecification responseSpec, nevativeResponseSpec;
	
	@BeforeSuite
	public void beforeSuite() {
		ExtentManager.setExtent();
	}
	
	@AfterSuite
	public void afterSuite() {
		ExtentManager.endReport();
	}
	
	
	@BeforeClass
	public void setup() {
	
		Response responseToken = given(). 
				contentType(ContentType.JSON). 
				body(DataBuilder.buildToken().toJSONString()). 
				post("https://dev-todo-b369f85c9f07.herokuapp.com/api/login")
				.then().extract().response();
				
		token = responseToken.jsonPath().getString("token");
		
		requestSpec = new RequestSpecBuilder(). 
				setContentType(ContentType.JSON). 
				setBaseUri("https://dev-todo-b369f85c9f07.herokuapp.com/"). 
				setBasePath("api/"). 
				addHeader("Authorization", "Bearer " +token).
				addHeader("accept", "application/json").build();
		
		
		responseSpec = new ResponseSpecBuilder(). 
				expectStatusCode(either(is(200)).or(is(201)).or(is(204)))
				.build();
		
		nevativeResponseSpec = new ResponseSpecBuilder(). 
				expectStatusCode(either(is(403)).or(is(405)).or(is(406)))
				.build();
	}
	
	
	public static Response doPostRequest(String path, String body) {
		
		Response response = given().
				spec(requestSpec).
				body(body).
			when().	
				post(path). 
			then(). 
				spec(responseSpec).
				extract().response();
			return response;	
	}
	
	public static Response doPatchRequest(String path, String body) {
		
		Response response = given().
				spec(requestSpec).
				body(body).
			when().	
				patch(path). 
			then(). 
				spec(responseSpec).
				extract().response();
			return response;	
	}
	
	
	public static Response doGetRequest(String path) {
		
		Response response = given().
				spec(requestSpec).
			when().	
				get(path). 
			then(). 
				spec(responseSpec).
				extract().response();
			return response;	
	}
	
	public static Response doDeleteRequest(String path) {
		
		Response response = given().
				spec(requestSpec).
			when().	
				delete(path). 
			then(). 
				spec(responseSpec).
				extract().response();
			return response;	
	}
}
