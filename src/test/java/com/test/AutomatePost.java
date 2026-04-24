package com.test;

import com.test.specBuilder.SpecBuilder;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.test.specBuilder.SpecBuilder.getRequestSpec;
import static com.test.specBuilder.SpecBuilder.getResponseSpec;
import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class AutomatePost extends BaseTest {

        @org.testng.annotations.Test

    public void validate_status_body_bdd(){

        String payload=" {\n" +
                "    \"name\": \"Alico Johnson\",\n" +
                "    \"age\": 20,\n" +
                "    \"email\": \"alice.johnson@example.com\",\n" +
                "    \"course\": \"Computer Science\",\n" +
                "    \"isActive\": true\n" +
                "  }";
        given().spec(getRequestSpec()).
                body(payload)
                .when().
        post("/students").then().spec(getResponseSpec()).assertThat().body("name",equalTo("Alico Johnson"));
    }
    @Test
    public void validate_status_body_non_bdd(){
        String payload=" {\n" +
                "    \"name\": \"Alico Johnson\",\n" +
                "    \"age\": 20,\n" +
                "    \"email\": \"alice.johnson@example.com\",\n" +
                "    \"course\": \"Computer Science\",\n" +
                "    \"isActive\": true\n" +
                "  }";
         Response response=with().spec(getRequestSpec()).body(payload).
                post("/students");
         assertThat(response.path("name"),equalTo("Alico Johnson"));

    }
    @Test
    public void post_request_as_map() {
        HashMap<String, Object> mainObject = new HashMap<String, Object>();
        HashMap<String, String> nestedObject = new HashMap<String, String>();
        nestedObject.put("name", "Josue1 Niyonkunda");
        nestedObject.put("age", "24");
        nestedObject.put("email", "joseuniyonk.@example.com");
        nestedObject.put("course", "Computer Science");
        mainObject.put("student1", nestedObject);
        given().spec(getRequestSpec()).body(mainObject).post("/students").then().spec(getResponseSpec()).log().all().assertThat().body("student1.name", equalTo("Josue1 Niyonkunda"));
    }
    @Test
    public void validate_post_request_payload_array_as_list(){
        List<HashMap<String, Object>> jsonList = new ArrayList<>();

        HashMap<String, Object> student2 = new HashMap<>();
        student2.put("name", "Josue4 Niyonkunda");
        student2.put("age", 24);
        student2.put("email", "joseu4niyonk@example.com");
        student2.put("course", "ICT");

        HashMap<String, Object> student3 = new HashMap<>();
        student3.put("name", "Josue5 Niyonkunda");
        student3.put("age", 24);
        student3.put("email", "joseu5niyonk@example.com");
        student3.put("course", "IT");

        jsonList.add(student2);
        jsonList.add(student3);

        for (Map<String, Object> student : jsonList) {
            given(getRequestSpec())
                    .contentType("application/json")
                    .body(student)
                    .when()
                    .post("/students").then().spec(getResponseSpec()).assertThat().statusCode(201);
        }
    }

}