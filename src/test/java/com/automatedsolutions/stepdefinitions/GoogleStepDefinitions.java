package com.automatedsolutions.stepdefinitions;

import org.junit.Assert;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class GoogleStepDefinitions extends AbstractGoogleSteps {

	@Before("@google")
	public void before(Scenario scenario) {
		super.before(scenario);
	}

	@Given("^I have nagivated to googles home page$")
	public void i_have_nagivated_to_googles_home_page() throws Throwable {
		Assert.assertTrue(googlePage.isGoogleHome());
	}

	@When("^I enter my search text \"([^\"]*)\"$")
	public void i_enter_my_search_text(String search) throws Throwable {
		googlePage.enterSearch(search);
	}

	@When("^I click Google Search$")
	public void i_click_Google_Search() throws Throwable {
		googlePage.clickSearchButton();
	}

	@Then("^I see my results \"([^\"]*)\" listed$")
	public void i_see_my_results_listed(String result) throws Throwable {
		Assert.assertTrue(googlePage.isResultOnPage(result));
	}

	@After("@google")
	public void after() {
		googlePage.closeWebDriver();
	}
}
