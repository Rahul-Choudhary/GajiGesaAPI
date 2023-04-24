package main.POJO.Response.OTP;

import java.util.ArrayList;

public class OTPResponseData {
    public String accessToken;
    public String refreshToken;
    public ArrayList<String> userRoles;
    public String userName;
    public boolean isGajitimEmployee;
    public ArrayList<EmployeeAccount> employeeAccounts;

    public String getAccessToken() {
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public ArrayList<String> getUserRoles() {
        return userRoles;
    }

    public String getUserName() {
        return userName;
    }

    public boolean isGajitimEmployee() {
        return isGajitimEmployee;
    }

    public ArrayList<EmployeeAccount> getEmployeeAccounts() {
        return employeeAccounts;
    }
}
