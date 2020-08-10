package br.com.iot.producer.simulator.api.model.event;

import org.springframework.lang.Nullable;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public interface Event {

    Long getId();

    BigDecimal getValue();

    OffsetDateTime getTimestamp();

    String getType();

    String getName();

    @Nullable
    Long getClusterId();
}
