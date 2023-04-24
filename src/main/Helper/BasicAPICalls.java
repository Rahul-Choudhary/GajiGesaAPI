package main.Helper;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class BasicAPICalls extends basicFunctionality{

    public Response callingGETapi(RequestSpecification requestSpecificaion,
                                  ResponseSpecification responseSpecification,
                                  String url){
        Response response = RestAssured.given(requestSpecificaion).get(url);
        response.then().spec(responseSpecification);
        return response;
    }

    public Response callingPUTapi(RequestSpecification requestSpecification,
                                  ResponseSpecification responseSpecification,
                                  String url,
                                  Object body){
        Response response = RestAssured.given(requestSpecification).body(body).post(url);
        response.then().spec(responseSpecification);
        return response;
    }

    public Response callingPOSTapi(RequestSpecification requestSpecification,
                                   ResponseSpecification responseSpecification,
                                   String url,
                                   Object body){
        Response response = RestAssured.given(requestSpecification).body(body).post(url);
        response.then().spec(responseSpecification);
        return response;
    }

    public Response callingDELETEapi(RequestSpecification requestSpecification,
                                     ResponseSpecification responseSpecification,
                                     String url){
        Response response = RestAssured.given(requestSpecification).delete(url);
        response.then().spec(responseSpecification);
        return response;
    }
}
