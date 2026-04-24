package com.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.config.EncoderConfig;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.config;
import static io.restassured.RestAssured.given;

public class JacksonAPI_JSONArray {
    private static ResponseSpecification customResponseSpecification;

    @BeforeClass
    public void before_class_method() {
        RequestSpecBuilder requestSpecBuilder=new RequestSpecBuilder();
        requestSpecBuilder.setBaseUri("http://localhost:3000/").
                setConfig(config.encoderConfig(EncoderConfig.encoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false))).
                setContentType(ContentType.JSON);

        requestSpecBuilder.log(LogDetail.ALL);
        RestAssured.requestSpecification=requestSpecBuilder.build();
        ResponseSpecBuilder responseSpecBuilder=new ResponseSpecBuilder();
        responseSpecBuilder.expectStatusCode(201)
                .expectContentType(ContentType.JSON).log(LogDetail.ALL);
        customResponseSpecification=responseSpecBuilder.build();

    }
    @Test
    public void serializeJsonArrayUsingJackson() throws JsonProcessingException {
        ObjectMapper objectMapper= new ObjectMapper();
        ArrayNode arrayNodeList=objectMapper.createArrayNode();
        ObjectNode student2=objectMapper.createObjectNode();
        student2.put("name", "Jos4 Niyonkunda");
        student2.put("age", 24);
        student2.put("email", "joseu4niyonk@example.com");
        student2.put("course", "ICT");

        ObjectNode student3=objectMapper.createObjectNode();
        student3.put("name", "Jos5 Niyonkunda");
        student3.put("age", 24);
        student3.put("email", "joseu5niyonk@example.com");
        student3.put("course", "IT");

        arrayNodeList.add(student2);
        arrayNodeList.add(student3);
        String ObjectMapperStr= objectMapper.writeValueAsString(arrayNodeList);
        ObjectMapper mapper = new ObjectMapper();

        List<Map<String, String>> students =
                mapper.readValue(ObjectMapperStr, new TypeReference<List<Map<String, String>>>() {});

        for (Map<String, String> student : students) {
            given()
                    .contentType("application/json")
                    .body(student)
                    .when()
                    .post("/students")
                    .then()
                    .assertThat()
                    .statusCode(201);
        }
    }
}
