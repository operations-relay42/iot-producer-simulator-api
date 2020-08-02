package br.com.iot.producer.simulator.api.model.exception;

public class ErrorField {

    private final String field;
    private final String description;

    public ErrorField(String field, String description) {
        this.field = field;
        this.description = description;
    }

    public String getField() {
        return field;
    }

    public String getDescription() {
        return description;
    }
}
