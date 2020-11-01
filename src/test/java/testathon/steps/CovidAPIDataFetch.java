package test.java.testathon.steps;

import java.util.List;

import com.aventstack.extentreports.ExtentTest;

import pojo.StateDetails;
import test.java.testathon.framework.BaseUITest;
import test.java.testathon.pagefactory.CovidHomePage;
import test.java.testathon.pagefactory.CovidStateWisePage;
import test.java.testathon.utils.Report;

public class CovidAPIDataFetch extends BaseUITest {
	private static final int MaxStates = 3;

	public List<StateDetails> getAPIData() {
		ExtentTest testStepReport = Report.getInstance().getTest().createNode("getUIDataSortedByActive");
		CovidHomePage covidHomePage = new CovidHomePage(driverInstance.getDriver(), testStepReport);
		covidHomePage.sortByActive();
		testStepReport.info("Home Page sorted by Active cases");
		List<StateDetails> states = covidHomePage.getStatesData(MaxStates);
		testStepReport.info("Top " + MaxStates + " states fetched");

		for (int i = 0; i < states.size(); i++) {
			CovidStateWisePage covidStateWisePage = covidHomePage.selectState(states.get(i).getStateName());
			covidStateWisePage.selectViewAll();
			states.get(0).setDistrictData(covidStateWisePage.getDistrictData());
		}

		return states;
	}
}
