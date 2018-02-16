package com.automatedsolutions.runner;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(plugin = { "pretty", "html:target/cucumber" }, features = { "classpath:cucumber/features/" }, glue = {
		"com.automatedsolutions.stepdefinitions" })
public class DebugRunner {

}
