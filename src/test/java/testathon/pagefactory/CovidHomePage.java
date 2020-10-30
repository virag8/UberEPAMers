package test.java.testathon.pagefactory;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import pojo.UiDetails;
import test.java.testathon.selenium.SeleniumUtils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class CovidHomePage {

    protected WebDriver driver;
    protected SeleniumUtils seleniumUtils;

    @FindBy(how = How.XPATH, using = "//div[@class='expand-table-toggle']")
    private WebElement expand;

    @FindBy(how = How.XPATH, using = "//div[text()='Test Positivity Ratio']/..")
    private WebElement headerTestPositivity;

    @FindBy(how = How.XPATH, using = "//div[@class='state-page']")
    private WebElement metadata;

    @FindBy(how = How.XPATH, using = "//div[@class='table fadeInUp']")
    private WebElement tblFadeInUp;

    public CovidHomePage(WebDriver driver) {
        this.driver = driver;
        seleniumUtils = new SeleniumUtils(driver);
        // TODO Auto-generated constructor stub
        driver.get("https://www.covid19india.org/");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
        PageFactory.initElements(driver, this);
    }

    public void sortByPositivityRate() {
        expand.click();
        headerTestPositivity.click();
        seleniumUtils.waitforVisibilityElement(tblFadeInUp);
    }

    public List<UiDetails> getValidThreeStates(int count) throws InterruptedException {
        //String txt = tblFadeInUp.getText();
        //System.out.println(tblFadeInUp.getText());
        List<WebElement> elements2 = tblFadeInUp.findElements(By.xpath("div"));
        //int[][] arr = new int[elements2.size()][elements2.iterator().Iterator().];
        LinkedHashMap<String, List<String>> maps = new LinkedHashMap<>();
        Boolean firstTime = true;
        for (WebElement webElement : elements2) {
            System.out.println("***" + webElement.getText());
            String[] multiText = webElement.getText().split("\n");
            for (String text : multiText) {
                if (firstTime) {
                	maps.put(text, new ArrayList<>());
				} else {
					
                }
            }
        }
        List<WebElement> elements1 = tblFadeInUp.findElements(By.tagName("div"));
        int s1 = elements1.size();
        int s2 = elements2.size();
        List<UiDetails> statesData = new LinkedList<>();
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
