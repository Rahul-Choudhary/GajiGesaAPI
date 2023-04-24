package StepDefinition;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import main.Helper.BasicAPICalls;
import main.POJO.Request.Login;
import io.restassured.response.Response;
import main.POJO.Request.OTP;
import main.POJO.Response.Login.LoginResponse;
import main.POJO.Response.OTP.OTPResponse;
import org.testng.Assert;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Login_StepDefinition extends BasicAPICalls {

    private String tokenFromLoginResponse = "";
    private String otpReceivedByUser = "";
    private String userNameFromOTPResponse = "";

    @Given("User sends in OTP request via {string} to phone Number {string} and gets token from response")
    public void user_sends_in_otp_request_via_to_phone_number_and_gets_token_from_response(String requestType, String phoneNumber) throws FileNotFoundException {
        Login login = (Login)creatingObjectFromJsonFile(JSON_REQUEST_PATH+"login.json", Login.class);
        login.setMobileNumber(phoneNumber);
        login.setMessagingType(requestType);

        Response response = callingPOSTapi(REQUEST_SPECIFICATION, RESPONSE_SPECIFICATION_201, "/auth/login", login);

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
        OTP otpRequestBody = (OTP)creatingObjectFromJsonFile(JSON_REQUEST_PATH+"otp.json", OTP.class);
        otpRequestBody.setOtp(Integer.parseInt(otpReceivedByUser));
        otpRequestBody.setToken(tokenFromLoginResponse);

        Response response = callingPOSTapi(REQUEST_SPECIFICATION, RESPONSE_SPECIFICATION_201, "/auth/login/otp", otpRequestBody);

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

}
