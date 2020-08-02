package br.com.iot.producer.simulator.api.controller.events.request;

import br.com.iot.producer.simulator.api.model.EventType;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;


public class SensorEventRequest {

    private final Long id;

    @Positive(message = "The field \"total\" must be a positive.")
    @NotNull(message = "The field \"total\" is mandatory.")
    private final Integer total;

    @NotNull(message = "The field \"type\" is invalid.")
    private final EventType type;

    @Max(value = 60, message = "The field \"every\" must be between 0 and 60.")
    @Min(value = 1, message = "The field \"every\" must be between 0 and 60.")
    private final Integer every;

    @JsonCreator
    public SensorEventRequest(@JsonProperty("total") Integer total, @JsonProperty("type") EventType type, @JsonProperty("every") Integer every, @JsonProperty("id") Long id) {
        this.total = total;
        this.type = type;
        this.every = every == null ? 10 : every;
        this.id = id;
    }

    public Integer getTotal() {
        return total;
    }

    public EventType getType() {
        return type;
    }

    public Integer getEvery() { return every; }

    public Long getId() { return id; }

    @Override
    public String toString() {
        return "EventRequest{" +
                "total=" + total +
                ", type=" + type +
                ", every=" + every +
                '}';
    }
}
