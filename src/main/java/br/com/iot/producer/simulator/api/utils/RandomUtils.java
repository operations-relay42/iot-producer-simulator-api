package br.com.iot.producer.simulator.api.utils;

import com.github.javafaker.Faker;

import java.math.BigDecimal;

public final class RandomUtils {

    private static final BigDecimal MIN_VALUE = new BigDecimal("25.00");
    private static final BigDecimal MAX_VALUE = new BigDecimal("100.00");
    private static final Faker faker = Faker.instance();

    private RandomUtils() { /* utility class */ }

    public static BigDecimal randomBigDecimal() {
        return MIN_VALUE.add(BigDecimal.valueOf(Math.random()).multiply(MAX_VALUE.subtract(MIN_VALUE)));
    }

    public static long randomInt() {
        return faker.number().randomNumber();
    }

    public static String randomName() {
        return faker.hacker().adjective();
    }
}
