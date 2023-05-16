package main.POJO.Response.Login;

import java.util.ArrayList;

public class InvalidFieldsResponse {


        public ArrayList<Validation> validations;
        public String error;

    public ArrayList<Validation> getValidations() {
        return validations;
    }

    public String getError() {
        return error;
    }
}

