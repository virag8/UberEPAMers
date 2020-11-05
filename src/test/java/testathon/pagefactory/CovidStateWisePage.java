package test.java.testathon.pagefactory;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.testng.Assert.assertEquals;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import com.aventstack.extentreports.ExtentTest;
import com.jayway.jsonpath.JsonPath;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import pojo.DistrictDetails;
import test.java.testathon.selenium.SeleniumUtils;

public class CovidStateWisePage {

	private static final String CORONAVIRUS_OUTBREAK_IN = "Coronavirus Outbreak in";
	protected WebDriver driver;
	protected SeleniumUtils seleniumUtils;
	ExtentTest testLogger;

	@FindBy(how = How.XPATH, using = "//*[contains(text(),'View all')]")
	private WebElement btnViewAll;

	@FindBy(how = How.XPATH, using = "//div[@class='StateDropdown']/h1[@class='state-name fadeInUp']")
	private WebElement lblStateName;

	@FindBy(how = How.XPATH, using = "//div[@class='districts fadeInUp is-grid']")
	private WebElement tblDistricts;

	@FindBy(how = How.XPATH, using = "//div[@class='navbar-middle']/a[@href='/']")
	private WebElement lnkHome;

	public CovidStateWisePage(WebDriver driver, ExtentTest testLogger) {
		this.driver = driver;
		this.seleniumUtils = new SeleniumUtils(this.driver);
		this.testLogger = testLogger;
		// driver.get("https://www.covid19india.org/state/BR");

		// TODO Auto-generated constructor stub
		if (!driver.getTitle().contains(CORONAVIRUS_OUTBREAK_IN)) {
			throw new IllegalStateException(
					"This is not " + CORONAVIRUS_OUTBREAK_IN + " Page," + " current page is: " + driver.getTitle());
		}
		PageFactory.initElements(driver, this);

		this.seleniumUtils.waitforVisibilityElement(lblStateName);

		String pageTitle = "Coronavirus Outbreak in " + lblStateName.getText() + " - covid19india.org";
		assertEquals(driver.getTitle().toString(), pageTitle,
				"State Page is not correctly displayed: " + lblStateName.getText());

		testLogger.info("Page launched: " + pageTitle);

	}

	public String GetCovidResponseByState() {

		// Set base uri
		RestAssured.baseURI = "https://api.covid19india.org";

		// Request specification
		RequestSpecification httpRequest = RestAssured.given();

		// Specifying the method and getting response
		Response response = httpRequest.get("/state_district_wise.json");

		Assert.assertEquals(response.getStatusCode(), 200);
		assertThat("Response code is 200", response.getStatusCode(), equalTo(200));

		String responseBody = response.getBody().asString();

		return responseBody;
	}

	public int GetCovidResponseByStateDist(String responseBody, String statename, String districtname) {

		LinkedHashMap HashMap = JsonPath.read(responseBody, "$." + statename + ".districtData." + districtname);
		return (int) HashMap.get("active");
	}

	public void selectViewAll() {
		this.seleniumUtils.waitforVisibilityElement(btnViewAll);

		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", btnViewAll);
		btnViewAll.click();

		testLogger.info("state: " + lblStateName.getText());
	}

	public CovidHomePage goToHomePage() {

		testLogger.info("Navigating back to home page: " + lnkHome.getText());

		lnkHome.click();
		return new CovidHomePage(this.driver, this.testLogger);

	}

	public List<DistrictDetails> getDistrictData() {
		String districtData = tblDistricts.getText().trim();
		String[] districtDataArray = districtData.split("\n");
		testLogger.info("Total Districts: " + districtDataArray.length);

		List<DistrictDetails> lstDistrict = new ArrayList<>();

		for (int i = 0; i < districtDataArray.length; i = i + 2) {

			String districtName = districtDataArray[i + 1];
			String activeCases = districtDataArray[i];

			DistrictDetails district = new DistrictDetails();
			district.setName(districtName);
			district.setActive(parseActive(activeCases));
			lstDistrict.add(district);

		}

		return lstDistrict;
	}

	private int parseActive(String activeCases) {
		return Integer.parseInt(activeCases.replace(",", "").trim());
	}
}
