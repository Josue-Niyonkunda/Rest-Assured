package com.test;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;


public class ResponseSpecificationExample {
//    ResponseSpecification responseSpecification;
//    @BeforeClass
//    public void before_class_method() {
////        requestSpecification=given().
////                baseUri("http://localhost:3000/").log().all();
//        RequestSpecBuilder requestSpecBuilder=new RequestSpecBuilder();
//        requestSpecBuilder.setBaseUri("http://localhost:3000/");
//        requestSpecBuilder.log(LogDetail.ALL);
//        RestAssured.requestSpecification=requestSpecBuilder.build();
////        responseSpecification=RestAssured.expect().statusCode(200)
////                .contentType(ContentType.JSON).log().all();
//        ResponseSpecBuilder responseSpecBuilder=new ResponseSpecBuilder();
//        responseSpecBuilder.expectStatusCode(200)
//                .expectContentType(ContentType.JSON).log(LogDetail.ALL);
//        responseSpecification=responseSpecBuilder.build();
//
//    }
//    @Test
//    public void validate_status_code(){
//        get("/students").then().spec(responseSpecification);
//    }
//    @Test
//    public void validate_response_body(){
//
//                Response response=get("/students")
//                .then().spec(responseSpecification).extract().response();
//                assertThat(response.path("name[0]").toString(),equalTo("Alice Johnson"))
//;
//    }


    @BeforeClass
    public void before_class_method() {
//        requestSpecification=given().
//                baseUri("http://localhost:3000/").log().all();
        RequestSpecBuilder requestSpecBuilder=new RequestSpecBuilder();
        requestSpecBuilder.setBaseUri("http://localhost:3000/");
        requestSpecBuilder.log(LogDetail.ALL);
        RestAssured.requestSpecification=requestSpecBuilder.build();
//        responseSpecification=RestAssured.expect().statusCode(200)
//                .contentType(ContentType.JSON).log().all();
        ResponseSpecBuilder responseSpecBuilder=new ResponseSpecBuilder();
        responseSpecBuilder.expectStatusCode(200)
                .expectContentType(ContentType.JSON).log(LogDetail.ALL);
        RestAssured.responseSpecification=responseSpecBuilder.build();

    }
    @Test
    public void validate_status_code(){
        get("/students");
    }
    @Test
    public void validate_response_body(){

        Response response=get("/students")
                .then().extract().response();
        assertThat(response.path("name[0]").toString(),equalTo("Alice Johnson"))
        ;
    }


}
