package com.test;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.QueryableRequestSpecification;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import io.restassured.specification.SpecificationQuerier;
import org.testng.annotations.BeforeMethod;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static java.lang.Math.log;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
public class Test {

RequestSpecification requestSpecification;
ResponseSpecification responseSpecification;
    @BeforeMethod
    public void setup(){
        requestSpecification=given().
                baseUri("http://localhost:3000/");
        responseSpecification= RestAssured.expect().
                statusCode(200).contentType(ContentType.JSON);
    }

    @org.testng.annotations.Test
    public void test1(){
        given().spec(requestSpecification).
                when().log().all().get("students").
              then().log().all().body("name",hasItems("Alice Johnson"));
//        given().baseUri("http://localhost:3000/").log().all().
//                when().log().all().get("students").
//                then().log().all().body("name",hasItems("Alice Johnson")).headers("Content-Type","application/json","Content-Length","5784");
//
////        Response res= given().baseUri("http://localhost:3000/
//    when().get("students").
//    then().assertThat().statusCode(200).body("name",hasItems("Alice Johnson","jo","Brian Smith")).
//            body("course",hasItems("Information Technology","Computer Science","Software Engineering"))
//        .extract().response();
//        JsonPath jsonPath=new JsonPath(res.asString());
////        System.out.println(jsonPath.getString("name[0]"));
//        System.out.println(res.asString());
//        Assert.assertEquals(jsonPath.getString("name[0]"),"Alice Johnson"," names not match");
   }
   @org.testng.annotations.Test
    public void test2() {
       Response response=requestSpecification.get("students");
       response.then().log().all().headers("Content-Type", "application/json", "Content-Length", "5784");
       assertThat(response.header("Content-Type"),is(equalTo("application/json")));
   }


}