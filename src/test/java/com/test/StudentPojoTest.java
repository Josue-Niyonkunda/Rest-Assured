package com.test;

import io.qameta.allure.*;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.config.EncoderConfig;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.ResponseSpecification;
import jdk.jfr.Description;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.pojoStudent.StudentPojo;
import com.pojoStudent.StudentRoot;

import static io.restassured.RestAssured.config;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
@Epic("Students POJO")
@Feature("Mapping")
public class StudentPojoTest {
    ResponseSpecification customResponseSpecification;

    @BeforeClass
    @Step
    public void before_class_method() {
        RequestSpecBuilder requestSpecBuilder=new RequestSpecBuilder();
        requestSpecBuilder.setBaseUri("http://localhost:3000/").
                setConfig(config.encoderConfig(EncoderConfig.encoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false))).
                setContentType(ContentType.JSON).addFilter(new AllureRestAssured());

        requestSpecBuilder.log(LogDetail.ALL);
        RestAssured.requestSpecification=requestSpecBuilder.build();
        ResponseSpecBuilder responseSpecBuilder=new ResponseSpecBuilder();
        responseSpecBuilder.expectStatusCode(201)
                .expectContentType(ContentType.JSON).log(LogDetail.ALL);
        customResponseSpecification=responseSpecBuilder.build();

    }
    @Story("serialisation and deserialisation")
    @Test(description = "mapDeserialisation")
    @Description("This is map deserialisation")
    @Step
    public void post_request_as_map_deserialization() {
        StudentPojo student=new StudentPojo( "Josue1 Niyonkunda",  24, "joseuniyonk.@example.com", "Computer Science", "isActive",  "oieuiewur");
        StudentRoot studentRoot=new StudentRoot(student);
        given().body(studentRoot)
                .post("/students").
                then().spec(customResponseSpecification).log().all().assertThat().body("studentPojo.name", equalTo("Josue1 Niyonkunda"));
    }
    @Test
    @Step
    public void post_request_as_map_serialize_deserialization() {
        StudentPojo student=new StudentPojo( "Josue1 Niyonkunda",  24, "joseuniyonk.@example.com", "Computer Science", "isActive",  "oieuiewur");
        StudentRoot studentRoot=new StudentRoot(student);
       StudentRoot deserialized= given().body(studentRoot)
                .post("/students").
                then().spec(customResponseSpecification).extract().response().as(StudentRoot.class);
        assertThat(deserialized.getStudentPojo().getName(),equalTo("Josue1 Niyonkunda"));
        assertThat(deserialized.getStudentPojo().getAge(),equalTo(24));
        assertThat(deserialized.getStudentPojo().getEmail(),equalTo("joseuniyonk.@example.com"));
        assertThat(deserialized.getStudentPojo().getCourse(),equalTo("Computer Science"));
        assertThat(deserialized.getStudentPojo().getId(),equalTo("oieuiewur"));
    }
    @Test(dataProvider = "student")
    @Step
    public void post_request_as_map_serialize_deserialization_parametrisation_using_TestNG_DataProvider(String name, int age, String email, String course, String isActive, String id) {
        StudentPojo student=new StudentPojo( name,  age, email, course, isActive,  id);
        StudentRoot studentRoot=new StudentRoot(student);
        StudentRoot deserialized= given().body(studentRoot)
                .post("/students").
                then().spec(customResponseSpecification).extract().response().as(StudentRoot.class);
        assertThat(deserialized.getStudentPojo().getName(),equalTo("Josue Niyonkunda"));
    }
    @Story("serialisation and deserialisation and data providers")
    @DataProvider(name = "student")
    @Step
    public Object[][] getStudent(){
        return new Object[][]{
                {"Josue Niyonkunda",  20, "joseuniyonk20.@example.com", "Computer Science", "isActive",  "oieuiewur20"},
                {"Josue Niyonkunda",  21, "joseuniyonk21.@example.com", "Computer Science", "isActive",  "oieuiewur"},
                {"Josue Niyonkunda",  23, "joseuniyonk23.@example.com", "Computer Science", "isActive",  "oieuiewur"}

        };
    }


}
