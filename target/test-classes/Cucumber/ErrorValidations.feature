@tag
Feature: Error Validation

@ErrorValidation
Scenario Outline: Neagtive scenario to validate login
	Given I landed on Ecommerce Page
	When Logged in with username <name> and password <password>
	Then "Incorrect email or password." message is displayed
	
		
Examples:
	| name				| password	   |
	| shivaay@gmail.com | Shivaay@007  |
    