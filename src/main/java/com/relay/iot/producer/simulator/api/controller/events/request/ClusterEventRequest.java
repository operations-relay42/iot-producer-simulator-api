package com.relay.iot.producer.simulator.api.controller.events.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.immutables.value.Value;
import org.springframework.lang.Nullable;

import javax.validation.constraints.*;

import static com.relay.iot.producer.simulator.api.model.constant.EventsConstant.*;

@Value.Immutable
@Value.Style(builder = "new")
@JsonDeserialize(builder = ImmutableClusterEventRequest.Builder.class)
public interface ClusterEventRequest {

    /*It needed nullable and notnull, otherwise it would throw java.lang.IllegalStateException
    and we cannot get the proper message.*/
    @Positive(message = "{invalid.request.total.positive}")
    @NotNull(message = "{mandatory.request.total}")
    @Nullable
    Integer getTotal();

    @NotNull(message = "{mandatory.request.type}")
    @Nullable
    String getType();

    @Max(value = MAX_HEART_BEAT, message = "{invalid.event.total.heartBeat}")
    @Min(value = MIN_HEART_BEAT, message = "{invalid.event.total.heartBeat}")
    @Value.Default
    default Integer getHeartBeat() {
        return DEFAULT_HEART_BEAT;
    }

    @Min(value = MIN_CLUSTER_SIZE, message = "{invalid.cluster.request.total.clusterSize}")
    @Max(value = MAX_CLUSTER_SIZE, message = "{invalid.cluster.request.total.clusterSize}")
    @Value.Default
    default Integer getClusterSize() {
        return DEFAULT_CLUSTER_SIZE;
    }

    @NotNull(message = "{mandatory.cluster.request.clusterId}")
    @Nullable
    Long getClusterId();

    @Nullable
    @NotBlank(message = "{mandatory.request.name}")
    String getName();

}
