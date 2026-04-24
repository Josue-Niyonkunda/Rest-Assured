package com.test;

import io.restassured.RestAssured;
import io.restassured.authentication.FormAuthConfig;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.Filter;
import io.restassured.filter.session.SessionFilter;
import io.restassured.http.Cookie;
import io.restassured.http.Cookies;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class FormAuthentication {
    @BeforeClass
    public void befofeClasss(){
        RestAssured.requestSpecification=new RequestSpecBuilder().setRelaxedHTTPSValidation()
                .setBaseUri("https://localhost:8443").build();
    }
    @Test
    public void formAuthenticationUsingCsrfToken(){
        given().
                auth().form("dan", "dan123", new FormAuthConfig("/signin", "txtUsername", "txtPassword")).
       log().all() . when().
                get("/login").
        then().log().all().assertThat().
                statusCode(200);
    }
    @Test
    public void formAuthenticationUsingCsrfTokenWithSessionFilter() {

        SessionFilter filter = new SessionFilter();

        // Step 1: Get login page and extract CSRF token
        Response loginPage =
                given()
                        .filter(filter)
                        .log().all()
                        .when()
                        .get("/login");

        String csrfToken = loginPage
                .htmlPath()
                .getString("**.find { it.@name == '_csrf' }.@value");

        System.out.println("CSRF Token: " + csrfToken);
        System.out.println("SessionId: " + filter.getSessionId());

        // Step 2: Perform login (REAL authentication)
        given()
                .filter(filter)
                .contentType("application/x-www-form-urlencoded")
                .formParam("txtUsername", "dan")
                .formParam("txtPassword", "dan123")
                .formParam("_csrf", csrfToken)
                .log().all()
                .when()
                .post("/signin")
                .then()
                .log().all()
                .statusCode(302); // Spring Security redirect after login

        // Step 3: Access protected endpoint
        given()
                .filter(filter)
                .log().all()
                .when()
                .get("/profile/index")
                .then()
                .log().all()
                .statusCode(200);
    }
    @Test
    public void ValidatingHtml() {

        SessionFilter filter = new SessionFilter();

        // Step 1: Get login page and extract CSRF token
        Response loginPage =
                given()
                        .filter(filter)
                        .log().all()
                        .when()
                        .get("/login");

        String csrfToken = loginPage
                .htmlPath()
                .getString("**.find { it.@name == '_csrf' }.@value");

        System.out.println("CSRF Token: " + csrfToken);
        System.out.println("SessionId: " + filter.getSessionId());

        // Step 2: Perform login (REAL authentication)
        given()
                .filter(filter)
                .contentType("application/x-www-form-urlencoded")
                .formParam("txtUsername", "dan")
                .formParam("txtPassword", "dan123")
                .formParam("_csrf", csrfToken)
                .log().all()
                .when()
                .post("/signin")
                .then()
                .log().all()
                .statusCode(302); // Spring Security redirect after login

        // Step 3: Access protected endpoint
        given()
                .filter(filter)
                .log().all()
                .when()
                .get("/profile/index")
                .then()
                .log().all()
                .statusCode(200).body("html.body.div.p",equalTo("This is User Profile\\Index. Only authenticated people can see this"));
    }
    @Test
    public void formAuthenticationUsingCookies() {

        SessionFilter filter = new SessionFilter();

        // Get CSRF + session
        Response loginPage = given().filter(filter).get("/login");

        String csrf = loginPage.htmlPath()
                .getString("**.find { it.@name == '_csrf' }.@value");

        String session = filter.getSessionId();

        // Login
        String authSession =
                given()
                        .cookie("JSESSIONID", session)
                        .contentType("application/x-www-form-urlencoded")
                        .formParam("txtUsername", "dan")
                        .formParam("txtPassword", "dan123")
                        .formParam("_csrf", csrf)
                        .when()
                        .post("/signin")
                        .getCookie("JSESSIONID");

        // Access protected API
        given()
                .cookie("JSESSIONID", authSession)
                .when().log().all()
                .get("/profile/index")
                .then().log().all()
                .statusCode(200).body("html.body.div.p",equalTo("This is User Profile\\Index. Only authenticated people can see this"));
    }
    @Test
    public void formAuthenticationUsingCookiesBuilder() {

        SessionFilter filter = new SessionFilter();

        // Get CSRF + session
        Response loginPage = given().filter(filter).get("/login");

        String csrf = loginPage.htmlPath()
                .getString("**.find { it.@name == '_csrf' }.@value");

        String session = filter.getSessionId();
        Cookie cookieLogin=new Cookie.Builder("JSESSIONID", session).setComment("I am testing cookies")
                .setHttpOnly(true).setSecured(true).build();

        // Login
        String authSession =
                given()
                        .cookie(cookieLogin)
                        .contentType("application/x-www-form-urlencoded")
                        .formParam("txtUsername", "dan")
                        .formParam("txtPassword", "dan123")
                        .formParam("_csrf", csrf)
                        .when()
                        .post("/signin")
                        .getCookie("JSESSIONID");
        Cookie AccessCookie= new Cookie.Builder("JSESSIONID", authSession).setComment("I am using cookie to access profile")
                        .setHttpOnly(true).build();

        // Access protected API
        given()
                .cookie(AccessCookie)
                .when().log().all()
                .get("/profile/index")
                .then().log().all()
                .statusCode(200).body("html.body.div.p",equalTo("This is User Profile\\Index. Only authenticated people can see this"));
    }
    @Test
    public void formAuthenticationUsingCookiesWithMultipleCookies() {

        SessionFilter filter = new SessionFilter();

        // Get CSRF + session
        Response loginPage = given().filter(filter).get("/login");

        String csrf = loginPage.htmlPath()
                .getString("**.find { it.@name == '_csrf' }.@value");

        String session = filter.getSessionId();
        Cookie cookieLogin=new Cookie.Builder("JSESSIONID", session).setComment("I am testing cookies")
                .setHttpOnly(true).setSecured(true).build();
        Cookie cookie2=new Cookie.Builder("Second Cookies","We love you").build();

        // Login
        String authSession =
                given()
                        .cookie(String.valueOf(cookieLogin),cookie2)
                        .contentType("application/x-www-form-urlencoded")
                        .formParam("txtUsername", "dan")
                        .formParam("txtPassword", "dan123")
                        .formParam("_csrf", csrf)
                        .when()
                        .post("/signin")
                        .getCookie("JSESSIONID");
        Cookie AccessCookie= new Cookie.Builder("JSESSIONID", authSession).setComment("I am using cookie to access profile")
                .setHttpOnly(true).build();

        // Access protected API
        given()
                .cookie(String.valueOf(AccessCookie),cookie2)
                .when().log().all()
                .get("/profile/index")
                .then().log().all()
                .statusCode(200).body("html.body.div.p",equalTo("This is User Profile\\Index. Only authenticated people can see this"));
    }
    @Test
    public void fetchingSingleCookies() {

       Response res= given()
                .when().log().all()
                .get("/profile/index")
                .then().log().all()
                .statusCode(200).extract().response();
        System.out.println("The cookie :"+res.getCookie("JSESSIONID"));
        System.out.println("The cookie :"+res.getDetailedCookie("JSESSIONID"));
    }
    @Test
    public void fetchingMultipleCookies() {

        Response res= given()
                .when().log().all()
                .get("/profile/index")
                .then().log().all()
                .statusCode(200).extract().response();
        Map<String,String>cookie=res.getCookies();
        for(Map.Entry<String,String>entry:cookie.entrySet()){
        System.out.println("The cookie key :"+ entry.getKey());
        System.out.println("The cookie value :"+entry.getValue());

    }
//        Cookies cookies3= res.getDetailedCookie();
        Cookies cookies3=res.detailedCookies();
        List<Cookie>cookies2List=cookies3.asList();
        for(Cookie cooki:cookies2List){
            System.out.println("cookie="+cooki.toString());
        }
}



}