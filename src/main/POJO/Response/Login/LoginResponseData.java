package main.POJO.Response.Login;

public class LoginResponseData {
    public String mobileNumber;
    public String token;
    public int retryInSeconds;
    public int remainingInSeconds;

    public String getMobileNumber() {
        return mobileNumber;
    }

    public String getToken() {
        return token;
    }

    public int getRetryInSeconds() {
        return retryInSeconds;
    }

    public int getRemainingInSeconds() {
        return remainingInSeconds;
    }
}
