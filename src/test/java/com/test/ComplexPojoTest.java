package com.test;


import com.studentCollection.Students;
import com.studentCollection.StudentsRoot;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.config.EncoderConfig;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.is;

public class ComplexPojoTest extends BaseTest{
     ResponseSpecification responseSpecification;
    @BeforeClass
    public void before_class_method() {
        RequestSpecBuilder requestSpecBuilder=new RequestSpecBuilder();
        requestSpecBuilder.setBaseUri("http://localhost:3000/").
                setConfig(config.encoderConfig(EncoderConfig.encoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false))).
                setContentType(ContentType.JSON);

        requestSpecBuilder.log(LogDetail.ALL);
        RestAssured.requestSpecification=requestSpecBuilder.build();
        ResponseSpecBuilder responseSpecBuilder=new ResponseSpecBuilder();
        responseSpecBuilder.expectStatusCode(anyOf(is(200), is(201)))
                .expectContentType(ContentType.JSON).log(LogDetail.ALL);
        responseSpecification=responseSpecBuilder.build();

    }
    @Test
    public void complexPojoCreateCollection(){

        Students students = new Students("Josue Niyonkunda","Computer Science","23","joseuniyonk.@example.com");

        StudentsRoot studentsRoot=new StudentsRoot(students);
        String studentRootId=given().
                body(studentsRoot).
        when().
                post("students").
        then().log().all().spec(responseSpecification).extract().response().path("id")
                ;
        System.out.println(studentRootId);
        StudentsRoot deserialised=given().pathParams("studentId",studentRootId).
                when().get("students/{studentId}").then().spec(responseSpecification).
                extract().response().as(StudentsRoot.class);
        System.out.println(deserialised);
    }
}
