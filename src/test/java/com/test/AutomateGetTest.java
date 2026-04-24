package com.test;

import io.restassured.config.LogConfig;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.config;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;

public class AutomateGetTest extends BaseTest {
    @Test
    public void validate_status_code(){
        given(). baseUri("http://localhost:3000/")
                .when().get("/students")
                .then().log().all().assertThat().statusCode(200);

    }
    @Test
    public void validate_response_body(){
        given().log().all(). baseUri("http://localhost:3000/")
                .when().get("/students")
                .then().log().all().statusCode(200)
                .body("name",hasItems("Alice Johnson"));
    }
    @Test
    public void extract_response(){
        Response response=given(). baseUri("http://localhost:3000/")
                .when().get("/students")
                .then().assertThat().statusCode(200)
                .body("name",hasItems("Alice Johnson")).extract().response();
        System.out.println("response:"+response.asString());
    }
    @Test
    public void extract_single_value_from_response(){
        Response resp=given(). baseUri("http://localhost:3000/")
                .when().get("/students")
                .then().assertThat().statusCode(200)
                .extract().response();

        System.out.println("response:"+resp.path("name[0]"));
    }
    @Test
    public void extract_multiple_userNames_from_response(){
        Response resp=given(). baseUri("http://localhost:3000/")
                .when().get("/students")
                .then().assertThat().statusCode(200)
                .extract().response();

        System.out.println("response:"+resp.path("name"));
    }
    @Test
    public void hamcrest_assert_on_extracted_response(){
        String name=given(). baseUri("http://localhost:3000/")
                .when().get("/students")
                .then().assertThat().statusCode(200)
                .extract().response().path("name[0]");
        //using hamcrest library
        assertThat(name,equalTo("Alice Johnson"));
        //using TestNG library
        Assert.assertEquals(name,"Alice Johnson","username test fail");
        System.out.println("response:"+name);
    }
    @Test
    public void request_response_logging(){
        given().log().all().baseUri("http://localhost:3000/")
                .when().get("/students")
                .then().log().all().assertThat().statusCode(200);

    }
    @Test
    public void log_only_if_error(){
        given().log().all().baseUri("http://localhost:3000/")
                .when().get("/students")
                .then().log().ifError().assertThat().statusCode(200);

    }
    @Test
    public void log_only_if_validation_fails(){
        given().baseUri("http://localhost:3000/"). config(config.logConfig(LogConfig.logConfig().enableLoggingOfRequestAndResponseIfValidationFails()))
//                log().
//                ifValidationFails()
                .when().get("/students")
              .then()
              //  .log().ifValidationFails()
                .assertThat().statusCode(200);

    }
    @Test
    public void log_blacklist_header(){
        given().baseUri("http://localhost:3000/").header("Content-Type","application/json")
        .config(config.logConfig(LogConfig.logConfig().blacklistHeader("Content-Type"))).log().all()
                .when().get("/students")
                .then()
                .assertThat().statusCode(200);
    }


}
