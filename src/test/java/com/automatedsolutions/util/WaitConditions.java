package com.automatedsolutions.util;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WaitConditions {

	private FluentWait<WebDriver> wait;

	public WaitConditions(WebDriver driver) {
		wait = new WebDriverWait(driver, 30).pollingEvery(300, TimeUnit.MILLISECONDS)
				.ignoring(StaleElementReferenceException.class);
	}

	public boolean tilteContains(String title) {
		return wait.until(ExpectedConditions.titleContains(title));
	}

	public boolean pageContains(String text) {
		return wait.until(ExpectedConditions.textToBePresentInElementLocated(By.tagName("body"), text));
	}

	public boolean elementVisible(WebElement element) {
		return wait.until(ExpectedConditions.visibilityOf(element)) != null;
	}

	public boolean elementClickable(WebElement element) {
		return wait.until(ExpectedConditions.elementToBeClickable(element)) != null;
	}

	public boolean elementHasText(WebElement element, String text) {
		return wait.until(ExpectedConditions.textToBePresentInElement(element, text));
	}
}
