package test.java.testathon.tests;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.testng.Assert.assertEquals;

import java.util.LinkedHashMap;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.jayway.jsonpath.JsonPath;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import pojo.UiDetails;
import test.java.testathon.framework.BaseUITest;
import test.java.testathon.pagefactory.CovidHomePage;
import test.java.testathon.utils.Report;

public class Covid19IndiaUIApp extends BaseUITest {

	private static final int MaxStates = 1;

	@Test(enabled = true)
	public void testVerifyDistrict() throws InterruptedException {

		Report.getTest().log(Status.INFO, "Test started: testVerifyDistrict");

		Report.getTest().log(Status.INFO, "Base page Launch successful");
		CovidHomePage covidHomePage = new CovidHomePage(driverInstance.getDriver());

		List<UiDetails> data = covidHomePage.getValidThreeStates(MaxStates);
		for (int i = 0; i < data.size(); ++i) {
			Statewise APIStateData = GetCovidResponseByState(data.get(i).getStateName());
			assertEquals(data.get(i).getActivated().replace(",", ""), APIStateData.getActive().replace(",", ""),
					"verify activated cases");
			assertEquals(data.get(i).getConfirmed().replace(",", ""), APIStateData.getConfirmed().replace(",", ""),
					"verify confirmed cases");
			assertEquals(data.get(i).getRecovered().replace(",", ""), APIStateData.getRecovered().replace(",", ""),
					"verify recovered cases");

		}

	}

	public Statewise GetCovidResponseByState(String statename) {

		// Set base uri
		RestAssured.baseURI = "https://api.covid19india.org";

		// Request specification
		RequestSpecification httpRequest = RestAssured.given();

		// Specifying the method and getting response
		Response response = httpRequest.get("/data.json");

		Assert.assertEquals(response.getStatusCode(), 200);
		assertThat("Response code is 200", response.getStatusCode(), equalTo(200));
		Root covidResponse = response.getBody().as(Root.class);
		List<Statewise> statewise = covidResponse.getStatewise();

		Statewise statedetails = statewise.stream().filter(a -> a.getState().equals(statename)).findFirst().get();
		return statedetails;
	}

	public void GetCovidResponseByStateDist(String statename) {

		// Set base uri
		RestAssured.baseURI = "https://api.covid19india.org";

		// Request specification
		RequestSpecification httpRequest = RestAssured.given();

		// Specifying the method and getting response
		Response response = httpRequest.get("/state_district_wise.json");

		Assert.assertEquals(response.getStatusCode(), 200);
		assertThat("Response code is 200", response.getStatusCode(), equalTo(200));

		String responseBody = response.getBody().asString();

		LinkedHashMap HashMap = JsonPath.read(responseBody, "$." + statename + ".districtData");

	}

}

final class Cases_time_series {
	private String dailyconfirmed;

	private String dailydeceased;

	private String dailyrecovered;

	private String date;

	private String totalconfirmed;

	private String totaldeceased;

	private String totalrecovered;

	public void setDailyconfirmed(String dailyconfirmed) {
		this.dailyconfirmed = dailyconfirmed;
	}

	public String getDailyconfirmed() {
		return this.dailyconfirmed;
	}

	public void setDailydeceased(String dailydeceased) {
		this.dailydeceased = dailydeceased;
	}

	public String getDailydeceased() {
		return this.dailydeceased;
	}

	public void setDailyrecovered(String dailyrecovered) {
		this.dailyrecovered = dailyrecovered;
	}

	public String getDailyrecovered() {
		return this.dailyrecovered;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getDate() {
		return this.date;
	}

	public void setTotalconfirmed(String totalconfirmed) {
		this.totalconfirmed = totalconfirmed;
	}

	public String getTotalconfirmed() {
		return this.totalconfirmed;
	}

	public void setTotaldeceased(String totaldeceased) {
		this.totaldeceased = totaldeceased;
	}

	public String getTotaldeceased() {
		return this.totaldeceased;
	}

	public void setTotalrecovered(String totalrecovered) {
		this.totalrecovered = totalrecovered;
	}

	public String getTotalrecovered() {
		return this.totalrecovered;
	}
}

final class Statewise {
	private String active;

	private String confirmed;

	private String deaths;

	private String deltaconfirmed;

	private String deltadeaths;

	private String deltarecovered;

	private String lastupdatedtime;

	private String migratedother;

	private String recovered;

	private String state;

	private String statecode;

	private String statenotes;

	public void setActive(String active) {
		this.active = active;
	}

	public String getActive() {
		return this.active;
	}

	public void setConfirmed(String confirmed) {
		this.confirmed = confirmed;
	}

	public String getConfirmed() {
		return this.confirmed;
	}

	public void setDeaths(String deaths) {
		this.deaths = deaths;
	}

	public String getDeaths() {
		return this.deaths;
	}

	public void setDeltaconfirmed(String deltaconfirmed) {
		this.deltaconfirmed = deltaconfirmed;
	}

	public String getDeltaconfirmed() {
		return this.deltaconfirmed;
	}

	public void setDeltadeaths(String deltadeaths) {
		this.deltadeaths = deltadeaths;
	}

	public String getDeltadeaths() {
		return this.deltadeaths;
	}

	public void setDeltarecovered(String deltarecovered) {
		this.deltarecovered = deltarecovered;
	}

	public String getDeltarecovered() {
		return this.deltarecovered;
	}

	public void setLastupdatedtime(String lastupdatedtime) {
		this.lastupdatedtime = lastupdatedtime;
	}

	public String getLastupdatedtime() {
		return this.lastupdatedtime;
	}

