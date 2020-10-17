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
import test.java.testathon.selenium.SeleniumUtils;
import test.java.testathon.utils.Report;

public class CovidStateWisePage {

	protected WebDriver driver;
	protected SeleniumUtils seleniumUtils;
	protected ExtentTest testLogger;

	@FindBy(how = How.XPATH, using = "//*[contains(text(),'View all')]")
	private WebElement btnViewAll;

	@FindBy(how = How.XPATH, using = "//div[@class='StateDropdown']/h1[@class='state-name fadeInUp']")
	private WebElement lblStateName;

	@FindBy(how = How.XPATH, using = "//div[@class='districts fadeInUp is-grid']")
	private WebElement tblDistricts;

	public CovidStateWisePage(WebDriver driver) {
		this.driver = driver;
		seleniumUtils = new SeleniumUtils(this.driver);
		testLogger = Report.getInstance().getTest();
		// driver.get("https://www.covid19india.org/state/BR");

		// TODO Auto-generated constructor stub
		if (!driver.getTitle().contains("Coronavirus Outbreak in")) {
			throw new IllegalStateException(
					"This is not Home Page of logged in user," + " current page is: " + driver.getTitle());
		}
		PageFactory.initElements(driver, this);

		seleniumUtils.waitforVisibilityElement(lblStateName);

		String pageTitle = "Coronavirus Outbreak in " + lblStateName.getText() + " - covid19india.org";
		assertEquals(driver.getTitle().toString(), pageTitle,
				"State Page is not correctly displayed: " + lblStateName.getText());

		testLogger.info("Page launched: " + pageTitle);

	}

	public CovidStateWisePage getDistricts() {
		// seleniumUtils.implicitwait();

		seleniumUtils.waitforVisibilityElement(btnViewAll);

		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", btnViewAll);

		if (btnViewAll.isDisplayed())
			btnViewAll.click();

		testLogger.info("state: " + lblStateName.getText());

		String districtData = tblDistricts.getText().trim();
		String[] districtDataArray = districtData.split("\n");

		String expectedDistricts = GetCovidResponseByState();

		List<District> lstDistrict = new ArrayList<>();

		for (int i = 0; i < districtDataArray.length; i = i + 2) {

			String districtName = districtDataArray[i + 1];
			String activeCases = districtDataArray[i].replace(",", "");

			District district = new District(districtName, activeCases);

//			int activeExpected = GetCovidResponseByStateDist(expectedDistricts, lblStateName.getText(), districtName);
//			assertEquals(Integer.parseInt(activeCases), activeExpected, "verification of active cases");

			lstDistrict.add(district);

		}

		testLogger.info("Total Districts: " + lstDistrict.size());

		return this;
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

}

class District {
	private String districtName;

	private int activeCases;

	public District(String Name, String ActiveCases) {
		// TODO Auto-generated constructor stub
		districtName = Name;
		activeCases = ParseInt(ActiveCases);
	}

	private int ParseInt(String ActiveCases) {
		return Integer.parseInt(ActiveCases.replace(",", "").trim());
	}
}
