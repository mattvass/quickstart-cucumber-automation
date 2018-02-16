Feature: Title of your feature
  I want to use this template for my feature file

  Background: 
    Given I have nagivated to googles home page

  @google
  Scenario Outline: I search google
    When I enter my search text "<search>"
    And I click Google Search
    Then I see my results "<result>" listed

    Examples: 
      | search   | result             |
      | selenium | www.seleniumhq.org |
      | yahoo    | www.yahoo.com      |
      | fox      | www.foxnews.com    |
