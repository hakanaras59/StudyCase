Feature: ApiTest

  Background:
    * def conf = call read('../karate-config.js')
    * url conf.mockUrl

  Scenario: Basarili Http Status Kontrolu

    Given path '/objects'
    When method GET
    Then print response
    Then status 200


  Scenario: Basarisiz Http Status Kontrolu Hatali Method
    Given path '/objects'
    When method POST
    Then print response
    Then status 200
