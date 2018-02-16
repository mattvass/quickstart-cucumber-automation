package com.automatedsolutions.browser;

public enum BrowserTypes {
	CHROME("chrome"), FIREFOX("firefox"), SAFARI("safari"), INTERNET_EXPLORER("internet explorer");

	private String browser;

	BrowserTypes(String browser) {
		this.browser = browser;
	}

	@Override
	public String toString() {
		return this.browser;
	}
}
