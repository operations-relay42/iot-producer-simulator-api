package br.com.iot.producer.simulator.api.model.event;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Map;

public interface Event {

    Long getId();

    BigDecimal getValue();

    OffsetDateTime getTimestamp();

    String getType();

    String getName();

    Long getClusterId();
}
