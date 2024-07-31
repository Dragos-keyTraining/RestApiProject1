package tests;

import org.testng.annotations.Test;

import io.restassured.response.Response;
import testdata.DataBuilder;
import utils.BaseComponent2;

public class BaseComponent2Test extends BaseComponent2{

	@Test
	public void postTodoWithToken() {
		
		Response response = doPostRequest("save", DataBuilder.buildTodo().toJSONString());
		
		System.out.println(response.asPrettyString());
		
	}
	
	
}
