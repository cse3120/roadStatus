Feature: Get Road Status

  Background:
    * url baseUrl

  Scenario: Fetch single valid road information

    Given path 'A2'
    And header Content-Type = 'application/json'
    When method GET
    Then status 200
    And match $ == { "response": "The status of the A2 is Good and No Exceptional Delays." }

  Scenario: Fetch a list of valid road information

    Given path 'A2,A3'
    And header Content-Type = 'application/json'
    When method GET
    Then status 200
    And match $ == [{"response": "The status of the A2 is Good and No Exceptional Delays." },{"response": "The status of the A3 is Good and No Exceptional Delays."}]

  Scenario: Fetch all list of valid road information

    Given path ''
    And header Content-Type = 'application/json'
    When method GET
    Then status 200


  Scenario: Fetch a invalid road information

    Given path 'A2,A132'
    And header Content-Type = 'application/json'
    When method GET
    Then status 200
    And match $ == { "response": "A132 is not a valid road." }

