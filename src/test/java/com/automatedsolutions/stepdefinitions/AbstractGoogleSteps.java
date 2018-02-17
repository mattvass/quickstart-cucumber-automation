package com.automatedsolutions.stepdefinitions;

import org.springframework.beans.factory.annotation.Autowired;

import com.automatedsolutions.pages.GooglePageObject;

import cucumber.api.Scenario;

/**
 * 
 * @author Matthew Vass Created: February 11, 2018
 *
 */
public abstract class AbstractGoogleSteps extends AbstractStepDefinition {

	/*
	 * This is just an extra abstraction layer so that if you're testing different
	 * features in the same framework you can separate these steps but still keep a
	 * common step definition
	 */
	@Autowired
	protected GooglePageObject googlePage;

	/**
	 * Call abstract before method and then initialize page factory
	 */
	public void before(Scenario scenario) {
		super.before(scenario);
		googlePage.init(super.getTestName());
	}
}
