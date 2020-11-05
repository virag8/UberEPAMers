package test.java.testathon.apis;

import java.util.List;
import java.util.Map;

import org.seleniumhq.jetty9.http.HttpStatus;
import org.testng.Assert;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import pojo.RootData;

public class CovidData {

	private static final String DATA_JSON = "/data.json";
	private static final String STATE_DISTRICT_WISE_JSON = "/state_district_wise.json";
	private static final String HTTPS_API_COVID19INDIA_ORG = "https://api.covid19india.org";

	public static void main(String[] args) {
		getAllCovidData();
	}

	public static String getCovidStateWiseData(String state) {

		// Set base uri
		RestAssured.baseURI = HTTPS_API_COVID19INDIA_ORG;

		// Request specification
		RequestSpecification httpRequest = RestAssured.given();

		// Specifying the method and getting response
		Response response = httpRequest.get(STATE_DISTRICT_WISE_JSON);

		Assert.assertEquals(response.getStatusCode(), HttpStatus.OK_200);

		List<String> jsonResponse = response.jsonPath().getList(state);

		Map<Object, Object> jsonPath = response.getBody().jsonPath().getMap("\"Andaman and Nicobar Islands\"");
		jsonPath.get("districtData");

		System.out.println(jsonPath.toString());
		return response.getBody().asString();

	}

	public static RootData getAllCovidData() {

		// Set base uri
		RestAssured.baseURI = HTTPS_API_COVID19INDIA_ORG;

		// Specifying the method and getting response
		Response response = RestAssured.given().get(DATA_JSON);

		Assert.assertEquals(response.getStatusCode(), HttpStatus.OK_200);
		return response.getBody().as(RootData.class);

	}

}
