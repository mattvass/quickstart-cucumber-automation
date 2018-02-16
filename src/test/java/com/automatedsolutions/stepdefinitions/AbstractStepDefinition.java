package com.automatedsolutions.stepdefinitions;

import org.springframework.test.context.ContextConfiguration;

import com.automatedsolutions.common.ApplicationConstants;
import com.automatedsolutions.config.spring.ApplicationConfiguration;
import cucumber.api.Scenario;

@ContextConfiguration(classes = ApplicationConfiguration.class)
public abstract class AbstractStepDefinition {
	protected Scenario scenario;

	/**
	 * Get a reference to the current cucumber scenario Supports writing text and
	 * xml to report within test steps
	 * 
	 * @param scenario
	 */
	public void before(Scenario scenario) {
		this.scenario = scenario;
	}

	protected void embedTextInReport(String text) {
		scenario.write(text);
	}

	protected void embedXmlInReport(String xml) {
		xml = "<textarea readonly>" + xml + "</textarea>";
		scenario.write(xml);
	}

	protected String getTestName() {
		int exampleRow = Integer.parseInt(scenario.getId().split(";;")[1].trim()) - 2;
		return String.format(ApplicationConstants.SCENARIO_NAME, scenario.getName(), exampleRow);
	}

}
