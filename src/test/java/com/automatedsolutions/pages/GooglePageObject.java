package com.automatedsolutions.pages;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

import com.automatedsolutions.driver.WebDriverFactory;

/**
 * 
 * @author Matthew Vass Created: February 11, 2018
 *
 */
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

	/**
	 * Initialize the page object factory
	 */
	public void init() {
		super.initializePage(this);
	}

	/**
	 * Initialize the page object factory and set the test name
	 * @param testName
	 */
	public void init(String testName) {
		super.initializePage(this, testName);
	}

	/**
	 * Got to and verify we are on googles home page
	 * @return boolean
	 */
	public boolean isGoogleHome() {
		super.getWebDriver().get(url);
		return super.getWaitCondition().tilteContains(title);
	}

	/**
	 * Enter search text, then tab out in order to click search button
	 * @param search
	 */
	public void enterSearch(String search) {
		this.inputSearch.sendKeys(search);
		this.inputSearch.sendKeys(Keys.TAB);
	}

	/**
	 * Focus on and the click the search button
	 */
	public void clickSearchButton() {
		new Actions(super.getWebDriver()).moveToElement(this.btnSearch).perform();
		this.btnSearch.click();
	}

	/**
	 * Using cusomt waits, check if results are on the page
	 * @param result
	 * @return boolean
	 */
	public boolean isResultOnPage(String result) {
		return super.getWaitCondition().elementHasText(contSearch, result);
	}
}
