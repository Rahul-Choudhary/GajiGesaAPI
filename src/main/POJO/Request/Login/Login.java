package main.POJO.Request.Login;

public class Login {
    public String loginType;
    public String mobileNumber;
    public String messagingType;

    public void setLoginType(String loginType) {
        this.loginType = loginType;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public void setMessagingType(String messagingType) {
        this.messagingType = messagingType;
    }

    @Override
    public String toString() {
        return "{" +
                "loginType='" + loginType + '\'' +
                ", mobileNumber='" + mobileNumber + '\'' +
                ", messagingType='" + messagingType + '\'' +
                '}';
    }
}
