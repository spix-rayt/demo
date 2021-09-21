package com.example.demo.util;

import java.security.SecureRandom;
import java.util.Base64;

public class RandomTokenGenerator {

    private static final SecureRandom secureRandom = new SecureRandom();
    private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder();

    public static String generateNewToken(int size) {
        byte[] bytes = new byte[size];
        secureRandom.nextBytes(bytes);
        return base64Encoder.encodeToString(bytes);
    }
}
