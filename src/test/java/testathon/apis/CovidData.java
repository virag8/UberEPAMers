package test.java.testathon.apis;

import java.util.List;
import java.util.Map;

import org.seleniumhq.jetty9.http.HttpStatus;
import org.testng.Assert;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class CovidData {

	public static void main(String[] args) {
		GetCovidResponseByState();
	}

	public static String GetCovidResponseByState() {

		// Set base uri
		RestAssured.baseURI = "https://api.covid19india.org";

		// Request specification
		RequestSpecification httpRequest = RestAssured.given();

		// Specifying the method and getting response
		Response response = httpRequest.get("/state_district_wise.json");

		Assert.assertEquals(response.getStatusCode(), HttpStatus.OK_200);

		List<String> jsonResponse = response.jsonPath().getList("Bihar");

		Map<Object, Object> jsonPath = response.getBody().jsonPath().getMap("\"Andaman and Nicobar Islands\"");
		jsonPath.get("districtData");

		System.out.println(jsonPath.toString());
		return response.getBody().asString();

	}

}
