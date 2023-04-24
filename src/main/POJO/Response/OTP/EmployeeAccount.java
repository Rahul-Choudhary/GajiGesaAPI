package main.POJO.Response.OTP;

import java.util.Date;

public class EmployeeAccount {
    public int id;
    public String accountName;
    public String accountTypeName;
    public String status;
    public int availableBalance;
    public Date createdAt;
    public Date updatedAt;

    public int getId() {
        return id;
    }

    public String getAccountName() {
        return accountName;
    }

    public String getAccountTypeName() {
        return accountTypeName;
    }

    public String getStatus() {
        return status;
    }

    public int getAvailableBalance() {
        return availableBalance;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }
}
