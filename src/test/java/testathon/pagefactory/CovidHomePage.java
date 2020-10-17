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

import pojo.UiDetails;

public class CovidHomePage {

	protected WebDriver driver;

	@FindBy(how = How.XPATH, using = "//div[@class='expand-table-toggle']")
	private WebElement expand;

	@FindBy(how = How.XPATH, using = "//div[text()='Test Positivity Ratio']/..")
	private WebElement ratioheader;
	@FindBy(how = How.XPATH, using = "//div[@class='state-page']")
	private WebElement metadata;

	public CovidHomePage(WebDriver driver) {
		this.driver = driver;
		// TODO Auto-generated constructor stub
		driver.get("https://www.covid19india.org/");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		PageFactory.initElements(driver, this);
	}

	public List<UiDetails> getValidThreeStates(int count) throws InterruptedException {
		List<UiDetails> statesData = new LinkedList<UiDetails>();
		expand.click();
		ratioheader.click();
		Thread.sleep(3000);
		List<WebElement> ratio = driver
				.findElements(By.xpath("//div[@class='table fadeInUp']//div[@class='row']/div[11]/div"));
		List<WebElement> states = driver
				.findElements(By.xpath("//div[@class='table fadeInUp']//div[@class='row']/div[1]/div"));
		List<WebElement> confirmed = driver
				.findElements(By.xpath("//div[@class='table fadeInUp']//div[@class='row']/div[2]/div[2]"));
		List<WebElement> activated = driver
				.findElements(By.xpath("//div[@class='table fadeInUp']//div[@class='row']/div[3]/div[1]"));
		List<WebElement> recovered = driver
				.findElements(By.xpath("//div[@class='table fadeInUp']//div[@class='row']/div[4]/div[2]"));

		for (int i = 0; i < count; ++i) {
			UiDetails data = new UiDetails();
			data.setRatio(ratio.get(i).getText());
			data.setStateName(states.get(i).getText());
			data.setConfirmed(confirmed.get(i).getText());
			data.setRecovered(recovered.get(i).getText());
			data.setActivated(activated.get(i).getText());
			statesData.add(data);
			states.get(i).click();
			metadata.click();
			//
			CovidStateWisePage districtPage = new CovidStateWisePage(driver);

			districtPage.getDistricts();

			Thread.sleep(3000);
			driver.navigate().back();
			Thread.sleep(3000);
			// ratioheader.click();
			ratio = driver.findElements(By.xpath("//div[@class='table fadeInUp']//div[@class='row']/div[11]/div"));
			states = driver.findElements(By.xpath("//div[@class='table fadeInUp']//div[@class='row']/div[1]/div"));
			confirmed = driver
					.findElements(By.xpath("//div[@class='table fadeInUp']//div[@class='row']/div[2]/div[2]"));

			activated = driver
					.findElements(By.xpath("//div[@class='table fadeInUp']//div[@class='row']/div[3]/div[1]"));

			recovered = driver
					.findElements(By.xpath("//div[@class='table fadeInUp']//div[@class='row']/div[4]/div[2]"));

		}
		return statesData;
	}
}
