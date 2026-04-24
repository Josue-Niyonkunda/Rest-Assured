package com.test.gmailSpecBuilder;

import groovy.lang.GString;
import io.restassured.response.Response;

import java.util.Map;

import static com.test.gmailSpecBuilder.GmailSpecBuilderClass.getRequestSpecBuilder;
import static com.test.gmailSpecBuilder.GmailSpecBuilderClass.getResponseBuilder;
import static io.restassured.RestAssured.given;

public class GmailApis {


    public static Response post(Map<String, String> payload) {
      return given(getRequestSpecBuilder()).basePath("/gmail/v1").
                pathParam("emailId","josueniyonkunda22@gmail.com").body(payload)
                .when().post("/users/{emailId}/messages/send").
              then().spec(getResponseBuilder()).extract().response();

    }
    public static Response get(String messageId){
      return given(getRequestSpecBuilder())
                .basePath("/gmail/v1")                        .
                pathParam("emailId", "josueniyonkunda22@gmail.com")
                .pathParam("id", messageId)
                .queryParam("format", "full")
                .when()
                .get("/users/{emailId}/messages/{id}")
                .then()
                .log().all()
                .extract()
                .response();
    }
    public static Response delete(String messageId){
       return given(getRequestSpecBuilder())
                .basePath("/gmail/v1")
                .pathParam("id", messageId)
                .when()
                .delete("/users/me/messages/{id}")
                .then().extract().response();
    }
}
