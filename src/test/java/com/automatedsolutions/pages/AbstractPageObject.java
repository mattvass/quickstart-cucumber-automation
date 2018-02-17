package com.automatedsolutions.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import com.automatedsolutions.driver.WebDriverFactory;
import com.automatedsolutions.util.WaitConditions;

/**
 * 
 * @author Matthew Vass Created: February 11, 2018
 *
 */
public abstract class AbstractPageObject {

	private WebDriverFactory webDriverFactory;

	private WaitConditions waitConditions;

	public AbstractPageObject(WebDriverFactory webDriverFactory) {
		this.webDriverFactory = webDriverFactory;
	}

	/**
	 * Gets the WebDriver from the bean
	 * 
	 * @return WebDriver
	 */
	protected WebDriver getWebDriver() {
		return webDriverFactory.getDriver();
	}

	/**
	 * Gets the WebDriver from the bean, passing the test name
	 * 
	 * @param testName
	 * @return
	 */
	protected WebDriver getWebDriver(String testName) {
		return webDriverFactory.getDriver(testName);
	}

	/**
	 * Get custom wait conditions
	 * 
	 * @return WaitConditions
	 */
	protected WaitConditions getWaitCondition() {
		if (null == waitConditions) {
			waitConditions = new WaitConditions(getWebDriver());
		}
		return waitConditions;
	}

	/**
	 * Initialize the page object factory
	 * 
	 * @param page
	 */
	protected void initializePage(Object page) {
		PageFactory.initElements(getWebDriver(), page);
	}

	/**
	 * Initialize the page object factory and set test name
	 * 
	 * @param page
	 * @param testName
	 */
	protected void initializePage(Object page, String testName) {
		PageFactory.initElements(getWebDriver(testName), page);
	}

	/**
	 * Close the WebDriver
	 */
	public void closeWebDriver() {
		webDriverFactory.quit();
	}
}
