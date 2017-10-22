Feature:  Few products of the same type in shopping chart
  User should be able to put multiple products into the shopping chart
  User should see proper final price for multiple products

  Scenario:
    Given I am on Amazon page
    And I go to category "Digital Cameras best sellers"
    When I select position 1
    And I add it to shopping chart in quantity of 2
    And I go to checkout
    Then I should see proper product name on item list
    And I sould see proper final price