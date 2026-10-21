package com.sdet.api.clients;

import org.apache.logging.log4j.Logger;

import com.sdet.api.models.TestUser;
import com.sdet.config.ConfigReader;
import com.sdet.utils.LogUtil;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;


public class UserApiClient {
    
    private static final Logger log = LogUtil.getLogger(UserApiClient.class);

    public void createUser(TestUser user){
        Response response = given()
            .baseUri(ConfigReader.get("baseUrl"))
            .formParam("name", user.fullName())
            .formParam("email", user.email())
            .formParam("password", user.password())
            .formParam("title", "Mr")
            .formParam("birth_date", "10")
            .formParam("birth_month", "October")
            .formParam("birth_year", "1995")
            .formParam("firstname", user.firstName())
            .formParam("lastname", user.lastName())
            .formParam("company", "QA")
            .formParam("address1", user.address())
            .formParam("address2", "")
            .formParam("country", user.country())
            .formParam("zipcode", user.zipcode())
            .formParam("state", user.state())
            .formParam("city", user.city())
            .formParam("mobile_number", user.mobile())
            .post("/api/createAccount");

        int code = new JsonPath(response.asString()).getInt("responseCode");
        if (code != 201) {
            throw new RuntimeException("createAccount failed: " + response.asString());
        }
        log.info("Created test user via API: {}", user.email());
    }

    public void deleteUser(TestUser user){
        try{
            given().baseUri(ConfigReader.get("baseUrl"))
            .formParam("email", user.email())
            .formParam("password", user.password())
            .delete("/api/deleteAccount");
            log.info("Deleted test user: {}", user.email());
        } catch (Exception e) {
            log.warn("Could not delete test user {}: {}", user.email(), e.getMessage());
        }
    }
    
}
