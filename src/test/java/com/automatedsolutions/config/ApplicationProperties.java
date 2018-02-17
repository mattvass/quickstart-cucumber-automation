package com.automatedsolutions.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import com.automatedsolutions.common.ApplicationConstants;

/**
 * 
 * @author Matthew Vass Created: February 11, 2018
 *
 */
public class ApplicationProperties {

	@Autowired
	private Environment env;

	public String getBrowser() {
		return env.getProperty(ApplicationConstants.WEBDRIVER_BROWSER);
	}

	public String getChromeDriverLocation() {
		return env.getProperty(ApplicationConstants.CHROME_DRIVER_PROPERTY);
	}

	public String getGeckoDriverLocation() {
		return env.getProperty(ApplicationConstants.GECKO_DRIVER_PROPERTY);
	}

	public String getFeatureLocation() {
		return env.getProperty("cucumber.feature.location");
	}
}
