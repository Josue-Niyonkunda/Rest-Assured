package com.test.gmailRestAssured;

import exercise.RefreshTokenManager;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.config.EncoderConfig;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static com.test.gmailRestAssuredResource.Routes.BASEURL;
import static io.restassured.RestAssured.config;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.is;

public class GmailSpecBuilderClass {



    public static RequestSpecification getRequestSpecBuilder(){
        String accessToken= RefreshTokenManager.getToken();

        return new RequestSpecBuilder().
        setBaseUri(BASEURL).
                addHeader("Authorization","Bearer "+accessToken).
   setConfig(config.encoderConfig(EncoderConfig.encoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false))).
        setContentType(ContentType.JSON).log(LogDetail.ALL).build();


    }
    public static ResponseSpecification getResponseBuilder(){
        return new ResponseSpecBuilder().
        expectStatusCode(anyOf(is(200), is(201))).
        expectContentType(ContentType.JSON).log(LogDetail.ALL).build();

    }
}
