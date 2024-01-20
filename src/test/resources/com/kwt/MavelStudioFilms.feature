Feature: Marvel Film Studios

  Blah blah blah...

  Scenario Outline: Fetch a list of all Marvel Studio films for a given year
    Given The number of MCU movies that have debuted
    When listing movies released in the year <year>
    Then there are a number of films <films> shown

    Examples:
      | year | films                        |
      | 2008 | Iron Man,The Incredible Hulk |
      | 2012 | Marvel's The Avengers        |