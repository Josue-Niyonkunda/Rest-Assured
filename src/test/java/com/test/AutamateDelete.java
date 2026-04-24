package com.test;

import org.testng.annotations.Test;

import java.io.File;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class AutamateDelete {
    @Test
    public void validate_body_bdd(){

        given().when().
                delete("http://localhost:3000/students/BPITE8QnCdE").
                then().log().all().assertThat().body("name",equalTo("Alico Johnson"));
    }


}
