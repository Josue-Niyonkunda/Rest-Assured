package com.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;

public class JacksonAPI_JSONobject {
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
    public void post_request_as_map() throws JsonProcessingException {
        HashMap<String, Object> mainObject = new HashMap<String, Object>();
        HashMap<String, String> nestedObject = new HashMap<String, String>();
        nestedObject.put("name", "Joseph0 Niyonkunda");
        nestedObject.put("age", "24");
        nestedObject.put("email", "joseph0niyonk.@example.com");
        nestedObject.put("course", "Computer Science");
        mainObject.put("student2", nestedObject);
        ObjectMapper objectMapper= new ObjectMapper();
        String mainObjectStr=objectMapper.writeValueAsString(mainObject);
        given().
                body(mainObjectStr).
                when().
                post("/students").
                then().spec(customResponseSpecification).log().all().assertThat().body("student2.name", equalTo("Joseph0 Niyonkunda"));
    }
    @Test
    public void validate_post_request_payload_array_as_list() throws JsonProcessingException {
        List<HashMap<String, Object>> jsonList = new ArrayList<>();

        HashMap<String, Object> student2 = new HashMap<>();
        student2.put("name", "Jos4 Niyonkunda");
        student2.put("age", 24);
        student2.put("email", "joseu4niyonk@example.com");
        student2.put("course", "ICT");

        HashMap<String, Object> student3 = new HashMap<>();
        student3.put("name", "Jos5 Niyonkunda");
        student3.put("age", 24);
        student3.put("email", "joseu5niyonk@example.com");
        student3.put("course", "IT");

        jsonList.add(student2);
        jsonList.add(student3);
        ObjectMapper objectMapper=new ObjectMapper();
        String ObjectMapperStr= objectMapper.writeValueAsString(jsonList);
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
    @Test
    public void serialize_json_usingJackson() throws JsonProcessingException {
       ObjectMapper objectMapper=new ObjectMapper();
        ObjectNode nestedObjectNode=objectMapper.createObjectNode();
        nestedObjectNode.put("name", "Joseph1 Niyonkunda");
        nestedObjectNode.put("age", "24");
        nestedObjectNode.put("email", "joseph1niyonk.@example.com");
        nestedObjectNode.put("course", "Computer Science");
        ObjectNode mainObjectNode=objectMapper.createObjectNode();
        mainObjectNode.set("student3",nestedObjectNode);

        String mainObjectStr=objectMapper.writeValueAsString(mainObjectNode);
        given().
                body(mainObjectStr).
                when().
                post("/students").
                then().spec(customResponseSpecification).log().all().assertThat().body("student3.name", equalTo("Joseph1 Niyonkunda"));
    }



}


