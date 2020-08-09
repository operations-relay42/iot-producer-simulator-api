package br.com.iot.producer.simulator.api.model.event;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public interface SensorEvent {

    Long getId();

    BigDecimal getValue();

    OffsetDateTime getTimestamp();

    String getType();

    String getName();
}
