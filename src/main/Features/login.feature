Feature: login specific features

  Background: User load environment Specific parameters
    Given User identifies specified environment and loads env specific baseUrl

 Scenario Outline: Valid login via SMS
   Given User sends in OTP request via "<requestMode>" to phone Number "<phoneNumber>" and gets token from response
   When User hits internal api to get OTP sent for phone number "<phoneNumber>"
   Then User hits Token generation api with token and OTP received for "<phoneNumber>" and fetches accessToken of response
   And User verifies userName from Token generation api as "<userName>"
   Examples:
   |requestMode|phoneNumber|userName|
   |SMS|629773933739|Noel Leonard Ayalin|
   |WHATSAPP|629773933739|Noel Leonard Ayalin|

