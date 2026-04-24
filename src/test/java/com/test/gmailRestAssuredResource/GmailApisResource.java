package com.test.gmailRestAssuredResource;

import io.restassured.response.Response;

import java.util.Map;

import static com.test.gmailRestAssured.GmailSpecBuilderClass.getRequestSpecBuilder;
import static com.test.gmailRestAssured.GmailSpecBuilderClass.getResponseBuilder;
import static com.test.gmailRestAssuredResource.Routes.*;
import static io.restassured.RestAssured.given;

public class GmailApisResource {


    public static Response post(Map<String, String> payload,String email) {
      return given(getRequestSpecBuilder()).basePath(BASE_PART).
                pathParam("emailId",email).body(payload)
                .when().post(USERS+"/{emailId}"+MESSAGES+SENT).
              then().spec(getResponseBuilder()).extract().response();

    }
    public static Response get(String messageId,String path,String email){
      return given(getRequestSpecBuilder())
                .basePath(path)                        .
                pathParam("emailId", email)
                .pathParam("id", messageId)
                .queryParam("format", "full")
                .when()
                .get(USERS+"/{emailId}"+MESSAGES+"/{id}")
                .then()
                .log().all()
                .extract()
                .response();
    }
    public static Response delete(String messageId,String basePath,String path){
       return given(getRequestSpecBuilder())
                .basePath(basePath)
                .pathParam("id", messageId)
                .when()
                .delete(path)
                .then().extract().response();
    }
}
