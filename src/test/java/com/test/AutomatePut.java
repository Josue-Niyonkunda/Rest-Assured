package com.test;

import org.testng.annotations.Test;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class AutomatePut {
    @Test
    public void validate_status_body_bdd(){
        String payload=" {\n" +
                "    \"name\": \"Alico12 Johnson\",\n" +
                "    \"age\": 20,\n" +
                "    \"email\": \"alice.johnson@example.com\",\n" +
                "    \"course\": \"Computer Science\",\n" +
                "    \"isActive\": true\n" +
                "  }";
        given().
                body(payload).when().
                put("http://localhost:3000/students/nuAzXiDO8mo").then().log().all().assertThat().body("name",equalTo("Alico12 Johnson"));
    }
    @Test
    public void validate_body_with_file_bdd(){
        File file= new File("src/main/resources/payload.json");

        given().body(file).when().
                put("http://localhost:3000/students/BPITE8QnCdE").
                then().log().all().assertThat().body("name",equalTo("Alico Johnson1"));
    }

}