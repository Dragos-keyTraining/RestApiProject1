package tests;

import org.testng.annotations.Test;

import io.restassured.http.ContentType;

import static io.restassured.RestAssured.given;
import io.restassured.response.Response;
import java.io.File;
import java.util.Map;

import io.restassured.response.Response;

public class MultiPartExample {
	
	@Test
	public void multiPartTest() {
		
		Response respone  =  given(). 
				contentType(ContentType.JSON). 
				body("{\"email\":\"test@test.com\",\"password\":\"123456\"}")
				.post("https://keytrainingtravelshop.herokuapp.com/api/users/login").
				then().extract().response();
		Map<String, String> cookies = respone.cookies();			
		
		File fisier =  new File("disney.png");
		
		Response response2 = given().
					cookies(cookies). 
					multiPart(new File("data.json")).
					multiPart(fisier).
					when(). 
					post("https://keytrainingtravelshop.herokuapp.com/api/product/uploadImage"). 
					then().extract().response();
	
		System.out.println(response2.asString());
		
	}

}
