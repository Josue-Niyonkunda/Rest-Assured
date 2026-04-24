package exercise;

import com.exercisePojo.Address;
import com.exercisePojo.Geo;
import com.exercisePojo.User;
import com.exercisePojo.UserRoot;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.config.EncoderConfig;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.awt.geom.GeneralPath;

import static io.restassured.RestAssured.config;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.is;

public class Exercise31 {
    ResponseSpecification responseSpecification;
    @BeforeClass
    public void before_class_method() {
        RequestSpecBuilder requestSpecBuilder=new RequestSpecBuilder();
        requestSpecBuilder.setBaseUri("https://jsonplaceholder.typicode.com").
                setConfig(config.encoderConfig(EncoderConfig.encoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false))).
                setContentType(ContentType.JSON);

        requestSpecBuilder.log(LogDetail.ALL);
        RestAssured.requestSpecification=requestSpecBuilder.build();
        ResponseSpecBuilder responseSpecBuilder=new ResponseSpecBuilder();
        responseSpecBuilder.expectStatusCode(anyOf(is(200), is(201)))
                .expectContentType(ContentType.JSON).log(LogDetail.ALL);
        responseSpecification=responseSpecBuilder.build();

    }
    @Test
            public void pojoTest() {
        Geo geo = new Geo("-37.3159", "81.1496");
        Address address = new Address("Kulas Light", "Apt. 556", "Gwenborough", "92998-3874", geo);
        User user = new User("Leanne Graham", "Bret", "Sincere@april.biz", address);
        UserRoot userRoot = new UserRoot(user);
        given().
                body(userRoot).log().all().
        when().
                post("/users").
        then().
                spec(responseSpecification);
    }

}
