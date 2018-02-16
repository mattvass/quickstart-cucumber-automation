# A simple quick start to cucumber automation

This project is my take on cucumber automation, using the cucumber-jvm and cucumber-jvm-paralell plugins with spring injection. I've seen a few out there already but this is my specific take based on the needs I have had throughout my career.

### Preconditions
1. Java 1.8 installed
2. Maven installed

### Getting Started
1. git clone https://github.com/mattvass/quickstart-cucumber-automation.git
2. cd quickstart-cucumber-automation
3. mvn clean install -DskipTests 
4. import as a maven project into your editor

### Running tests via maven in paralell
One of the plus sides to the cucumber-jvm-paralell is the ability to run cucumber features or scenarios in paralell seamlessly without any additional work. By running the below maven command, it will generate the runners and run them for you.

```mvn clean verify```

In addition to running tests via maven, there is a debug runner located in the com.automatedsolutions.runner package. You can run this as a junit test.  Other ways of running the features, include installing one of the many cucumber plugins for your editor.

### Breaking down the approach
Anyone familar with the page object model will be familar with the concept of a base abstract page, that has a constructor accepting a WebDriver. Since this project uses spring injection, the normal approach to doing things wouldn't excactly work. My concept is simple, instead of passing the WebDriver to the constructor I pass a WebDriverFactory, which has a method to get the driver. 

##### My Abstract Page Object
```
public abstract class AbstractPageObject {

	private WebDriverFactory webDriverFactory;

	private WaitConditions waitConditions;

	public AbstractPageObject(WebDriverFactory webDriverFactory) {
		this.webDriverFactory = webDriverFactory;
	}

	protected WebDriver getWebDriver() {
		return webDriverFactory.getDriver();
	}

	protected WebDriver getWebDriver(String testName) {
		return webDriverFactory.getDriver(testName);
	}

	protected WaitConditions getWaitCondition() {
		if (null == waitConditions) {
			waitConditions = new WaitConditions(getWebDriver());
		}
		return waitConditions;
	}

	protected void initializePage(Object page) {
		PageFactory.initElements(getWebDriver(), page);
	}

	protected void initializePage(Object page, String testName) {
		PageFactory.initElements(getWebDriver(testName), page);
	}

	public void closeWebDriver() {
		webDriverFactory.quit();
	}
}
```

As you can see in the above code, I have an overloaded method for initializing the page factory and getting the WebDriver. I've added a bit of logic that allows you to pass the current running test name to the creation of the WebDriver.  Anyone that's ever used sauce labs or similar tooling, knows how this is important for identifying test runs. 

##### My Page Object
```
public class GooglePageObject extends AbstractPageObject {

	private final String url = "http://www.google.com";

	private final String title = "Google";

	@FindBy(name = "q")
	WebElement inputSearch;

	@FindBy(name = "btnK")
	WebElement btnSearch;

	@FindBy(id = "search")
	WebElement contSearch;

	public GooglePageObject(WebDriverFactory webDriverFactory) {
		super(webDriverFactory);
	}

	public void init() {
		super.initializePage(this);
	}

	public void init(String testName) {
		super.initializePage(this, testName);
	}

	public boolean isGoogleHome() {
		super.getWebDriver().get(url);
		return super.getWaitCondition().tilteContains(title);
	}

	public void enterSearch(String search) {
		this.inputSearch.sendKeys(search);
		this.inputSearch.sendKeys(Keys.TAB);
	}

	public void clickSearchButton() {
		new Actions(super.getWebDriver()).moveToElement(this.btnSearch).perform();
		this.btnSearch.click();
	}

	public boolean isResultOnPage(String result) {
		return super.getWaitCondition().elementHasText(contSearch, result);
	}
}
```

As you can see above, the major difference in my approach here is that the initialization of the page factory does not happen in the constructor. This happens in the abstract step definition which allows me to create the bean in my configuration and later be autowired in that class.

##### My Abstract Step Definitions 
```
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

public abstract class AbstractGoogleSteps extends AbstractStepDefinition {

	/*
	 * This is just an extra abstraction layer so that if you're testing different
	 * features in the same framework you can separate these steps but still keep a
	 * common step definition
	 */
	@Autowired
	protected GooglePageObject googlePage;

	public void before(Scenario scenario) {
		super.before(scenario);
		googlePage.init(super.getTestName());
	}
}
```

As you can see in the above code snippets, I autowire the google page object and call the initialization in my before method. In my example I've initialized it with the test name since in many cases for my needs this was well suited. Also I added a layer of abstraction, the AbstractStepDefinition is for all common shared test methods and the AbstractGoogleSteps will only relate to tests involving google.

##### Setup and Teardown
```
  @Before("@google")
	public void before(Scenario scenario) {
		super.before(scenario);
	}
  
  @After("@google")
	public void after() {
		googlePage.closeWebDriver();
	}
```
Simply add the @Before annotation to your step definition and it will call the abstact step definitions functions in order to initialize all of you page objects. The after method will call the driver quit, since that method is part of my abstract base i could call it from any of my page objects.

##### Beans
```
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
```
In the above you can see I've created the beans, and added the cucumber-glue scope to the beans I want to be created per test run. 
