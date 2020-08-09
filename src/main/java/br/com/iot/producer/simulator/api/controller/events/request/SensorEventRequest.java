package br.com.iot.producer.simulator.api.controller.events.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.immutables.value.Value;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;


@Value.Immutable
@Value.Style(builder = "new")
@JsonDeserialize(builder = ImmutableSensorEventRequest.Builder.class)
public interface SensorEventRequest {

    int DEFAULT_HEART_BEAT = 10;
    int DEFAULT_CLUSTER_SIZE = 1;

    @Positive(message = "The field 'total' must be a positive.")
    @NotNull(message = "The field 'total' is mandatory.")
    Integer getTotal();

    @NotNull(message = "The field 'type' is invalid.")
    String getType();

    @Max(value = 60, message = "The field 'heartBeat' must be between 1 and 60.")
    @Min(value = 1, message = "The field 'heartBeat' must be between 1 and 60.")
    @Value.Default
    default Integer getHeartBeat() {
        return DEFAULT_HEART_BEAT;
    }

    @Min(value = 1, message = "The field 'clusterSize' must be between 1 and 60.")
    @Value.Default
    default Integer getClusterSize() {
        return DEFAULT_CLUSTER_SIZE;
    }

}
