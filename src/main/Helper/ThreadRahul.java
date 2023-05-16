package main.Helper;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import main.POJO.Request.Login.Login;

import java.io.FileNotFoundException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class ThreadRahul extends BasicAPICalls{
    public static void main(String[] args) {
        // Create an array of CompletableFuture instances representing your requests
        CompletableFuture<String> request1 = CompletableFuture.supplyAsync(() -> makeRequest("Request 1"));
        CompletableFuture<String> request2 = CompletableFuture.supplyAsync(() -> makeRequest("Request 2"));
        CompletableFuture<String> request3 = CompletableFuture.supplyAsync(() -> makeRequest("Request 3"));

        // Use CompletableFuture.allOf to wait for all requests to complete
        CompletableFuture<Void> allRequests = CompletableFuture.allOf(request1, request2, request3);

        // Handle the completion of all requests
        allRequests.thenRun(() -> {
            try {
                // Get the results of individual requests
                String result1 = request1.get();
                String result2 = request2.get();
                String result3 = request3.get();

                // Process the results as needed
                System.out.println("Result 1: " + result1);
                System.out.println("Result 2: " + result2);
                System.out.println("Result 3: " + result3);
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        });

        // Wait for all requests to complete
        try {
            allRequests.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    private static String makeRequest(String requestName) {
        // Simulate a request that takes some time to complete
        Response response = null;
        try {
            Thread.sleep(2000); // Sleep for 2 seconds

            Login login = (Login)creatingObjectFromJsonFile(System.getProperty("user.dir")+"/src/main/POJO/Request/Login/login.json", Login.class);
login.setMobileNumber("629773933739");
login.setMessagingType("SMS");

            RequestSpecification reqspec = RestAssured
                    .given()
                    .baseUri("https://gajigesa-backend.apps.stg.gajigesa-infra.com")
                    .header("x-api-key","12345678")
                    .contentType(ContentType.JSON)
                    .body(login);

            response = reqspec.post("/auth/login");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        // Return the result of the request
        return "Response for " + response.getBody().asString();

    }
}
