package com.test.specBuilder;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.config.EncoderConfig;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.RestAssured.config;

public class SpecBuilder {
    public static RequestSpecification getRequestSpec(){
          return new RequestSpecBuilder()
        .setBaseUri("http://localhost:3000/").
                setConfig(config.encoderConfig(EncoderConfig.encoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false))).
                setContentType(ContentType.JSON).log(LogDetail.ALL).build();
    }
    public static ResponseSpecification getResponseSpec(){
       return new ResponseSpecBuilder().expectStatusCode(201)
               .expectContentType(ContentType.JSON).log(LogDetail.ALL).build();

    }





}
