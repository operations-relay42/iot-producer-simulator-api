package br.com.iot.producer.simulator.api.utils;

import br.com.iot.producer.simulator.api.model.constant.EventsConstant;
import com.github.javafaker.Faker;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static br.com.iot.producer.simulator.api.model.constant.EventsConstant.*;

public final class RandomUtils {

    private static final BigDecimal MIN_VALUE = new BigDecimal("25.00");
    private static final BigDecimal MAX_VALUE = new BigDecimal("100.00");
    private static final Faker faker = Faker.instance();

    private RandomUtils() { /* utility class */ }

    public static BigDecimal randomBigDecimal() {
        return MIN_VALUE.add(BigDecimal.valueOf(Math.random()).multiply(MAX_VALUE.subtract(MIN_VALUE))).setScale(4, RoundingMode.CEILING);
    }

    public static long randomInt() {
        return faker.number().numberBetween(MIN_CLUSTER_SIZE, MAX_CLUSTER_SIZE);
    }

    public static String randomName() {
        return faker.hacker().adjective();
    }
}
