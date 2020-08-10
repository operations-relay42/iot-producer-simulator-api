package br.com.iot.producer.simulator.api.exception;

import br.com.iot.producer.simulator.api.model.exception.BaseErrorMessages;

public class BaseException extends Exception {

    public BaseException(BaseErrorMessages baseErrorMessages) {
        super(baseErrorMessages.getMessage());
    }

    public BaseException(BaseErrorMessages baseErrorMessages, Throwable cause) {
        super(baseErrorMessages.getMessage(), cause);
    }
}
