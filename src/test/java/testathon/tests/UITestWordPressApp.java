package test.java.testathon.tests;

import org.json.simple.JSONObject;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import test.java.testathon.framework.BaseUITest;
import test.java.testathon.pagefactory.WordPressHomePage;
import test.java.testathon.pagefactory.WordPressLoginPage;
import test.java.testathon.utils.Report;
import java.util.List;
import pojo.UiDetails;

public class UITestWordPressApp extends BaseUITest {

	@Test(enabled = false, dataProvider = "jsonDataProvider")
	public void testWordPressLogin(JSONObject testData) {
		try {
			Report.getTest().log(Status.INFO, "TestData: " + testData.toJSONString());
			String user = testData.get("user").toString();
			String password = testData.get("password").toString();

			WordPressLoginPage loginPage = new WordPressLoginPage(driverInstance.getDriver());

			WordPressHomePage homePage = loginPage.Login(user, password);

			homePage.VerifyUser();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw e;
		}
	}

	@Test(enabled = false, dataProvider = "jsonDataProvider")
	public void testWordPressInvalidLogin(JSONObject testData) {
		try {
			Report.getTest().log(Status.INFO, "TestData: " + testData.toJSONString());
			String user = testData.get("user").toString();
			String password = testData.get("password").toString();

			WordPressLoginPage loginPage = new WordPressLoginPage(driverInstance.getDriver());

			WordPressHomePage homePage = loginPage.Login(user, password);

			homePage.VerifyUser();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw e;
		}
	}
	@Test(enabled = true)
	public void testMaximumRatio() throws Exception {
		try {
			Report.getTest().log(Status.INFO, "Base page Launch successful");
			WordPressLoginPage loginPage = new WordPressLoginPage(driverInstance.getDriver());
			List<UiDetails> data=loginPage.getValidThreeStates(3);
			for(int i=0;i<3;++i)
			{
				System.out.println(data.get(i).getStateName());
				System.out.println(data.get(i).getRatio());
				System.out.println(data.get(i).getConfirmed());
				System.out.println(data.get(i).getRecovered());
				System.out.println(data.get(i).getActivated());
			}
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			throw e;
		}
	}

}
