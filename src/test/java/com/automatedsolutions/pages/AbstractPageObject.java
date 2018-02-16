package com.automatedsolutions.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import com.automatedsolutions.driver.WebDriverFactory;
import com.automatedsolutions.util.WaitConditions;

public abstract class AbstractPageObject {

	private WebDriverFactory webDriverFactory;

	private WaitConditions waitConditions;

	public AbstractPageObject(WebDriverFactory webDriverFactory) {
		this.webDriverFactory = webDriverFactory;
	}

	protected WebDriver getWebDriver() {
		return webDriverFactory.getDriver();
	}

	protected WebDriver getWebDriver(String testName) {
		return webDriverFactory.getDriver(testName);
	}

	protected WaitConditions getWaitCondition() {
		if (null == waitConditions) {
			waitConditions = new WaitConditions(getWebDriver());
		}
		return waitConditions;
	}

	protected void initializePage(Object page) {
		PageFactory.initElements(getWebDriver(), this);
	}

	protected void initializePage(Object page, String testName) {
		PageFactory.initElements(getWebDriver(testName), this);
	}

	public void closeWebDriver() {
		webDriverFactory.quit();
	}
}
