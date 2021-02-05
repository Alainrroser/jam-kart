package ch.bbcag.jamkart.utils;

import java.util.Random;

public class RandomUtils {

    private static final Random RANDOM = new Random();

    public static float getRandomFloatBetween(float min, float max) {
        return min + RANDOM.nextFloat() * (max - min);
    }

    public static int getRandomIntBetween(int min, int max) {
        return min + RANDOM.nextInt(max + 1);
    }

    public static boolean getRandomBoolean() {
        return RANDOM.nextBoolean();
    }

}
