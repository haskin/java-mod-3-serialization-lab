package util;

import java.util.Random;

public class RandomUtil {
    private static final String[] NAMES = {"David", "Stewart", "Sam", "Anderson", "Valeria", "Elliott", "Jacob", "Allen", "Evelyn", "Cameron", "Audrey", "Ellis", "Dexter", "Wells", "Maya", "Ellis", "Edith", "Gray", "Dale", "Baker", "Sophia", "Morgan", "Kimberly", "Gibson", "Victoria", "Douglas", "Frederick", "Evans", "Valeria", "Russell"};

    public static final String getRandomName() {
        Random random = new Random();
        return NAMES[random.nextInt(NAMES.length)];
    }

    public static final int getRandomYear() {
        Random random = new Random();
        return 1920 + random.nextInt(123); // 1920 - 2022
    }

    public static final int getRandomMonth() {
        Random random = new Random();
        return random.nextInt(13);
    }

    public static final int getRandomDay() {
        Random random = new Random();
        return random.nextInt(32);
    }
}
