package exercise;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.config.EncoderConfig;
import io.restassured.http.ContentType;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


import java.util.List;

import static io.restassured.RestAssured.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;

public class Exercise3 {
    ResponseSpecification customResponseSpecification;

    @BeforeClass
    public void before_class_method() {
        RequestSpecBuilder requestSpecBuilder=new RequestSpecBuilder();
        requestSpecBuilder.setBaseUri("http://localhost:3000/").
                setConfig(config.encoderConfig(EncoderConfig.encoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false))).
                setContentType(ContentType.JSON).setConfig(config.logConfig(config.getLogConfig().enableLoggingOfRequestAndResponseIfValidationFails()));

        RestAssured.requestSpecification=requestSpecBuilder.build();
        ResponseSpecBuilder responseSpecBuilder=new ResponseSpecBuilder();
        responseSpecBuilder.expectStatusCode(200)
                .expectContentType(ContentType.JSON);
        customResponseSpecification=responseSpecBuilder.build();

    }
    @Test
    public void fetchValueOfLanguageName(){

        List<String> languageNames = given()
                .when()
                .get("names")
                .then()
                .spec(customResponseSpecification)
                .extract()
                .path("language.name");
                System.out.println("The language names are:"+languageNames);
    }
    @Test
    public void fetchValueOfLanguageUrl(){

        List<String> languageUrl = given()
                .when()
                .get("names")
                .then()
                .spec(customResponseSpecification)
                .extract()
                .path("language.url");
        for(String language :languageUrl){
            System.out.println("The language url :"+language);
        }
//        System.out.println("The language url are:"+languageUrl);

    }
    @Test
    public void fetchValueOfLanguageFields(){

        List<String> FieldName = given()
                .when()
                .get("names")
                .then()
                .spec(customResponseSpecification)
                .extract()
                .path("name");
        System.out.println("The field names are:"+FieldName);
    }



}






