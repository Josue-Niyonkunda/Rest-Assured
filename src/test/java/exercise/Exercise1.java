package exercise;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.config.EncoderConfig;
import io.restassured.config.LogConfig;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;



import static io.restassured.RestAssured.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.lessThan;

public class Exercise1 {

        ResponseSpecification customResponseSpecification;

        @BeforeClass
        public void before_class_method() {
            RequestSpecBuilder requestSpecBuilder=new RequestSpecBuilder();
            requestSpecBuilder.setBaseUri("https://dummyjson.com").
                    setConfig(config.encoderConfig(EncoderConfig.encoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false))).
                    setContentType(ContentType.JSON);

            requestSpecBuilder.log(LogDetail.ALL);
            RestAssured.requestSpecification=requestSpecBuilder.build();
            ResponseSpecBuilder responseSpecBuilder=new ResponseSpecBuilder();
            responseSpecBuilder.expectStatusCode(200)
                    .expectContentType(ContentType.JSON).log(LogDetail.ALL);
            customResponseSpecification=responseSpecBuilder.build();

        }

    @Test
    public void fetchUserValidation(){
            given().

                    when().spec(requestSpecification).get("/users").
                    then().spec(customResponseSpecification);



    }
    @Test
    public void log_only_if_validation_fails() {

        given()
                .baseUri("https://dummyjson.com")
                .config(config()
                        .logConfig(LogConfig.logConfig()
                                .enableLoggingOfRequestAndResponseIfValidationFails()))
                .when()
                .get("/users")
                .then()
                .assertThat()
                .statusCode(200)
                .time(lessThan(2000L));
    }


    }






