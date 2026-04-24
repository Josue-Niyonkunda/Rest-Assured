package com.test;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.config.EncoderConfig;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.is;

public class GmailAPI {
    ResponseSpecification responseSpecification;
    String messageId;
    String accessToken="ya29.a0Aa7MYirZEUjO3yaB2mVdzoDCY7CLHDdLx-3zSSYW3WMSbQz7kOR7knHrwgjBUjOAigxKqnwpcYd3Dlr0OUhTLyWzwDDJC8zVn_lEG9fcCT9PZlQvKuh3vyqZhWRejy74XeU0L_sfy5suHZXmWL3xgYcpXKXuWuHofNjUNVtmEpGVCG4XQcVdQn6rYDUm7eDTNHmVbdJIaCgYKAaASARQSFQHGX2MiejYAfaPrgQ3pn335SpnhQQ0207";
    @BeforeClass
    public void before_class_method() {

        RequestSpecBuilder requestSpecBuilder=new RequestSpecBuilder();
        requestSpecBuilder.setBaseUri("https://gmail.googleapis.com").
                addHeader("Authorization","Bearer "+accessToken)
        .
//    setConfig(config.encoderConfig(EncoderConfig.encoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false))).
                setContentType(ContentType.JSON);

        requestSpecBuilder.log(LogDetail.ALL);
        RestAssured.requestSpecification=requestSpecBuilder.build();
        ResponseSpecBuilder responseSpecBuilder=new ResponseSpecBuilder();
      responseSpecBuilder.expectStatusCode(anyOf(is(200), is(201)));
               responseSpecBuilder.expectContentType(ContentType.JSON).log(LogDetail.ALL);
        responseSpecification=responseSpecBuilder.build();

    }
    @Test
    public void getUserProfile(){
        given(requestSpecification).basePath("/gmail/v1")
                .when().get("/users/josueniyonkunda22@gmail.com/profile").then().spec(responseSpecification);
    }
    @Test
    public void sendingEmailSms(){
        String sms="From:josueniyonkunda22@gmail.com\n" +
                "To:josueniyonkunda11@gmail.com\n" +
                "Subject: Google OAuth2.0 Access Token\n" +
                "\n" +

                "Sending from Gmail API";
        String smsEncoded= Base64.getUrlEncoder().encodeToString(sms.getBytes());
        Map<String,String> payload=new HashMap<>();
        payload.put("raw",smsEncoded);
        given(requestSpecification).basePath("/gmail/v1").
        pathParam("emailId","josueniyonkunda22@gmail.com").body(payload)
                .when().post("/users/{emailId}/messages/send").then().spec(responseSpecification);
    }
    @Test
    public void fetchEmail() {

        String response =
                given(requestSpecification)
                        .basePath("/gmail/v1")
                                .
                        pathParam("emailId", "josueniyonkunda22@gmail.com")
                        .pathParam("id", messageId)
                        .queryParam("format", "full")
                        .log().all()

                        .when()
                        .get("/users/{emailId}/messages/{id}")

                        .then()
                        .log().all()
                        .statusCode(200)
                        .extract()
                        .asString();

        JsonPath jp = new JsonPath(response);

        System.out.println("Message ID: " + jp.getString("id"));
    }

    @Test
    public void deleteEmail(){

        given(requestSpecification)
                .basePath("/gmail/v1")
                .pathParam("id", messageId)
                .when()
                .delete("/users/me/messages/{id}")
                .then()

                .statusCode(204);

        System.out.println("Deleted successfully: " + messageId);
    }

}
