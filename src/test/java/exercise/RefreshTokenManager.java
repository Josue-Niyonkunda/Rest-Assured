package utils;
import io.restassured.response.Response;

import java.time.Instant;

import static io.restassured.RestAssured.*;

public class RefreshTokenManager {
    private static String access_token;
    private static Instant expiry_time;
    public static String getToken() {
        try {
            if (access_token == null || expiry_time == null || Instant.now().isAfter(expiry_time)) {
                System.out.println("Renewing token.....");

                ConfigLoader config = ConfigLoader.getInstance();

                Response response = refreshAccessToken(
                        config.getClientId(),
                        config.getClientSecret(),
                        config.getRefreshToken()
                );

                access_token = response.path("access_token");

                int expiryDurationInSeconds = response.path("expires_in");
                expiry_time = Instant.now().plusSeconds(expiryDurationInSeconds - 300);

            } else {
                System.out.println("token is good to use");
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to get a token", e);
        }

        return access_token;
    }


    public static Response refreshAccessToken(String clientId,
                                              String clientSecret,
                                              String refreshToken) {

        return given()
                .baseUri("https://oauth2.googleapis.com")
                .contentType("application/x-www-form-urlencoded")
                .formParam("client_id", clientId)
                .formParam("client_secret", clientSecret)
                .formParam("refresh_token", refreshToken)
                .formParam("grant_type", "refresh_token")
                .log().ifValidationFails()
                .when()
                .post("/token")
                .then()
                .statusCode(200)
                .extract()
                .response();
    }
}