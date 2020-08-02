package br.com.iot.producer.simulator.api.model.event;

import java.math.BigDecimal;

public class SensorEvent {

    protected Long id;
    protected BigDecimal value;
    protected Long timestamp;
    protected String type;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "SensorEvent{" +
                "id=" + id +
                ", value=" + value +
                ", timestamp=" + timestamp +
                ", type='" + type + '\'' +
                '}';
    }
}
