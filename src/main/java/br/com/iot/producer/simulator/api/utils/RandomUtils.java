package br.com.iot.producer.simulator.api.utils;

import com.github.javafaker.Faker;

import java.math.BigDecimal;
import java.util.Random;

public class RandomUtils {

    private static final BigDecimal MIN_VALUE = new BigDecimal("25.00");
    private static final BigDecimal MAX_VALUE = new BigDecimal("100.00");
    private static Faker faker;

    private RandomUtils() {
        // utility class
        faker = new Faker();
    }

    public static BigDecimal randomBigDecimal() {
        return MIN_VALUE.add(BigDecimal.valueOf(Math.random()).multiply(MAX_VALUE.subtract(MIN_VALUE)));
    }

    public static int randomInt() {
        return faker.number().numberBetween(0, 100);
    }

    public static String randomName() {
        return faker.hacker().adjective();
    }

}
