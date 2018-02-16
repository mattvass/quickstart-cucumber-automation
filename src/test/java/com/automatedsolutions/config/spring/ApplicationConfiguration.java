package com.automatedsolutions.config.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;

import com.automatedsolutions.browser.BrowserCapabilities;
import com.automatedsolutions.config.ApplicationProperties;
import com.automatedsolutions.driver.WebDriverFactory;
import com.automatedsolutions.pages.GooglePageObject;

@Configuration
@PropertySource(value = { "classpath:/application-default.properties" })
public class ApplicationConfiguration {

	@Bean
	public ApplicationProperties properties() {
		return new ApplicationProperties();
	}

	@Bean
	public BrowserCapabilities browserCapabilities() {
		return new BrowserCapabilities(properties());
	}

	@Bean
	@Scope("cucumber-glue")
	public WebDriverFactory webDriverFactory() {
		return new WebDriverFactory(browserCapabilities().getCapabilities());
	}

	@Bean
	@Scope("cucumber-glue")
	public GooglePageObject googlePageObject() {
		return new GooglePageObject(webDriverFactory());
	}
}
