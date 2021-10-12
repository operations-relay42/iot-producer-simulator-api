package com.relay.iot.producer.simulator.api.exception;

import com.relay.iot.producer.simulator.api.model.exception.BaseErrorMessages;

public class UnauthorizedException extends BaseException {

    public UnauthorizedException(Throwable cause) {
        super(BaseErrorMessages.GENERIC_UNAUTHORIZED_EXCEPTION, cause);
    }
}
