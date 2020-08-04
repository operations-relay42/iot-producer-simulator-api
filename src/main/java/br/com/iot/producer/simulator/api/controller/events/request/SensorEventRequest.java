package br.com.iot.producer.simulator.api.controller.events.request;

import br.com.iot.producer.simulator.api.utils.RandomUtils;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.StringUtils;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;


public class SensorEventRequest {

    private final Long id;
    private final String name;

    @Positive(message = "The field 'total' must be a positive.")
    @NotNull(message = "The field 'total' is mandatory.")
    private final Integer total;

    @NotNull(message = "The field 'type' is invalid.")
    private final String type;

    @Max(value = 60, message = "The field 'every' must be between 0 and 60.")
    @Min(value = 0, message = "The field 'every' must be between 0 and 60.")
    private final Integer every;

    @JsonCreator
    public SensorEventRequest(@JsonProperty("total") Integer total, @JsonProperty("type") String type, @JsonProperty("every") Integer every, @JsonProperty("id") Long id, @JsonProperty("name") String name) {
        this.total = total;
        this.type = type;
        this.every = every == null ? 10 : every;
        this.id = id == null ? RandomUtils.randomInt() : id;
        this.name = StringUtils.defaultString(name, RandomUtils.randomName());
    }

    public Integer getTotal() {
        return total;
    }

    public String getType() {
        return type;
    }

    public Integer getEvery() {
        return every;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "SensorEventRequest{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", total=" + total +
                ", type='" + type + '\'' +
                ", every=" + every +
                '}';
    }
}
