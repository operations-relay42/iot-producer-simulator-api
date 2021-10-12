package com.relay.iot.producer.simulator.api.model.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.immutables.value.Value;
import org.springframework.lang.Nullable;

import java.util.List;

@Value.Immutable
public interface ErrorResponse {
    @Value.Default
    default String getCode() {
        return "GenericError";
    }

    String getDescription();

    @Nullable
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    List<ErrorField> getFields();

}
