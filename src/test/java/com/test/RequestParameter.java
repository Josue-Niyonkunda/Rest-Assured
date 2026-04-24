package com.test;

import io.restassured.config.EncoderConfig;
import org.testng.IAttributes;
import org.testng.annotations.Test;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.config;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class RequestParameter {
    @Test
    public void singleQueryParameters(){
        given()
                .baseUri("https://postman-echo.com")
               .queryParam("foo1","bar1")
                .log().all()
                .when().get("/get")
                .then()
                .log().all()
                .assertThat()
                .statusCode(200);
    }
    @Test
    public void multipleQueryParameters(){
        HashMap<String,String>queryParam=new HashMap<String,String>();
        queryParam.put("foo1","bar1");
        queryParam.put("foo2","bar1");
        given()
                .baseUri("https://postman-echo.com")
                .queryParam(queryParam.toString())
                .log().all()
                .when().get("/get")
                .then()
                .log().all()
                .assertThat()
                .statusCode(200);
    }
    @Test
    public void multiValueQueryParameters(){
        given()
                .baseUri("https://postman-echo.com")
                .queryParam("foo1","bar1,bar2,bar3")
                .log().all()
                .when().get("/get")
                .then()
                .log().all()
                .assertThat()
                .statusCode(200);
    }
    @Test
    public void pathQueryParameters(){
        given()
                .baseUri("http://localhost:3000")
                .pathParam("studId","2")
                .log().all()
                .when().get("/students/{studId}")
                .then()
                .log().all()
                .assertThat()
                .statusCode(200);
    }
    @Test
    public void multiFormDataQueryParameters(){
        given()
                .baseUri("https://postman-echo.com")
                .multiPart("foo1","bar1")
                .multiPart("foo2","bar2")
                .log().all()
                .when().post("/post")
                .then()
                .log().all()
                .assertThat()
                .statusCode(200);
    }
    @Test
    public void aploadFileMultiFormDataQueryParameters(){

        String attributes = "{\"greet\":\"hello world\"}";
        given()
                .baseUri("https://postman-echo.com")
                .multiPart("file",new File("src/main/resources/file.txt"))
                .multiPart("attributes", attributes,"application/json")
                .log().all()
                .when().post("/post")
                .then()
                .log().all()
                .assertThat()
                .statusCode(200);
    }
    @Test
    public void downLoadFileMultiFormDataQueryParameters() throws IOException {


       InputStream  is= given()
                .baseUri("https://github.com/Josue-Niyonkunda/DevOps_Gate_Preparation/raw/refs/heads")
                .log().all()
                .when().get("/main/.github/workflows/practicesoftwaretestingDevOps.yml")
                .then()
                .log().all()
               .extract().response().asInputStream();
        OutputStream os=new FileOutputStream(new File("practicesoftwaretestingDevOps.yml"));
        byte[] bytes=new byte[is.available()];
        is.read(bytes);
        os.write(bytes);
        os.close();
    }
    @Test
    public void formUrlEncoded() throws IOException {


        given()
                .baseUri("https://postman-echo.com")
                .config(config.encoderConfig(EncoderConfig.encoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false)))
                .formParam("key","value1")
                .formParam("key 2","value 2")
                .log().all()
                .when().post("/post")
                .then()
                .log().all()
                .assertThat().statusCode(200);

    }



}

