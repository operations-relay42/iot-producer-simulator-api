package br.com.iot.producer.simulator.api.model.event;

import br.com.iot.producer.simulator.api.utils.RandomUtils;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

public class RandomSensorEvent extends SensorEvent {

    @Override
    public Long getTimestamp() {
        return Instant.now().toEpochMilli();
    }

    @Override
    public BigDecimal getValue() {
        return RandomUtils.randomBigDecimal();
    }
}
