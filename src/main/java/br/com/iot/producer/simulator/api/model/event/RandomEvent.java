package br.com.iot.producer.simulator.api.model.event;

import br.com.iot.producer.simulator.api.utils.RandomUtils;
import org.immutables.value.Value;
import org.springframework.lang.Nullable;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Map;

@Value.Modifiable
public interface RandomEvent extends Event {

    @Override
    @Value.Default
    default Long getId() {
        return RandomUtils.randomInt();
    }

    @Override
    @Value.Default
    default String getName() {
        return RandomUtils.randomName();
    }

    @Override
    @Nullable
    Long getClusterId();

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

    @Override
    String getType();

}
