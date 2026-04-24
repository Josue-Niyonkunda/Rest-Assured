package com.test;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class RequestSpecification {
    @Test
    public void validate_status_code(){
        RequestSpecification requestSpecification;
        given(). baseUri("http://localhost:3000/")
                .when().get("/students")
                .then().log().all().assertThat().statusCode(200);

    }

}
