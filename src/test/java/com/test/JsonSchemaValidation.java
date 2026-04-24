package com.test;

import io.restassured.config.EncoderConfig;
import org.testng.annotations.Test;

import java.io.File;

import static io.restassured.RestAssured.config;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
public class JsonSchemaValidation {
    @Test
    public void jsonSchemaValidator(){


        given()
                .baseUri("https://postman-echo.com")
                .log().all()
                .when().get("/get")
                .then()
                .log().all()
                .assertThat().statusCode(200).body(matchesJsonSchemaInClasspath("schema.json"));

    }
}
