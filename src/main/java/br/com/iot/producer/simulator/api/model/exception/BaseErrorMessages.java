package br.com.iot.producer.simulator.api.model.exception;

import org.apache.commons.lang3.ArrayUtils;

import java.text.MessageFormat;
import java.util.ResourceBundle;


public class BaseErrorMessages {

    public static final BaseErrorMessages GENERIC_ERROR = new BaseErrorMessages("generic");
    public static final BaseErrorMessages GENERIC_INVALID_PARAMETERS = new BaseErrorMessages("generic.invalid.parameters");
    public static final BaseErrorMessages GENERIC_NOT_FOUND = new BaseErrorMessages("generic.notFound");
    public static final BaseErrorMessages GENERIC_METHOD_NOT_ALLOWED = new BaseErrorMessages("generic.methodNotAllowed");
    public static final BaseErrorMessages GENERIC_UNAUTHENTICATED_EXCEPTION = new BaseErrorMessages("generic.unauthenticated");
    public static final BaseErrorMessages GENERIC_UNAUTHORIZED_EXCEPTION = new BaseErrorMessages("generic.unauthorized");

    private final String key;
    private String[] params;

    public BaseErrorMessages(String key) {
        this.key = key;
    }

    public BaseErrorMessages withParams(String... params) {
        this.params = ArrayUtils.clone(params);
        return this;
    }

    public String getMessage() {
        String message = ResourceBundle.getBundle("messages.ErrorResource").getString(key);
        if (params != null) {
            final MessageFormat fmt = new MessageFormat(message);
            message = fmt.format(params);
        }
        return message;
    }
}
