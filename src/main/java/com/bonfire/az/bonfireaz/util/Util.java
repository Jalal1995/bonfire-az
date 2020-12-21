package com.bonfire.az.bonfireaz.util;

import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Random;

@Service
public class Util {

    private final Random RANDOM = new SecureRandom();
    private final static String ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    public String generateUserId(int length) {
        return generateRandomString(length);
    }

    public String generateUserId() {
        return generateUserId(10);
    }

    private String generateRandomString(int length) {
        StringBuilder returnValue = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            returnValue.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())));
        }
        return new String(returnValue);
    }
}
