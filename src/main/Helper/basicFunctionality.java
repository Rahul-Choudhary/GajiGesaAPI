package main.Helper;

import com.google.gson.Gson;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.hamcrest.Matchers;
import java.io.*;
import java.util.HashMap;
import java.util.Properties;
import java.util.Set;

public class basicFunctionality {
    protected String ENV_SELECTED = null;
    protected final String PROJECT_PATH = System.getProperty("user.dir");
    protected String BASE_URL = "";
    protected RequestSpecification REQUEST_SPECIFICATION;
    protected final String RESOURCES_PATH = PROJECT_PATH+"/src/main/resources/";
    protected final String RESPONSE_POJO_PATH = PROJECT_PATH+"/src/main/POJO/Response/";
    protected ResponseSpecification RESPONSE_SPECIFICATION_201;
    protected ResponseSpecification RESPONSE_SPECIFICATION_400;
    protected ResponseSpecification RESPONSE_SPECIFICATION_404;
    protected final String JSON_REQUEST_PATH = PROJECT_PATH+"/src/main/POJO/Request/";
    protected String ACCESS_TOKEN_RECEIVED_FROM_RESPONSE = "";
    public HashMap<String, String> loadingValueFromProperties(String propertiesFilePath) throws IOException {
        Properties properties = new Properties();
        properties.load(new FileInputStream(new File(propertiesFilePath)));
        Set<Object> keySet = properties.keySet();
        HashMap<String, String> valueFromProperties = new HashMap<>();
        keySet.stream().forEach(x-> valueFromProperties.put(x.toString(), properties.get(x.toString()).toString()));
        return valueFromProperties;
    }
    public String loadingEnvironment() throws IOException {
        ENV_SELECTED = System.getProperty("env");
        if(ENV_SELECTED ==null){
            HashMap<String, String> valueFromProperties = loadingValueFromProperties(RESOURCES_PATH + "environment.properties");
            ENV_SELECTED = valueFromProperties.get("env");
        }
        return ENV_SELECTED;
    }

    public static Object creatingObjectFromJsonFile(String jsonPath, Class requestClassStruct) throws FileNotFoundException {
        return new Gson().
                fromJson(
                        new FileReader(
                                new File(jsonPath)),
                        requestClassStruct);
    }

    public void initializingRequestAndResponseSpecification(){
        Header header = new Header("x-api-key","12345678");

        REQUEST_SPECIFICATION = RestAssured.given().baseUri(BASE_URL).header(header).contentType(ContentType.JSON);
        RESPONSE_SPECIFICATION_201 = RestAssured.expect().
                statusCode(201).
                time(Matchers.lessThan(5000L));

        RESPONSE_SPECIFICATION_404 = RestAssured.expect().
                statusCode(404).
                time(Matchers.lessThan(5000L));

        RESPONSE_SPECIFICATION_400 = RestAssured.expect().
                statusCode(400).
                time(Matchers.lessThan(5000L));

    }

    public void loadingBaseUrl(String environmentSelected) throws IOException {
        HashMap<String, String> envBasePath = loadingValueFromProperties(RESOURCES_PATH+"basePath.properties");
        BASE_URL = envBasePath.get(environmentSelected);
    }

    public Object creatingObjectFromResponse(Response response, Class responseObject){
        return response.getBody().as(responseObject);
    }

    public Object fetchingPathFromResponse(Response response, String pathOfVariableToFetchValueOf){
        return response.getBody().jsonPath().get(pathOfVariableToFetchValueOf);
    }
}
