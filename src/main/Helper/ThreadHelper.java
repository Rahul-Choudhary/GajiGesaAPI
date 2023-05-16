package main.Helper;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import main.POJO.Request.Login.Login;

import java.util.LinkedHashMap;
import java.util.concurrent.Callable;

public class ThreadHelper extends BasicAPICalls implements Runnable {
    private String requestTypeClass = "";
    private RequestSpecification requestSpecificationClass;
    private ResponseSpecification responseSpecificationClass;
    private String urlClass;
    private Object bodyClass;

//    ThreadLocal<LinkedHashMap<String, Object|String>> dataMapLocal =
//            ThreadLocal.withInitial(()-> new LinkedHashMap<String, Object|String>());

    public ThreadHelper(String requestType,
                        RequestSpecification requestSpecification,
                        ResponseSpecification responseSpecification,
                        String url, Object body){
        requestTypeClass = requestType;
        requestSpecificationClass = requestSpecification;
        responseSpecificationClass = responseSpecification;
        urlClass = url;
        bodyClass = body;
    }
    @Override
    public void run() {

//        dataMapLocal.get().put("METHOD", "POST");
//        dataMapLocal.get().put("URL", urlClass);
//        dataMapLocal.get().put("BODY", bodyClass);
//        dataMapLocal.get().put("REQUESTSPECIFICATION", requestSpecificationClass);

        if(requestTypeClass=="POST") {
            System.out.println("I am thread number before "+ Thread.currentThread().threadId());
            //callingPOSTapi(requestSpecificationClass, null, "", urlClass, bodyClass);

//            String bodyLocal = "{\n" +
//                    "    \"loginType\": \"SMS_OTPS\",\n" +
//                    "    \"mobileNumber\": \"62813393721358\",\n" +
//                    "    \"messagingType\": \"SMS\"\n" +
//                    "}";

            try {
//                Integer statusCode =
//                        RestAssured
//                                .given(requestSpecificationClass)
//                                .body(bodyLocal)
//                                .post(urlClass)
//                                .getStatusCode();


                RequestSpecification reqspec = RestAssured
                        .given()
                        .baseUri("https://gajigesa-backend.apps.stg.gajigesa-infra.com")
                        .header("x-api-key","12345678")
                        .contentType(ContentType.JSON)
                        .body(bodyClass.toString());

                Response response = reqspec.post("/auth/login");

//                        .body(bodyClass)
//                        .post(urlClass);

                Integer StatusCode = response.getStatusCode();


            }
            catch (Exception ex){
                System.out.println("inside catch");
                System.out.println(ex.getStackTrace());
                System.out.println(ex.getMessage());
            }

            System.out.println("I am thread number after "+ Thread.currentThread().threadId());
        }
        if(requestTypeClass=="PUT") {
            Response response = RestAssured.given(requestSpecificationClass).body(bodyClass).put(urlClass);
            response.then().spec(responseSpecificationClass);
        }
        if(requestTypeClass=="GET") {
            Response response = RestAssured.given(requestSpecificationClass).body(bodyClass).get(urlClass);
            response.then().spec(responseSpecificationClass);
        }
    }
}
