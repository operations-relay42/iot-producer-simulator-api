package br.com.iot.producer.simulator.api.controller.events.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.immutables.value.Value;
import org.springframework.lang.Nullable;

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

    /*It needed nullable and notnull, otherwise it would throw java.lang.IllegalStateException
    and we cannot get the proper message.*/
    @Positive(message = "{invalid.event.request.total.positive}")
    @NotNull(message = "{mandatory.event.request.total}")
    @Nullable
    Integer getTotal();

    @NotNull(message = "{mandatory.event.request.type}")
    @Nullable
    String getType();

    @Max(value = 60, message = "{invalid.event.request.total.heartBeat.max}")
    @Min(value = 1, message = "{invalid.event.request.total.heartBeat.min}")
    @Value.Default
    default Integer getHeartBeat() {
        return DEFAULT_HEART_BEAT;
    }

    @Min(value = 1, message = "The field 'clusterSize' start at 1")
    @Value.Default
    default Integer getClusterSize() {
        return DEFAULT_CLUSTER_SIZE;
    }

}
