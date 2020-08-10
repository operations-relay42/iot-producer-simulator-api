package br.com.iot.producer.simulator.api.utils;

import java.math.BigDecimal;

public final class RandomUtils {

    private RandomUtils() { /* utility class */ }

    public static BigDecimal randomBigDecimal() {
        return BigDecimal.valueOf(org.apache.commons.lang3.RandomUtils.nextDouble(25.00D, 100.00D));
    }

    public static long randomInt(int clusterSize) {
        return org.apache.commons.lang3.RandomUtils.nextInt(1, clusterSize);
    }

}
