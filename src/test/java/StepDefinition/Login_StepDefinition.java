package StepDefinition;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import main.Helper.BasicAPICalls;
import main.POJO.Request.Login.Login;
import io.restassured.response.Response;
import main.POJO.Request.OTP.OTP;
import main.POJO.Response.Login.InvalidFieldsResponse;
import main.Helper.ThreadHelper;
import main.POJO.Response.Login.InvalidResponse;
import main.POJO.Response.Login.LoginResponse;
import main.POJO.Response.Login.Validation;
import main.POJO.Response.OTP.OTPResponse;
import org.testng.Assert;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class Login_StepDefinition extends BasicAPICalls{

    private String tokenFromLoginResponse = "";
    private String otpReceivedByUser = "";
    private String userNameFromOTPResponse = "";

    @Given("User sends in OTP request via {string} to phone Number {string} and gets token from response")
    public void user_sends_in_otp_request_via_to_phone_number_and_gets_token_from_response(String requestType, String phoneNumber) throws FileNotFoundException {
        Login login = (Login)creatingObjectFromJsonFile(JSON_REQUEST_PATH+"Login/login.json", Login.class);
        login.setMobileNumber(phoneNumber);
        login.setMessagingType(requestType);

        Response response = callingPOSTapi(
                REQUEST_SPECIFICATION,
                RESPONSE_SPECIFICATION_201,
                RESPONSE_POJO_PATH+"Login/ResponseSchema.json",
                "/auth/login",
                login);

        LoginResponse loginResponse = (LoginResponse) creatingObjectFromResponse(response, LoginResponse.class);
        Assert.assertEquals(loginResponse.getData().getMobileNumber(), phoneNumber);
        tokenFromLoginResponse = loginResponse.getData().getToken();
    }
    @When("User hits internal api to get OTP sent for phone number {string}")
    public void user_hits_internal_api_to_get_otp_sent_for_phone_number(String string) {
        otpReceivedByUser = "1111";
    }
    @Then("User hits Token generation api with token and OTP received for {string} and fetches accessToken of response")
    public void user_hits_token_generation_api_with_token_and_otp_received_for_and_fetches_access_token_of_response(String string) throws FileNotFoundException {
        OTP otpRequestBody = (OTP)creatingObjectFromJsonFile(JSON_REQUEST_PATH+"/OTP/otp.json", OTP.class);
        otpRequestBody.setOtp(Integer.parseInt(otpReceivedByUser));
        otpRequestBody.setToken(tokenFromLoginResponse);

        Response response = callingPOSTapi(REQUEST_SPECIFICATION, RESPONSE_SPECIFICATION_201,
                RESPONSE_POJO_PATH+"OTP/ResponseSchema.json",
                "/auth/login/otp", otpRequestBody);

        //String userNameFetched = fetchingPathFromResponse(response, "data.userName").toString();

        OTPResponse otpResponse = (OTPResponse) creatingObjectFromResponse(response, OTPResponse.class);
        ACCESS_TOKEN_RECEIVED_FROM_RESPONSE = otpResponse.getData().getAccessToken();
        userNameFromOTPResponse = otpResponse.getData().getUserName();
    }
    @Then("User verifies userName from Token generation api as {string}")
    public void user_verifies_user_name_from_token_generation_api_as(String userNameExpected) {
        Assert.assertEquals(userNameExpected, userNameFromOTPResponse);

        System.out.println("user name received from response is as "+ userNameFromOTPResponse+ " and token from response is as "+ ACCESS_TOKEN_RECEIVED_FROM_RESPONSE);
    }

    @Given("User identifies specified environment and loads env specific baseUrl")
    public void user_identifies_specified_environment_and_loads_env_specific_base_url() throws IOException {
        ENV_SELECTED = loadingEnvironment();
        loadingBaseUrl(ENV_SELECTED);
        initializingRequestAndResponseSpecification();
    }
    Response invalidFieldResponse;
    @Given("User sends in OTP request via {string} to phone Number {string}")
    public void user_sends_in_otp_request_via_to_phone_number(String requestType, String phoneNumber) throws FileNotFoundException {
        Login login = (Login)creatingObjectFromJsonFile(JSON_REQUEST_PATH+"Login/login.json", Login.class);
        login.setMobileNumber(phoneNumber);
        login.setMessagingType(requestType);

        invalidFieldResponse = callingPOSTapi(
                REQUEST_SPECIFICATION,
                RESPONSE_SPECIFICATION_404,
                RESPONSE_POJO_PATH+"Login/InvalidNumberResponseSchema.json",
                "/auth/login",
                login);
    }
    @Then("User gets response with status code as {string} and Response having error message as {string} with response having error code as {string}")
    public void user_gets_response_with_status_code_as_and_response_having_error_message_as_with_response_having_error_code_as(String responseStatusCode, String errorMessage, String errorCodeFromResponseBody) {
        Assert.assertEquals(invalidFieldResponse.getStatusCode(), Integer.parseInt(responseStatusCode));
        InvalidResponse invalidResponse = (InvalidResponse) creatingObjectFromResponse(invalidFieldResponse, InvalidResponse.class);
        Assert.assertEquals(invalidResponse.getError(), errorCodeFromResponseBody);
        Assert.assertEquals(invalidResponse.getMessage(), errorMessage);
    }

    @Given("User sends in OTP request with invalid field values via {string} to phone Number {string}")
    public void user_sends_in_otp_request_with_invalid_field_values_via_to_phone_number(String requestType, String phoneNumber) throws FileNotFoundException {
        Login login = (Login)creatingObjectFromJsonFile(JSON_REQUEST_PATH+"Login/login.json", Login.class);
        login.setMobileNumber(phoneNumber);
        login.setMessagingType(requestType);

        invalidFieldResponse = callingPOSTapi(
                REQUEST_SPECIFICATION,
                RESPONSE_SPECIFICATION_400,
                RESPONSE_POJO_PATH+"Login/InvalidRequestParametersSchema.json",
                "/auth/login",
                login);
    }

    @Then("User gets validation response with status code as {string} and Response having error field as {string} with response having validation field as {string} with message {string}")
    public void user_gets_validation_response_with_status_code_as_and_response_having_error_field_as_with_response_having_validation_field_as_with_message(String responseStatusCode, String errorField, String validationField, String validationMessage) {
        InvalidFieldsResponse invalidFieldsResponse = (InvalidFieldsResponse) creatingObjectFromResponse(invalidFieldResponse, InvalidFieldsResponse.class);
        Assert.assertEquals(invalidFieldsResponse.getError(), errorField);
        Assert.assertEquals(invalidFieldResponse.getStatusCode(), Integer.parseInt(responseStatusCode));

        HashMap<String, String> mapOfValidationFields = new HashMap<>();
        ArrayList<Validation> listOfValidation = invalidFieldsResponse.getValidations();

        listOfValidation.stream().forEach(x-> mapOfValidationFields.put(x.field.toString(), x.message.toString()));


        Assert.assertEquals(invalidFieldsResponse.getError(), errorField);
        if(mapOfValidationFields.keySet().contains(validationField))
        {
            Assert.assertEquals(mapOfValidationFields.get(validationField), validationMessage);
        }
        else
        {
            Assert.assertTrue(false, "validation Field do not contains expected field");
        }

    }
    public static ThreadLocal<LinkedHashMap<String, Object>> dataMap =
            ThreadLocal.withInitial(()-> new LinkedHashMap<String, Object>());
    @When("User sends in {string} OTP request via {string} to phone Number {string}")
    public void user_sends_in_otp_request_via_to_phone_number(String Count, String requestMode, String phoneNumber) throws FileNotFoundException {

        List<Thread> listOfThreads = new LinkedList<>();

        Login login = (Login)creatingObjectFromJsonFile(JSON_REQUEST_PATH+"Login/login.json", Login.class);
        login.setMobileNumber(phoneNumber);
        login.setMessagingType(requestMode);


        for(int i=0;i<Integer.parseInt(Count);i++)

            listOfThreads.add(new Thread(new ThreadHelper("POST", REQUEST_SPECIFICATION
            , RESPONSE_SPECIFICATION_201, "/auth/login", login)));

        for(Thread t: listOfThreads) {
            t.setUncaughtExceptionHandler(

                    new Thread.UncaughtExceptionHandler() {
                        @Override
                        public void uncaughtException(Thread th, Throwable ex) {
                            System.out.println("Uncaught exception: " + ex);
                            System.out.println("Number of thread is " + th.threadId());
                        }
                    }
            );
            t.start();
        }
        }
}
