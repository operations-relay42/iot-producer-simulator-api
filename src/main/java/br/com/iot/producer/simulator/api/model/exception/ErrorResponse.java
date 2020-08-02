package br.com.iot.producer.simulator.api.model.exception;

import java.util.Collections;
import java.util.List;

public class ErrorResponse {

    private final String code;
    private final String description;
    private final List<ErrorField> fields;

    public ErrorResponse(String code, String description, List<ErrorField> fields) {
        this.code = code;
        this.description = description;
        this.fields = fields;
    }

    public ErrorResponse(String description, List<ErrorField> fields) {
        this("GenericError", description, fields);
    }

    public ErrorResponse(String code, BaseErrorMessages errorMessages, List<ErrorField> fields) {
        this(code, errorMessages.getMessage(), fields);
    }

    public ErrorResponse(BaseErrorMessages errorMessages) {
        this("GenericError", errorMessages.getMessage(), Collections.emptyList());
    }

    public ErrorResponse(String description) {
        this("GenericError", description, Collections.emptyList());
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public List<ErrorField> getFields() {
        return fields;
    }
}
