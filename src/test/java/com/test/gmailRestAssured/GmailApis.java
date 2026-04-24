package com.test.gmailRestAssured;

import com.test.gmailRestAssuredResource.GmailApisResource;
import io.restassured.response.Response;

import java.util.Map;

import static com.test.gmailRestAssuredResource.Routes.*;


public class GmailApis {


    public static Response post(Map<String, String> payload) {
        return GmailApisResource.post(payload,"josueniyonkunda22@gmail.com");

    }
    public static Response get(String messageId){
        return GmailApisResource.get(messageId,BASE_PART,"josueniyonkunda22@gmail.com");

    }
    public static Response delete(String messageId){
        return GmailApisResource.delete(messageId,BASE_PART,USERS+"/me"+MESSAGES+"/{id}");

    }
}
