package com.test.gmailSpecBuilder;

import exercise.OAuthUtil;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.config.EncoderConfig;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.RestAssured.config;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.is;

public class GmailSpecBuilderClass {



    public static RequestSpecification getRequestSpecBuilder(){
        String accessToken= OAuthUtil.refreshAccessToken("657110595538-1r0fmbtt1i96t28d33tdd18no4s6u9ln.apps.googleusercontent.com",
                "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",
                "1//05a1SoPhyBXu8CgYIARAAGAUSNwF-L9IrLhj8xkeuNbXVZ73mVVtvDEGt_D0Jk_luYfuJ7rcVKSmK4162YEmklss8qP9_fqimIas");


        return new RequestSpecBuilder().
        setBaseUri("https://gmail.googleapis.com").
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
