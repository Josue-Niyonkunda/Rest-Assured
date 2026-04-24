package com.test;

import code.StatusCode;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import com.pojo.ResponsePojo;
import com.pojo.SimplePojo;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.config.EncoderConfig;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.internal.http.Status;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.config;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class SimplePojoTest {
   private static ResponseSpecification customResponseSpecification;
   @BeforeClass
    public void before_class_method() {

        RequestSpecBuilder requestSpecBuilder=new RequestSpecBuilder();
        requestSpecBuilder.setBaseUri("https://a0fadf82-8421-4d0d-96d9-e85ecd3813bf.mock.pstmn.io").setConfig(config.encoderConfig(EncoderConfig.encoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false)));
        requestSpecBuilder.log(LogDetail.ALL);
        RestAssured.requestSpecification=requestSpecBuilder.build();
        ResponseSpecBuilder responseSpecBuilder=new ResponseSpecBuilder();
        responseSpecBuilder.expectStatusCode(200)
                .expectContentType(ContentType.JSON).log(LogDetail.ALL);

        customResponseSpecification=responseSpecBuilder.build();

    }
    @Test
    public void simplePojoExample(){


        Faker faker = new Faker();

        String name = faker.name().fullName(); // Miss Samanta Schmidt
        String firstName = faker.name().firstName(); // Emory
        String lastName = faker.name().lastName(); // Barton

        String streetAddress = faker.address().streetAddress(); // 60018 Sawayn Brooks Suite 449
//        SimplePojo simplePojoTest=new SimplePojo(name);
        List<String> fakeDataHolder=new ArrayList<>();
        fakeDataHolder.add(name);
        fakeDataHolder.add(firstName);
        fakeDataHolder.add(lastName);
        fakeDataHolder.add(streetAddress);


        given()
                .body(fakeDataHolder)
                .when()
                .post("/post1")
                .then().statusCode(StatusCode.CODE_200.getCode());
        //.spec(customResponseSpecification);
}
    @Test
    public void simplePojoExampleDeserializing() throws JsonProcessingException {
        SimplePojo simplePojo=new SimplePojo("key3","value3");
        ResponsePojo response =
                given()
                        .contentType("application/json")
                        .body(simplePojo)
                        .when()
                        .post("/post1")
                        .then()
                        .extract()
                        .as(ResponsePojo.class);

        assertThat(response.getMsg3(), equalTo("successful"));
}}
