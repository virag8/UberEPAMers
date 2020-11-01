package test.java.testathon.steps;

import java.util.List;

import org.openqa.selenium.WebDriver;

import com.aventstack.extentreports.ExtentTest;

import pojo.StateDetails;
import test.java.testathon.framework.BaseUITest;
import test.java.testathon.pagefactory.CovidHomePage;
import test.java.testathon.pagefactory.CovidStateWisePage;
import test.java.testathon.utils.Report;

public class CovidUIDataFetch extends BaseUITest {
	private static final int MaxStates = 3;
	private WebDriver driver;

	public CovidUIDataFetch(WebDriver driver) {
		// TODO Auto-generated constructor stub
		this.driver = driver;
	}

	public List<StateDetails> getUIDataSortedByActive() {
		ExtentTest report = Report.getInstance().getTest();
		ExtentTest testStepReport = report.createNode("getUIDataSortedByActive");
		CovidHomePage covidHomePage = new CovidHomePage(this.driver, testStepReport);
		covidHomePage.sortByActive();
		testStepReport.info("Home Page sorted by Active cases");
		List<StateDetails> states = covidHomePage.getStatesData(MaxStates);
		testStepReport.info("Top " + MaxStates + " states fetched");

		for (int i = 0; i < states.size(); i++) {
			covidHomePage.reportLogger = testStepReport.createNode(states.get(i).getStateName());
			CovidStateWisePage covidStateWisePage = covidHomePage.selectState(states.get(i).getStateName());
			covidStateWisePage.selectViewAll();
			states.get(i).setDistrictData(covidStateWisePage.getDistrictData());
			covidHomePage = covidStateWisePage.goToHomePage();
		}

		return states;
	}
}
