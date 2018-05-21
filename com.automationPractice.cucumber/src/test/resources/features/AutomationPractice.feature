Feature: Test the Automation Practice website

 
 Background: user is Logged in to the site
    Given Open firefox browser and start the application
    When I enter the Username and Password
    Then User shoud be able to Login successfully

 Scenario: Select the T-shirt and Order the product and verify the confirmation
  	Given Click T-shirts button and Select the size
  	When  Proceed to checkout and navigate to Order summary page
  	And click I confirm My Order button
  	Then Order confirmation should be done
  	
  	Scenario: Update the First name in Personal information
  	Given Navigate to My Account and Click on Personal Information
  	When  Modify the First Name
  	And click Save button
  	Then Personal Information should be change