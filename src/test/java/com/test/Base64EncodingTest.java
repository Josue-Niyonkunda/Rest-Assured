package com.test;

import java.util.Base64;

public class Base64EncodingTest {

    public static void main (String[] args){
        String usernameColonPassword="myUsername:myPassword";
        String base64Encoded= Base64.getEncoder().encodeToString(usernameColonPassword.getBytes());
        System.out.println("Encoded = "+base64Encoded);
        byte[] decodedBytes=Base64.getDecoder().decode(base64Encoded);
        System.out.println("Decoder = "+ new String(decodedBytes));
    }
}
