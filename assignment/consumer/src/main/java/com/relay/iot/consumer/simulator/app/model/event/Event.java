package com.relay.iot.consumer.simulator.app.model.event;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

public interface Event {

    Long getId();

    BigDecimal getValue();

    OffsetDateTime getTimestamp();

    String getType();

    String getName();

//    @Nullable
    Long getClusterId();
}