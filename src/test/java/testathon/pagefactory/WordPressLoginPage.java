package test.java.testathon.pagefactory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import pojo.UiDetails;

public class WordPressLoginPage {

	protected WebDriver driver;

	@FindBy(how = How.ID, using = "wp-submit")
	private WebElement btnSubmit;

	@FindBy(how = How.ID, using = "user_login")
	private WebElement tbUserLogin;

	@FindBy(how = How.ID, using = "user_pass")
	private WebElement tbUserPassword;
	@FindBy(how = How.XPATH, using = "//div[@class='expand-table-toggle']")
	private WebElement expand;

	@FindBy(how = How.XPATH, using = "//div[text()='Test Positivity Ratio']/..")
	private WebElement ratioheader;
	@FindBy(how = How.XPATH, using = "//div[@class='state-page']")
	private WebElement metadata;

	public WordPressLoginPage(WebDriver driver) {
		this.driver = driver;
		// TODO Auto-generated constructor stub
		driver.get("https://www.covid19india.org/");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		PageFactory.initElements(driver, this);
	}

	public WordPressHomePage Login(String userId, String password) {

		tbUserLogin.sendKeys(userId);

		tbUserPassword.sendKeys(password);

		btnSubmit.click();

		return new WordPressHomePage(driver);
	}
	public List<UiDetails>  getValidThreeStates(int count) throws InterruptedException {
		List<UiDetails> statesData=new LinkedList<UiDetails>();
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
			UiDetails data=new UiDetails();
			data.setRatio(ratio.get(i).getText());
			data.setStateName(states.get(i).getText());
			data.setConfirmed(confirmed.get(i).getText());
			data.setRecovered(activated.get(i).getText());
			data.setActivated(recovered.get(i).getText());
			statesData.add(data);
			states.get(i).click();
			metadata.click();
			Thread.sleep(3000);
			driver.navigate().back();
			Thread.sleep(3000);
			//ratioheader.click();
			ratio = driver
					.findElements(By.xpath("//div[@class='table fadeInUp']//div[@class='row']/div[11]/div"));
			states = driver
					.findElements(By.xpath("//div[@class='table fadeInUp']//div[@class='row']/div[1]/div"));
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
