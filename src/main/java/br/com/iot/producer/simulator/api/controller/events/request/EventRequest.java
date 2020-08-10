package br.com.iot.producer.simulator.api.controller.events.request;

import br.com.iot.producer.simulator.api.model.constant.EventsConstant;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.immutables.value.Value;
import org.springframework.lang.Nullable;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.Map;

import static br.com.iot.producer.simulator.api.model.constant.EventsConstant.*;

@Value.Immutable
@Value.Style(builder = "new")
@JsonDeserialize(builder = ImmutableEventRequest.Builder.class)
public interface EventRequest {

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

    @Positive(message = "{invalid.event.request.total.id}")
    @NotNull(message = "{mandatory.event.request.id}")
    @Nullable
    Long getId();

    @Nullable
    String getName();

    @Nullable
    Long getClusterId();

}
