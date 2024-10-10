package stepDefinations;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.*;

import java.io.IOException;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import resources.APIResources;
import resources.TestDataBuilder;
import resources.Utils;

public class StepDefination extends Utils{
	
	RequestSpecification res;
	ResponseSpecification resspec;
	Response response;
	TestDataBuilder data = new TestDataBuilder();
	static String place_Id;
	JsonPath js;
	
	@Given("Add Place Payload with {string} {string} {string}")
	public void add_place_payload_with(String name, String language, String address) throws IOException {
				 
		res=given().spec(requestSpecification())
		.body(data.addPlacePayload(name, language, address));
	}
	
	@When("user calls {string} with {string} http request")
	public void user_calls_with_http_request(String resource, String method) {
		APIResources resourceAPI = APIResources.valueOf(resource);
		
		resspec = new ResponseSpecBuilder().expectStatusCode(200)
				 .expectContentType(ContentType.JSON).build();
		
		if(method.equalsIgnoreCase("Post")) {
			response =res.when().post(resourceAPI.getResource());
		}else if(method.equalsIgnoreCase("Get")){
		response =res.when().get(resourceAPI.getResource());
		}
	}

	@Then("the API call got success with status code {int}")
	public void the_api_call_got_success_with_status_code(Integer int1) {
	    assertEquals(response.getStatusCode(), 200);
	}

	@Then("{string} in response body in {string}")
	public void in_response_body_in(String keyValue, String expectedValue) {
	   
	    assertEquals(getJsonPath(response, keyValue), expectedValue);
	}
	
	@Then("verify place_Id created maps to {string} using {string}")
	public void verify_place_id_created_maps_to_using(String expectedName, String resource) throws IOException {
	   
		place_Id = getJsonPath(response, "place_id");
		res = given().spec(requestSpecification()).queryParam("place_id", place_Id);
		user_calls_with_http_request(resource, "Get");
		String actualName = getJsonPath(response, "name");
		
		assertEquals(actualName, expectedName);
	}
	
	@Given("DeletePlace Payload")
	public void delete_place_payload() throws IOException {
	   
		res=given().spec(requestSpecification())
				.body(data.deletePlace(place_Id));
		
		}

}
