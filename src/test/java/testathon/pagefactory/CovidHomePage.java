package test.java.testathon.pagefactory;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.aventstack.extentreports.ExtentTest;

import pojo.StateDetails;
import test.java.testathon.selenium.SeleniumUtils;

public class CovidHomePage {

	protected WebDriver driver;
	protected SeleniumUtils seleniumUtils;
	private ExtentTest reportLogger;

	@FindBy(how = How.XPATH, using = "//div[@class='expand-table-toggle']")
	private WebElement expand;

	@FindBy(how = How.XPATH, using = "//div[text()='Test Positivity Ratio']/..")
	private WebElement headerTestPositivity;

	@FindBy(how = How.XPATH, using = "//div[text()='Active']/..")
	private WebElement headerActive;

	@FindBy(how = How.XPATH, using = "//div[@class='state-page']")
	private WebElement metadata;

	@FindBy(how = How.XPATH, using = "//div[@class='table fadeInUp']")
	private WebElement tblFadeInUp;

	@FindBy(how = How.XPATH, using = "//div[@class='table fadeInUp']/div")
	private List<WebElement> tblFadeInUpData;

	public CovidHomePage(WebDriver driver, ExtentTest reportLogger) {
		this.driver = driver;
		this.setReportLogger(reportLogger);
		seleniumUtils = new SeleniumUtils(driver);
		// TODO Auto-generated constructor stub
		driver.get("https://www.covid19india.org/");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		PageFactory.initElements(driver, this);
	}

	public List<StateDetails> getStatesData(int count) {

		List<StateDetails> statesData = new LinkedList<>();

		for (int i = 1; i <= count; i++) {
			List<WebElement> rowData = tblFadeInUpData.get(i).findElements(By.xpath("div"));

			// String[] rowData = tblFadeInUpData.get(i).getText().trim().split("\n");

			StateDetails data = new StateDetails();
			data.setStateName(rowData.get(0).getText().trim());
			data.setConfirmed(getCellTotal(rowData.get(1)));
			data.setActivated(getCellTotal(rowData.get(2)));
			data.setRecovered(getCellTotal(rowData.get(3)));
			data.setRatio(getCellTotal(rowData.get(10)));

			statesData.add(data);
		}

		return statesData;
	}

	private String getCellTotal(WebElement row) {
		String cellValue = "";

		WebElement cell = row.findElement(By.xpath("div[@class='total']"));
		cellValue = cell.getAttribute("title");
		return cellValue.trim();
	}

	public CovidStateWisePage selectState(String stateName) {
		WebElement state = tblFadeInUp
				.findElement(By.xpath("//div[@class='state-name fadeInUp'][text()='" + stateName + "']"));
		state.click();
		metadata.click();
		return new CovidStateWisePage(this.driver, this.getReportLogger());
	}

	public void sortByPositivityRate() {
		expand.click();
		headerTestPositivity.click();
		seleniumUtils.waitforVisibilityElement(tblFadeInUp);
	}

	public void sortByActive() {
		expand.click();
		headerActive.click();
		seleniumUtils.waitforVisibilityElement(tblFadeInUp);
	}

	public ExtentTest getReportLogger() {
		return reportLogger;
	}

	public void setReportLogger(ExtentTest reportLogger) {
		this.reportLogger = reportLogger;
	}

}
