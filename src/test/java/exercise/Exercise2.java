package exercise;

import org.testng.annotations.Test;

import java.util.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class Exercise2 {
    @Test
    public void requestPayloadValidation(){
        Map<String,Object>payload=new HashMap<>();
        List<Map<String,Object>>colors=new ArrayList<Map<String,Object>>();
        Map<String,Object> color1 = new HashMap<>();
        color1.put("color", "black");
        color1.put("category", "hue");
        color1.put("type", "primary");

        Map<String, Object> code1 = new HashMap<>();
        code1.put("rgba", Arrays.asList(255, 255, 255, 1));
        code1.put("hex", "#000");

        color1.put("code", code1);
        Map<String, Object> color2 = new HashMap<>();
        color2.put("color", "white");
        color2.put("category", "value");

        Map<String, Object> code2 = new HashMap<>();
        code2.put("rgba", Arrays.asList(0, 0, 0, 1));
        code2.put("hex", "#FFF");
        color2.put("code", code2);
        colors.add(color1);
        colors.add(color2);

        payload.put("colors", colors);

        given().baseUri("https://edafec7f-be6d-44b7-aac8-ed1298344861.mock.pstmn.io")
                .contentType("application/json")
                .body(payload)
                .when()
                .post("/post")
                .then()
                .log().all().body("msg",equalTo("create successful"));

    }
}
