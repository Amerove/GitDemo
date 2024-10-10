Feature: Validating Place API's

@AddPlace @All
Scenario Outline: Verify if Place is being Succesfully added using AddPlaceAPI
	Given Add Place Payload with "<name>" "<language>" "<address>"
	When user calls "addPlaceAPI" with "Post" http request
	Then the API call got success with status code 200
	And "status" in response body in "OK"
	And verify place_Id created maps to "<name>" using "getPlaceAPI"
	
	Examples: 
	|name  |language |address 	 |
	|Rami  |English  |Gessner rd |

@DeletePlace @All
Scenario: Verify if Delete Place functionality is working
	Given DeletePlace Payload
	When user calls "deletePlaceAPI" with "Post" http request
	Then the API call got success with status code 200
	And "status" in response body in "OK"
	