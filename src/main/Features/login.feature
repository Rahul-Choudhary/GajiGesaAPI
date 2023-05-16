Feature: login specific features

  Background: User load environment Specific parameters
    Given User identifies specified environment and loads env specific baseUrl

#  Scenario Outline: Valid Login Verification
#    Given User sends in OTP request via "<requestMode>" to phone Number "<phoneNumber>" and gets token from response
#    When User hits internal api to get OTP sent for phone number "<phoneNumber>"
#    Then User hits Token generation api with token and OTP received for "<phoneNumber>" and fetches accessToken of response
#    And User verifies userName from Token generation api as "<userName>"
#    Examples:
#      | requestMode | phoneNumber  | userName            |
#      | SMS         | 629773933739 | Noel Leonard Ayalin |
#      | WHATSAPP    | 629773933739 | Noel Leonard Ayalin |
#
#  Scenario Outline: Validating Invalid length phoneNumber case, OTP sending api
#    Given User sends in OTP request via "<requestMode>" to phone Number "<phoneNumber>"
#    Then User gets response with status code as "<responseStatusCode>" and Response having error message as "<errorMessage>" with response having error code as "<errorCodeFromResponseBody>"
#    Examples:
#      | requestMode | phoneNumber       | responseStatusCode | errorMessage         | errorCodeFromResponseBody |
#      | SMS         | 629773            | 404                | Akun belum terdaftar | GG-404                    |
#      | WHATSAPP    | 629773            | 404                | Akun belum terdaftar | GG-404                    |
#      | SMS         | 62977393373909876 | 404                | Akun belum terdaftar | GG-404                    |
#      | WHATSAPP    | 62977393373909876 | 404                | Akun belum terdaftar | GG-404                    |
#
#  Scenario Outline: Validating Invalid request parameters, OTP sending api
#    Given User sends in OTP request with invalid field values via "<requestMode>" to phone Number "<phoneNumber>"
#    Then User gets validation response with status code as "<responseStatusCode>" and Response having error field as "<errorField>" with response having validation field as "<validationField>" with message "<validationMessage>"
#    Examples:
#      | requestMode | phoneNumber  | responseStatusCode | errorField  | validationField | validationMessage                  |
#      | SMS         | 629773933@#$ | 400                | BAD_REQUEST | mobileNumber    | Format nomor handphone tidak valid |
#      | WHATSAPP    | 698@29773933 | 400                | BAD_REQUEST | mobileNumber    | Format nomor handphone tidak valid |
#      | SMS         |              | 400                | BAD_REQUEST | mobileNumber    | nomor handphone tidak boleh kosong |
#      | WHATSAPP    |              | 400                | BAD_REQUEST | mobileNumber    | nomor handphone tidak boleh kosong |
#      | WHATAPP     | 629773933739 | 400                | BAD_REQUEST | messagingType   | Tipe messaging tidak valid         |
#      | SSS         | !@#          | 400                | BAD_REQUEST | messagingType   | Tipe messaging tidak valid         |
#      | SSS         | !@#          | 400                | BAD_REQUEST | mobileNumber    | Format nomor handphone tidak valid |
#      |             | 629773933739 | 400                | BAD_REQUEST | messagingType   | tipe messaging tidak boleh kosong  |
#      |             |              | 400                | BAD_REQUEST | messagingType   | tipe messaging tidak boleh kosong  |
#      |             |              | 400                | BAD_REQUEST | mobileNumber    | nomor handphone tidak boleh kosong |

    Scenario Outline: Validating multiple requests in one go, OTP sending api
#      Given User sends in OTP request via "<requestMode>" to phone Number "<phoneNumber>" and gets token from response
      When User sends in "<count>" OTP request via "<requestMode>" to phone Number "<phoneNumber>"
#      Then User gets response with status code as "<responseStatusCode>" and Response having error message as "<errorMessage>" with response having error code as "<errorCodeFromResponseBody>"
      Examples:
      |count|requestMode|phoneNumber|responseStatusCode|errorMessage|errorCodeFromResponseBody|
      |1    |SMS        |62813393721358|429            |Request terlalu banyak|GG-429         |