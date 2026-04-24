package com.test;

import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class Filters {
    @Test
    public void filterLogs(){


        given()
                .baseUri("https://postman-echo.com")
                .filter(new RequestLoggingFilter(LogDetail.ALL)).
                filter(new ResponseLoggingFilter(LogDetail.STATUS))
//                .log().all()
                .when().get("/get")
                .then()
//                .log().all()
                .assertThat().statusCode(200);

    }
    @Test
    public void loggingFilters () throws FileNotFoundException {
        PrintStream FileOutPutStream=new PrintStream(new File("RestAssured.log"));

        given()
                .baseUri("https://postman-echo.com")
                .filter(new RequestLoggingFilter(LogDetail.ALL,FileOutPutStream)).
                filter(new ResponseLoggingFilter(LogDetail.STATUS,FileOutPutStream))
                .when().get("/get")
                .then()
                .assertThat().statusCode(200);

    }
}
