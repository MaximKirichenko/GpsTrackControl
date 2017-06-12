package com.ksgagro.gps.utils;

import java.util.Random;

/**
 * Created by Maxim Kirichenko on 14.05.17.
 */
public class RandomUtils {
    public static String fileKey(){
        String characters = "qwertyuiopasdfghjklzxcvbnm1234567890";
        return generateString(new Random(), characters, 20);
    }
    private static String generateString(Random rng, String characters, int length)
    {
        char[] text = new char[length];
        for (int i = 0; i < length; i++)
        {
            text[i] = characters.charAt(rng.nextInt(characters.length()));
        }
        return new String(text);
    }
}
