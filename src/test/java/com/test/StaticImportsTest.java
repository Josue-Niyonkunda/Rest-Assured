
package com.test;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.is;

public class StaticImportsTest {
    @Test
    public void simpleTestCase(){
        given(). baseUri("http://localhost:3000/").when().get("/students").then().statusCode(200)
                .body("name",hasItems("Alice Johnson"));
    }

}