	public void setMigratedother(String migratedother) {
		this.migratedother = migratedother;
	}

	public String getMigratedother() {
		return this.migratedother;
	}

	public void setRecovered(String recovered) {
		this.recovered = recovered;
	}

	public String getRecovered() {
		return this.recovered;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getState() {
		return this.state;
	}

	public void setStatecode(String statecode) {
		this.statecode = statecode;
	}

	public String getStatecode() {
		return this.statecode;
	}

	public void setStatenotes(String statenotes) {
		this.statenotes = statenotes;
	}

	public String getStatenotes() {
		return this.statenotes;
	}
}

final class Tested {
	private String dailyrtpcrsamplescollectedicmrapplication;

	private String individualstestedperconfirmedcase;

	private String positivecasesfromsamplesreported;

	private String samplereportedtoday;

	private String source;

	private String source1;

	private String source3;

	private String testedasof;

	private String testpositivityrate;

	private String testsconductedbyprivatelabs;

	private String testsperconfirmedcase;

	private String testspermillion;

	private String totalindividualstested;

	private String totalpositivecases;

	private String totalrtpcrsamplescollectedicmrapplication;

	private String totalsamplestested;

	private String updatetimestamp;

	public void setDailyrtpcrsamplescollectedicmrapplication(String dailyrtpcrsamplescollectedicmrapplication) {
		this.dailyrtpcrsamplescollectedicmrapplication = dailyrtpcrsamplescollectedicmrapplication;
	}

	public String getDailyrtpcrsamplescollectedicmrapplication() {
		return this.dailyrtpcrsamplescollectedicmrapplication;
	}

	public void setIndividualstestedperconfirmedcase(String individualstestedperconfirmedcase) {
		this.individualstestedperconfirmedcase = individualstestedperconfirmedcase;
	}

	public String getIndividualstestedperconfirmedcase() {
		return this.individualstestedperconfirmedcase;
	}

	public void setPositivecasesfromsamplesreported(String positivecasesfromsamplesreported) {
		this.positivecasesfromsamplesreported = positivecasesfromsamplesreported;
	}

	public String getPositivecasesfromsamplesreported() {
		return this.positivecasesfromsamplesreported;
	}

	public void setSamplereportedtoday(String samplereportedtoday) {
		this.samplereportedtoday = samplereportedtoday;
	}

	public String getSamplereportedtoday() {
		return this.samplereportedtoday;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getSource() {
		return this.source;
	}

	public void setSource1(String source1) {
		this.source1 = source1;
	}

	public String getSource1() {
		return this.source1;
	}

	public void setSource3(String source3) {
		this.source3 = source3;
	}

	public String getSource3() {
		return this.source3;
	}

	public void setTestedasof(String testedasof) {
		this.testedasof = testedasof;
	}

	public String getTestedasof() {
		return this.testedasof;
	}

	public void setTestpositivityrate(String testpositivityrate) {
		this.testpositivityrate = testpositivityrate;
	}

	public String getTestpositivityrate() {
		return this.testpositivityrate;
	}

	public void setTestsconductedbyprivatelabs(String testsconductedbyprivatelabs) {
		this.testsconductedbyprivatelabs = testsconductedbyprivatelabs;
	}

	public String getTestsconductedbyprivatelabs() {
		return this.testsconductedbyprivatelabs;
	}

	public void setTestsperconfirmedcase(String testsperconfirmedcase) {
		this.testsperconfirmedcase = testsperconfirmedcase;
	}

	public String getTestsperconfirmedcase() {
		return this.testsperconfirmedcase;
	}

	public void setTestspermillion(String testspermillion) {
		this.testspermillion = testspermillion;
	}

	public String getTestspermillion() {
		return this.testspermillion;
	}

	public void setTotalindividualstested(String totalindividualstested) {
		this.totalindividualstested = totalindividualstested;
	}

	public String getTotalindividualstested() {
		return this.totalindividualstested;
	}

	public void setTotalpositivecases(String totalpositivecases) {
		this.totalpositivecases = totalpositivecases;
	}

	public String getTotalpositivecases() {
		return this.totalpositivecases;
	}

	public void setTotalrtpcrsamplescollectedicmrapplication(String totalrtpcrsamplescollectedicmrapplication) {
		this.totalrtpcrsamplescollectedicmrapplication = totalrtpcrsamplescollectedicmrapplication;
	}

	public String getTotalrtpcrsamplescollectedicmrapplication() {
		return this.totalrtpcrsamplescollectedicmrapplication;
	}

	public void setTotalsamplestested(String totalsamplestested) {
		this.totalsamplestested = totalsamplestested;
	}

	public String getTotalsamplestested() {
		return this.totalsamplestested;
	}

	public void setUpdatetimestamp(String updatetimestamp) {
		this.updatetimestamp = updatetimestamp;
	}

	public String getUpdatetimestamp() {
		return this.updatetimestamp;
	}
}

final class Root {
	private List<Cases_time_series> cases_time_series;

	private List<Statewise> statewise;

	private List<Tested> tested;

	public void setCases_time_series(List<Cases_time_series> cases_time_series) {
		this.cases_time_series = cases_time_series;
	}

	public List<Cases_time_series> getCases_time_series() {
		return this.cases_time_series;
	}

	public void setStatewise(List<Statewise> statewise) {
		this.statewise = statewise;
	}

	public List<Statewise> getStatewise() {
		return this.statewise;
	}

	public void setTested(List<Tested> tested) {
		this.tested = tested;
	}

	public List<Tested> getTested() {
		return this.tested;
	}
}
