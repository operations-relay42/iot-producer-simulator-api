package com.relay.iot.consumer.test.integration;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import com.relay.iot.consumer.simulator.app.model.event.Event;

public class EventSub implements Event {

    @Override
    public Long getId() {
        return 12345L;
    }

    @Override
    public BigDecimal getValue() {
        return BigDecimal.TEN;
    }

    @Override
    public OffsetDateTime getTimestamp() {
        return OffsetDateTime.of(2020, 10, 10, 3, 40, 1, 1, ZoneOffset.UTC);
    }

    @Override
    public String getType() {
        return "TEMPERATURE";
    }

    @Override
    public String getName() {
        return "Stub sensor";
    }

    @Override
    public Long getClusterId() {
        return null;
    }


}
