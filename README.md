************************************************************
                     ROAD STATUS API
************************************************************

Tools and Technologies Used
-------------------------------------------------
Java        -  11  
Spring Boot - v2.7.17  
Karate BDD  - 1.4.1  
OpenApi     - 1.7.0  
**************************************************
This application focus is to build a rest api that integrates with the TFL unified API.

Implementation:
--------------------
1. Generated the API key.
2. Followed the Behavior-Driven Development (BDD) approach using Karate. 
3. Utilized `RestTemplate` to format the URL and send a GET request to the TfL Unified Road Service to obtain the response.
4. Deserialized the JSON response body, mapped it, and generated our custom JSON response.

Test Coverage details:
------------------------

Element       | Class, %  | Method, % | Line, %| 
-------------- | -------- | ---------- |-----------------
com.api.road	 |    100% (0/0)  |	100% (0/0)  | 100% (0/0)
com.api.road.status	| 100% (1/1)	|0% (0/1)|	50% (1/2)
com.api.road.status.configuration |	100% (1/1) |	100% (5/5)|	100% (20/20)
com.api.road.status.controller	| 100% (1/1)	|100% (5/5)|	100% (10/10)
com.api.road.status.deserializer|	100% (2/2)|	100% (5/5)	|100% (31/31)
com.api.road.status.response	| 100% (3/3)|	61% (25/42)	| 51% (35/68)
com.api.road.status.service	|100% |(2/2)	| 91% (11/12)|	78% (70/89)

To Run/test the Application
----------------------------
1.Using the command "mvn spring-boot:run -Dspring.config.name=your-config-file" runs the application with your custom configuration file, 
where you can set the values for the following properties:
roadapi.app.key=api-key_value  
roadapi.app.url=https://api.tfl.gov.uk/Road/{ids}?app_key={appKeyValue}

2.Using Postman, provide the URL in one of the following formats: "http://localhost:8080/road/A10" or "http://localhost:8080/road/A10?app_key={appkeyvalue}".
In this context, "A10" represents the road ID, allowing you to specify the particular road for testing and retrieving its status. 
Additionally, the "app_key" is an optional request parameter. If you haven't configured it in the application.properties file, you can provide the value directly in the endpoint.


To Run the test case of the application
---------------------------------------------
Implemented Junit test cases, Integration test cases and Karate BDD test cases.    
Execute the "mvn test" command. 
It will generate a Karate test case report.  
Karate uses the actual live server to execute the test case, so please ensure the application is running before executing the "mvn test" command.
Click to view [Karate BDD Test case summary report](
https://username.github.io/repository/path/to/your/page.html)



Suggestions
---------------------------------------------
Here are few suggestion regarding storing the API Key.
1. If we are using cloud base for example if it is AWS - we can AWS Secret Manger, AWS Parameter store.
2. We can also use Secure Properties with Jasypt.
3. If we are storing the Database, we can use hashing mechanism (bcrypt).

Swagger
----------------------------------
Implemented Open API (Swagger) for this API,
http://localhost:8080/swagger-ui/index.html

Assumptions
----------------------------------
When registering with the Unified API, only an app key is provided; there is no app ID.