package test.java.testathon.tests;

import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import test.java.testathon.framework.BaseUITest;
import test.java.testathon.pagefactory.CovidStateWisePage;
import test.java.testathon.utils.Report;

public class Covid19IndiaUIApp extends BaseUITest {

	@Test(enabled = true)
	public void testVerifyDistrict() {

		Report.getTest().log(Status.INFO, "Test started: testVerifyDistrict");

		CovidStateWisePage districtPage = new CovidStateWisePage(driverInstance.getDriver());

		districtPage.getDistricts();

	}

}
