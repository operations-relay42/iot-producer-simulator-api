package br.com.iot.producer.simulator.api.model.event;

import br.com.iot.producer.simulator.api.model.EventType;
import br.com.iot.producer.simulator.api.utils.RandomUtils;

import java.time.OffsetDateTime;

public class RandomSensorEvent extends SensorEvent {

    public RandomSensorEvent(Long id, EventType eventType) {
        this.id = id == null ? RandomUtils.randomInt() : id;
        this.timestamp = OffsetDateTime.now().toEpochSecond();
        this.value = RandomUtils.randomBigDecimal();
        this.type = eventType.name();
    }
}
