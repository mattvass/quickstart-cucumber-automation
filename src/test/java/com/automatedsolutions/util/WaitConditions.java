package com.automatedsolutions.util;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * 
 * @author Matthew Vass Created: February 11, 2018
 *
 */
public class WaitConditions {

	private FluentWait<WebDriver> wait;

	/**
	 * Construct the Fluent Wait from the WebDriver
	 * 
	 * @param driver
	 */
	public WaitConditions(WebDriver driver) {
		wait = new WebDriverWait(driver, 30).pollingEvery(300, TimeUnit.MILLISECONDS)
				.ignoring(StaleElementReferenceException.class);
	}

	/**
	 * Does the title contain text
	 * 
	 * @param title
	 * @return boolean
	 */
	public boolean tilteContains(String title) {
		return wait.until(ExpectedConditions.titleContains(title));
	}

	/**
	 * Does the page contain text
	 * 
	 * @param text
	 * @return boolean
	 */
	public boolean pageContains(String text) {
		return wait.until(ExpectedConditions.textToBePresentInElementLocated(By.tagName("body"), text));
	}

	/**
	 * Is the element visible
	 * 
	 * @param element
	 * @return boolean
	 */
	public boolean elementVisible(WebElement element) {
		return wait.until(ExpectedConditions.visibilityOf(element)) != null;
	}

	/**
	 * Is the element clickable
	 * 
	 * @param element
	 * @return boolean
	 */
	public boolean elementClickable(WebElement element) {
		return wait.until(ExpectedConditions.elementToBeClickable(element)) != null;
	}

	/**
	 * Does the element have text
	 * 
	 * @param element
	 * @param text
	 * @return
	 */
	public boolean elementHasText(WebElement element, String text) {
		return wait.until(ExpectedConditions.textToBePresentInElement(element, text));
	}
}
