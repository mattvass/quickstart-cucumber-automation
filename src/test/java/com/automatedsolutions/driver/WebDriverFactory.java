package com.automatedsolutions.driver;

import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;

import com.automatedsolutions.common.ApplicationConstants;
import com.automatedsolutions.common.ErrorMessages;

public class WebDriverFactory {

	private static final Logger log = Logger.getLogger(WebDriverFactory.class.getName());

	private WebDriver driver;

	private DesiredCapabilities caps;

	private String testName;

	public WebDriverFactory(DesiredCapabilities caps) {
		this.caps = caps;
	}

	public WebDriver getDriver() {
		if (null == driver) {

			if (StringUtils.isNotBlank(this.testName)) {
				caps.setCapability(ApplicationConstants.NAME, this.testName);
			}

			if (caps.is("remote")) {
				try {
					driver = new RemoteWebDriver(new URL((String) caps.getCapability("url")), caps);
				} catch (MalformedURLException e) {
					String msg = String.format(ErrorMessages.MALFORMED_URL_EXCEPTION, caps.getCapability("url"),
							e.getMessage());
					log.error(msg);
				}
			} else {
				switch (caps.getBrowserName().toLowerCase()) {
				case "internet explorer":
					driver = new InternetExplorerDriver(new InternetExplorerOptions(caps));
					break;
				case "firefox":
					driver = new FirefoxDriver(new FirefoxOptions().merge(caps));
					break;
				case "chrome":
					driver = new ChromeDriver(new ChromeOptions().merge(caps));
					break;
				case "safari":
					driver = new SafariDriver(new SafariOptions().merge(caps));
					break;
				default:
					driver = new FirefoxDriver();
				}
			}
		}

		return driver;
	}

	public WebDriver getDriver(String testName) {
		this.testName = testName;
		return getDriver();
	}

	public void quit() {
		if (null != driver) {
			driver.close();

			try {
				driver.quit();
			} catch (SessionNotCreatedException ex) {
				log.debug(String.format(ErrorMessages.SESSION_NOT_CREATED_EXCEPTION, ex.getMessage()));
			}

		}
	}
}
