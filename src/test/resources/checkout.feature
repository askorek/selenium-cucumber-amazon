Feature:  Few products of the same type in shopping chart
  User should be able to put multiple products into the shopping chart
  User should see proper final price for multiple products

  Scenario:
    Given I am on Amazon page
    And I go to best selling cameras
    When I select position 5
    And I add it to shopping chart in quantity of 8
    And I go to checkout
    Then I should see proper product name on item list
    And I sould see proper final price