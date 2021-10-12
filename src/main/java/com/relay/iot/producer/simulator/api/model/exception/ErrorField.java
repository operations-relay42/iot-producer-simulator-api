package com.relay.iot.producer.simulator.api.model.exception;

import org.immutables.value.Value;

@Value.Immutable
public interface ErrorField {
    String getField();

    String getDescription();
}
