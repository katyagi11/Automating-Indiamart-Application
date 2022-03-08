Feature: Sharing Quotations to Leads

  Scenario Outline: This scenario validates Sharing Quotations to Leads
    Given User enters URL
    When User navigates to Sellers tool
    When User enters "<MobileNo>" and click on Start Selling button
    And Wait till OTP is entered manually And click on Login Button
    Then User navigates to Lead Manager section
    Then User search Existing Lead "<Lead>"
    And Click on Quotation
    And Uncheck Select All Checkbox
    Then Enter Details of "<NoOfQuotations>" Product "<ProductName>", "<Price>", "<Unit>"
    Then Click Next
    Then Click Next Again
    Then Click Generate Quotation
    Then Click Send Quotation
    And User should be able to send Quotation successfully
    Examples:
      | MobileNo   | Lead          | LeadMobile | NoOfQuotations | ProductName                                             | Price    | Unit |
      | 9137089916 | Chirag Pandey | 9999999998 | 2              | Panasonic Kx Test 824 Epabx System;gents cotton t shirt | 1000;500 | 2;1  |
#
  Scenario Outline: This scenario validates Leaving Sharing Quotations Popup
    Given User enters URL
    When User navigates to Sellers tool
    When User enters "<MobileNo>" and click on Start Selling button
    And Wait till OTP is entered manually And click on Login Button
    Then User navigates to Lead Manager section
    #    Then User Create New Lead if Lead is not available with "<LeadMobile>"
    Then User search Existing Lead "<Lead>"
    And Click on Quotation
    And Click on Close icon
    Then User is able to see Popup asking to Leave or Stay
    Then User click on Leave option
    Then Popup should be closed and no Quotation should be sent
    Examples:
      | MobileNo   | Lead          | LeadMobile |
      | 9137089916 | Chirag Pandey | 9999999998 |