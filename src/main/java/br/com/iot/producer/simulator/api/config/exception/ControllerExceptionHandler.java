package br.com.iot.producer.simulator.api.config.exception;

import br.com.iot.producer.simulator.api.exception.BaseException;
import br.com.iot.producer.simulator.api.model.exception.BaseErrorMessages;
import br.com.iot.producer.simulator.api.model.exception.ErrorField;
import br.com.iot.producer.simulator.api.model.exception.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ControllerExceptionHandler {

    private static final Logger LOG = LoggerFactory.getLogger(ControllerExceptionHandler.class);

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(ConstraintViolationException.class)
    public ErrorResponse handleConstraintViolationException(ConstraintViolationException exception) {
        LOG.error("==== ConstraintViolationException -> {}", exception.getLocalizedMessage());
        final List<ErrorField> errorFields = exception
                .getConstraintViolations()
                .parallelStream()
                .map(fieldError -> new ErrorField(fieldError.getPropertyPath().toString(), fieldError.getMessage()))
                .collect(Collectors.toList());

        return new ErrorResponse("ValidationError", BaseErrorMessages.GENERIC_INVALID_PARAMETERS, errorFields);
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(WebExchangeBindException.class)
    public ErrorResponse handleWebExchangeBindException(WebExchangeBindException exception) {
        LOG.error("==== WebExchangeBindException -> {}", exception.getLocalizedMessage());
        final List<ErrorField> errorFields = exception
                .getFieldErrors()
                .parallelStream()
                .map(fieldError -> new ErrorField(fieldError.getField(), fieldError.getDefaultMessage()))
                .collect(Collectors.toList());

        return new ErrorResponse("ValidationError", BaseErrorMessages.GENERIC_INVALID_PARAMETERS, errorFields);
    }


    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(BaseException.class)
    public ErrorResponse handleGenericException(BaseException exception) {
        LOG.error("==== BaseException", exception);
        return new ErrorResponse(exception.getLocalizedMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ErrorResponse handleGenericException(Exception exception) {
        LOG.error("=== Exception ", exception);
        return new ErrorResponse(BaseErrorMessages.GENERIC_ERROR);
    }
}
