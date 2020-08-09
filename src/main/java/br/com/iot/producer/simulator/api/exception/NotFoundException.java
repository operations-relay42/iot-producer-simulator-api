package br.com.iot.producer.simulator.api.exception;

import br.com.iot.producer.simulator.api.model.exception.BaseErrorMessages;

public class NotFoundException extends BaseException {

    public NotFoundException() {
        super(BaseErrorMessages.GENERIC_NOT_FOUND);
    }
}
