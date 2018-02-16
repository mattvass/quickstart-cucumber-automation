package com.automatedsolutions.pages;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

import com.automatedsolutions.driver.WebDriverFactory;

public class GooglePageObject extends AbstractPageObject {

	private final String url = "http://www.google.com";

	private final String title = "Google";

	@FindBy(name = "q")
	WebElement inputSearch;

	@FindBy(name = "btnK")
	WebElement btnSearch;

	@FindBy(id = "search")
	WebElement contSearch;

	public GooglePageObject(WebDriverFactory webDriverFactory) {
		super(webDriverFactory);
	}

	public void init() {
		super.initializePage(this);
	}

	public void init(String testName) {
		super.initializePage(this, testName);
	}

	public boolean isGoogleHome() {
		super.getWebDriver().get(url);
		return super.getWaitCondition().tilteContains(title);
	}

	public void enterSearch(String search) {
		this.inputSearch.sendKeys(search);
		this.inputSearch.sendKeys(Keys.TAB);
	}

	public void clickSearchButton() {
		new Actions(super.getWebDriver()).moveToElement(this.btnSearch).perform();
		this.btnSearch.click();
	}

	public boolean isResultOnPage(String result) {
		return super.getWaitCondition().elementHasText(contSearch, result);
	}
}
