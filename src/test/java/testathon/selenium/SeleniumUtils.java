package test.java.testathon.selenium;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SeleniumUtils {
	WebDriver driver;

	public SeleniumUtils(WebDriver driver) {
		this.driver = driver;
	}

	public void Hover(WebElement element) {
		Actions actions = new Actions(driver);
		actions.moveToElement(element);
	}

	public void selectByText(WebElement element, String text) {
		Select select = new Select(element);
		select.selectByVisibleText(text);
	}

	public void selectByIndex(WebElement element, int index) {
		Select select = new Select(element);
		select.selectByIndex(index);
	}

	public void selectByOption(WebElement element, String text) {
		Select select = new Select(element);
		select.selectByValue(text);
	}

	public void javasctiptClick(WebElement element) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", element);
	}

	public void implicitwait() {
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
	}

	public void waitforVisibilityElement(WebElement element) {
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.visibilityOf(element));
	}

	public void waitforInVisibilityElement(WebElement element) {
		WebDriverWait wait = new WebDriverWait(driver, 60);
		wait.until(ExpectedConditions.invisibilityOf(element));
	}

}
