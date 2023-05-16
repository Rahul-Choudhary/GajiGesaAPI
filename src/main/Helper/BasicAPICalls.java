package main.Helper;

import io.restassured.RestAssured;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.apache.poi.xddf.usermodel.chart.DisplayBlanks;


import java.io.File;

import static org.apache.commons.lang3.StringUtils.isBlank;

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
                                   String schemaPath,
                                   String url,
                                   Object body){
        RestAssured restAssured = new RestAssured();
        Response response = restAssured.given(requestSpecification).body(body).post(url);
        if(responseSpecification==null){
            System.out.println("Status code from thread " + Thread.currentThread().threadId()
                    + " is as " + response.getStatusCode());
        }
        else {
            response.then().spec(responseSpecification);

            response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchema(new File(schemaPath)));
        }
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
