package br.com.iot.producer.simulator.api.utils;

import java.math.BigDecimal;
import java.util.Random;

public class RandomUtils {

    private static final BigDecimal MIN_VALUE = new BigDecimal("25.00");
    private static final BigDecimal MAX_VALUE = new BigDecimal("100.00");

    private RandomUtils() {
        // utility class
    }

    public static BigDecimal randomBigDecimal() {
        return MIN_VALUE.add(BigDecimal.valueOf(Math.random()).multiply(MAX_VALUE.subtract(MIN_VALUE)));
    }

    public static int randomInt() {
        return new Random().ints(0, (100 + 1)).findFirst().getAsInt();
    }

}
