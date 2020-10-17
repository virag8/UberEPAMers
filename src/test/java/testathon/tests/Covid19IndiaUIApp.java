package test.java.testathon.tests;

import java.util.List;

import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

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
			System.out.println(data.get(i).getStateName());
			System.out.println(data.get(i).getRatio());
			System.out.println(data.get(i).getConfirmed());
			System.out.println(data.get(i).getRecovered());
			System.out.println(data.get(i).getActivated());
		}

	}

}
