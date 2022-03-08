Feature: Sharing Catalogs to Leads

  Scenario Outline: This scenario validates sharing catalogs to Leads
    Given User enters URL
    When User navigates to Sellers tool
    When User enters "<MobileNo>" and click on Start Selling button
    And Wait till OTP is entered manually And click on Login Button
    Then User navigates to Lead Manager section
    Then User search Existing Lead "<Lead>"
    And Click on Catalog option
    Then Select "<NoOfCatalogs>" Catalog and Enter "<Price>"
    And Click on Send button
    Then Verify Catalog is sent
    Examples:
      | MobileNo   | Lead          | LeadMobile | NoOfCatalogs | Price   |
      | 9137089916 | Chirag Pandey | 9999999998 | 1            | 100     |
      | 9137089916 | Chirag Pandey | 9999999998 | 2            | 100;300 |
