package com.relay.iot.producer.simulator.api.model.event;

import com.relay.iot.producer.simulator.api.utils.RandomUtils;
import org.immutables.value.Value;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Value.Modifiable
public interface RandomEvent extends Event {

    @Override
    @Value.Default
    default OffsetDateTime getTimestamp() {
        return OffsetDateTime.now(ZoneOffset.UTC);
    }

    @Override
    @Value.Default
    default BigDecimal getValue() {
        return RandomUtils.randomBigDecimal();
    }

}
