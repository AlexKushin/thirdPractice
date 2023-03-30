package com.shpp.mentoring.okushin.task3;

import java.util.Random;

/**
 * class StringGenerator for generating random strings
 */
public class StringGenerator {
    private StringGenerator(){}

    public static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    static String randomString() {
        Random random = new Random();
        char[] text = new char[random.nextInt(15) + 1];
        for (int i = 0; i < text.length; i++) {
            text[i] = CHARACTERS.charAt(random.nextInt(CHARACTERS.length()));
        }
        return new String(text);
    }


}
