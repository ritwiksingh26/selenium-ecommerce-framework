package com.sdet.api.config;

import com.sdet.config.ConfigReader;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class ApiConfig {

    private static RequestSpecification requestSpec;
    private static ResponseSpecification responseSpec;

    public static RequestSpecification getRequestSpec(){
        if (requestSpec == null){
            requestSpec = new RequestSpecBuilder()
                    .setBaseUri(ConfigReader.get("apiBaseUrl"))
                    .setContentType(ContentType.JSON)
                    .addFilter(new RequestLoggingFilter())
                    .addFilter(new ResponseLoggingFilter())
                    .build();
        }
        return requestSpec;
    }

    public static ResponseSpecification getResponseSpec(){
        if (responseSpec == null){
            responseSpec = new ResponseSpecBuilder()
                    .expectContentType(ContentType.JSON)
                    .build();
        }
        return responseSpec;
    }

    public static void init(){
        RestAssured.baseURI = ConfigReader.get("apiBaseUrl");
        RestAssured.useRelaxedHTTPSValidation();
    }
}
