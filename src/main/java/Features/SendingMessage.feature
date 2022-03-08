Feature: Sending Message to Leads

  Scenario Outline: This scenario validates sending messages to Leads
    Given User enters URL
    When User navigates to Sellers tool
    When User enters "<MobileNo>" and click on Start Selling button
    And Wait till OTP is entered manually And click on Login Button
    Then User navigates to Lead Manager section
#    Then User Create New Lead if Lead is not available with "<LeadMobile>"
    Then User search Existing Lead "<Lead>"
    Then User Enter message to Lead
    And Clicks on Send button
    Then Message should be sent and visible in displayed space
    Examples:
      | MobileNo   | Lead          | LeadMobile |
      | 9137089916 | Chirag Pandey | 9999999998 |
#      | 9137089916 | Tom Peter     | 9999999998 |
