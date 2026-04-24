package exercise;

import io.restassured.response.Response;
import utils.ConfigLoader;

import java.time.Instant;

import static io.restassured.RestAssured.given;

public class RefreshTokenManager {

    private static String accessToken;
    private static Instant expiryTime;

    public static synchronized String getToken() {

        if (accessToken == null || expiryTime == null || Instant.now().isAfter(expiryTime)) {
            System.out.println("🔄 Access token expired or missing. Refreshing...");

            Response response = refreshAccessToken();

            // ❗ handle failure properly
            if (response.statusCode() != 200) {
                System.err.println("❌ Token refresh failed:");
                System.err.println(response.asPrettyString());
                throw new RuntimeException("Failed to refresh access token. Check refresh token validity.");
            }

            accessToken = response.path("access_token");

            Integer expiresIn = response.path("expires_in");
            expiryTime = Instant.now().plusSeconds(expiresIn - 300);

            System.out.println("✅ New access token generated");
        } else {
            System.out.println("✔ Using cached access token");
        }

        return accessToken;
    }

    private static Response refreshAccessToken() {

        ConfigLoader config = ConfigLoader.getInstance();

        return given()
                .baseUri("https://oauth2.googleapis.com")
                .contentType("application/x-www-form-urlencoded")
                .formParam("client_id", config.getClientId())
                .formParam("client_secret", config.getClientSecret())
                .formParam("refresh_token", config.getRefreshToken())
                .formParam("grant_type", "refresh_token")
                .log().ifValidationFails()

                .when()
                .post("/token")

                .then()
                .extract()
                .response();
    }
}