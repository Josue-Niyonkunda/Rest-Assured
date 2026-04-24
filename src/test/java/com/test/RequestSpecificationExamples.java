package com.test;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.response.Response;
import io.restassured.specification.QueryableRequestSpecification;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.SpecificationQuerier;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class RequestSpecificationExamples {
    RequestSpecification requestSpecification;
    @BeforeClass
    public void before_class_method() {
//        requestSpecification=given().
//                baseUri("http://localhost:3000/").log().all();
        RequestSpecBuilder requestSpecBuilder=new RequestSpecBuilder();
        requestSpecBuilder.setBaseUri("http://localhost:3000/");
        requestSpecBuilder.log(LogDetail.ALL);
        requestSpecification=requestSpecBuilder.build();
    }
    @Test
    public void validate_status_code(){
        given().spec(requestSpecification)
                .when().get("/students")
                .then().log().all().assertThat().statusCode(200);

    }
    @Test
    public void validate_response_body(){
        given().spec(requestSpecification)
                .when().get("/students")
                .then().log().all().assertThat().statusCode(200).body("name[0]",equalTo("Alice Johnson"));

    }
    @Test
    public void nonBDDTest() {
        Response response=given(requestSpecification).get("students");
        assertThat(response.statusCode(),is(equalTo(200)));
        System.out.println(response.statusCode());
        System.out.println(response.asString());
    }
    @Test
    public void queryTest(){
        QueryableRequestSpecification queryableRequestSpecification= SpecificationQuerier.query(requestSpecification);
        System.out.println(queryableRequestSpecification.getBaseUri());

    }

}
