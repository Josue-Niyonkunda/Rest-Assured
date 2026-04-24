package com.test.gmailSpecBuilder;

import io.restassured.path.json.JsonPath;

import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.io.InputStream;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import static com.test.gmailSpecBuilder.GmailSpecBuilderClass.getRequestSpecBuilder;

import static com.test.gmailSpecBuilder.GmailSpecBuilderClass.getResponseBuilder;
import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;


public class GmailAPIWithSpecBuilder {


        String messageId="19db90940bc3cfb6";



        @Test
        public void getUserProfile(){
            given(getRequestSpecBuilder()).basePath("/gmail/v1")
                    .when().get("/users/josueniyonkunda22@gmail.com/profile").then().spec(getResponseBuilder());
        }
        @Test
        public void sendingEmailSms(){
            String sms="From:josueniyonkunda22@gmail.com\n" +
                    "To:josueniyonkunda11@gmail.com\n" +
                    "Subject: Google OAuth2.0 Access Token\n" +
                    "\n" +

                    "Sending from Gmail API";
            String smsEncoded= Base64.getUrlEncoder().encodeToString(sms.getBytes());
            Map<String,String> payload=new HashMap<>();
            payload.put("raw",smsEncoded);
          Response postRes= GmailApis.post(payload);
          assertThat(postRes.path("labelIds[0]"),equalTo("SENT"));

        }
        @Test
        public void fetchEmail() {

          Response response=GmailApis.get("19db96ec96d65fb2");
           assertThat(response.path("snippet"),equalTo("Sending from Gmail API"));
        }

        @Test
        public void deleteEmail(){
           Response response= GmailApis.delete("19db96ec96d65fb2");
           assertThat(response.statusCode(),equalTo(204));
            System.out.println("Deleted successfully: " + "19db96ec96d65fb2");
        }

    }


