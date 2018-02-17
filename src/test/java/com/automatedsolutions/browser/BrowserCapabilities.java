package com.automatedsolutions.browser;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.automatedsolutions.common.ApplicationConstants;
import com.automatedsolutions.config.ApplicationProperties;

/**
 * 
 * @author Matthew Vass Created: February 11, 2018
 * 
 */
public class BrowserCapabilities {

	private ApplicationProperties properties;

	public BrowserCapabilities(ApplicationProperties properties) {
		this.properties = properties;
	}

	/**
	 * set all your capabilities here
	 * 
	 * @return DesiredCapabilities
	 */
	public DesiredCapabilities getCapabilities() {
		DesiredCapabilities caps = null;

		switch (BrowserTypes.valueOf(properties.getBrowser().toUpperCase())) {
		case CHROME:
			if (StringUtils.isBlank(System.getProperty(ApplicationConstants.CHROME_DRIVER_PROPERTY))) {
				System.setProperty(ApplicationConstants.CHROME_DRIVER_PROPERTY, properties.getChromeDriverLocation());
			}
			caps = DesiredCapabilities.chrome();
			break;
		case FIREFOX:
			if (StringUtils.isBlank(System.getProperty(ApplicationConstants.GECKO_DRIVER_PROPERTY))) {
				System.setProperty(ApplicationConstants.GECKO_DRIVER_PROPERTY, properties.getGeckoDriverLocation());
			}
			caps = DesiredCapabilities.firefox();
			break;
		case SAFARI:
			caps = DesiredCapabilities.safari();
			break;
		case INTERNET_EXPLORER:
			caps = DesiredCapabilities.internetExplorer();
			break;
		default:
			caps = DesiredCapabilities.firefox();
			break;
		}
		return caps;

	}
}
