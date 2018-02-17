package com.automatedsolutions.config.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;

import com.automatedsolutions.browser.BrowserCapabilities;
import com.automatedsolutions.config.ApplicationProperties;
import com.automatedsolutions.driver.WebDriverFactory;
import com.automatedsolutions.pages.GooglePageObject;

/**
 * 
 * @author Matthew Vass Created February 11, 2018
 *
 */
@Configuration
@PropertySource(value = { "classpath:/application-default.properties" })
public class ApplicationConfiguration {

	/**
	 * Created the application property bean
	 * 
	 * @return ApplicationProperties
	 */
	@Bean
	public ApplicationProperties properties() {
		return new ApplicationProperties();
	}

	/**
	 * Creates user specific browser capabilities with properties
	 * 
	 * @return BrowserCapabilities
	 */
	@Bean
	public BrowserCapabilities browserCapabilities() {
		return new BrowserCapabilities(properties());
	}

	/**
	 * Creates a WebDriverFactory bean with browser capabilities per test run using
	 * cucumber-glue scope
	 * 
	 * @return
	 */
	@Bean
	@Scope("cucumber-glue")
	public WebDriverFactory webDriverFactory() {
		return new WebDriverFactory(browserCapabilities().getCapabilities());
	}

	/**
	 * Creates a GooglePageObject bean with webDriverFactory per test run using
	 * cucumber-glue scope
	 * 
	 * @return
	 */
	@Bean
	@Scope("cucumber-glue")
	public GooglePageObject googlePageObject() {
		return new GooglePageObject(webDriverFactory());
	}
}
