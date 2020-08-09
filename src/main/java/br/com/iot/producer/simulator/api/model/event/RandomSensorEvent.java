package br.com.iot.producer.simulator.api.model.event;

import br.com.iot.producer.simulator.api.utils.RandomUtils;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

public class RandomSensorEvent implements SensorEvent {

    private final String type;
    private final String name;
    private final long id;

    public RandomSensorEvent(String type) {
        this.type = type;
        this.id = RandomUtils.randomInt();
        this.name = RandomUtils.randomName();
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public OffsetDateTime getTimestamp() {
        return OffsetDateTime.now(ZoneOffset.UTC);
    }

    @Override
    public BigDecimal getValue() {
        return RandomUtils.randomBigDecimal();
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return "RandomSensorEvent{" +
                "type='" + type + '\'' +
                ", name='" + name + '\'' +
                ", id=" + id +
                '}';
    }
}
