package br.com.iot.producer.simulator.api.config.exception;

import br.com.iot.producer.simulator.api.exception.BaseException;
import br.com.iot.producer.simulator.api.model.exception.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
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

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public ErrorResponse handleConstraintViolationException(ConstraintViolationException exception) {
        LOG.error("==== ConstraintViolationException -> {}", exception.getLocalizedMessage());
        final List<ErrorField> errorFields = exception
                .getConstraintViolations()
                .parallelStream()
                .map(fieldError -> ImmutableErrorField.builder().field(fieldError.getPropertyPath().toString()).description(fieldError.getMessage()).build())
                .collect(Collectors.toList());

        return ImmutableErrorResponse.builder()
                .code("ValidationError")
                .description(BaseErrorMessages.GENERIC_INVALID_PARAMETERS.getMessage())
                .fields(errorFields)
                .build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(WebExchangeBindException.class)
    public ErrorResponse handleWebExchangeBindException(WebExchangeBindException exception) {
        LOG.error("==== WebExchangeBindException -> {}", exception.getLocalizedMessage());
        final List<ErrorField> errorFields = exception
                .getFieldErrors()
                .parallelStream()
                .map(fieldError -> ImmutableErrorField.builder().field(fieldError.getField()).description(fieldError.getDefaultMessage()).build())
                .collect(Collectors.toList());

        return ImmutableErrorResponse.builder()
                .code("ValidationError")
                .description(BaseErrorMessages.GENERIC_INVALID_PARAMETERS.getMessage())
                .fields(errorFields)
                .build();
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(BaseException.class)
    public ErrorResponse handleGenericException(BaseException exception) {
        LOG.error("==== BaseException", exception);
        return ImmutableErrorResponse.builder().description(exception.getLocalizedMessage()).build();
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ErrorResponse handleGenericException(Exception exception) {
        LOG.error("=== Exception ", exception);
        return ImmutableErrorResponse.builder()
                .description(BaseErrorMessages.GENERIC_ERROR.getMessage())
                .build();
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(AccessDeniedException.class)
    public ErrorResponse handleAccessDeniedException(AccessDeniedException exception) {
        LOG.error("=== AccessDeniedException ", exception);
        return ImmutableErrorResponse.builder()
                .description(BaseErrorMessages.GENERIC_UNAUTHORIZED_EXCEPTION.getMessage())
                .code(AccessDeniedException.class.getSimpleName())
                .build();
    }
}
