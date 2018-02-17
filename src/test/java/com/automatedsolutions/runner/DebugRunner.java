package com.automatedsolutions.runner;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

/**
 * 
 * @author Matthew Vass Created: February 11, 2018
 *
 *         A local cucumber runner, for use with debugging or running via
 *         editors.
 */
@RunWith(Cucumber.class)
@CucumberOptions(plugin = { "pretty", "html:target/cucumber" }, features = { "classpath:cucumber/features/" }, glue = {
		"com.automatedsolutions.stepdefinitions" })
public class DebugRunner {

}
