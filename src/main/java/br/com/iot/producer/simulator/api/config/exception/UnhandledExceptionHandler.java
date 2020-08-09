package br.com.iot.producer.simulator.api.config.exception;

import br.com.iot.producer.simulator.api.exception.BaseException;
import br.com.iot.producer.simulator.api.exception.UnauthenticatedException;
import br.com.iot.producer.simulator.api.model.exception.ErrorResponse;
import br.com.iot.producer.simulator.api.model.exception.ImmutableErrorResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.server.MethodNotAllowedException;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebExceptionHandler;
import reactor.core.publisher.Mono;
import reactor.util.annotation.NonNull;

import static br.com.iot.producer.simulator.api.model.exception.BaseErrorMessages.*;

/**
 * Any exception that happens before the process reaches a Controller class.
 * Ex: UnauthenticatedException
 */
@Component
@Order(-2)
public class UnhandledExceptionHandler implements WebExceptionHandler {

    private static final Logger LOG = LoggerFactory.getLogger(UnhandledExceptionHandler.class);

    private final ObjectMapper mapper;
    private final DataBufferFactory dataBufferFactory = new DefaultDataBufferFactory();

    @Autowired
    public UnhandledExceptionHandler(final ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @NonNull
    @Override
    public Mono<Void> handle(@NonNull final ServerWebExchange exchange, @NonNull final Throwable ex) {
        try {
            if (ex instanceof MethodNotAllowedException) {
                LOG.error("Exception: {}", ex.getLocalizedMessage());
                return handleMethodNotAllowedException(exchange, (MethodNotAllowedException) ex);
            } else if (ex instanceof UnauthenticatedException) {
                return handleUnauthenticatedException(exchange, ex);
            } else if (ex instanceof ResponseStatusException) {
                LOG.error("Exception: {}", ex.getLocalizedMessage());
                return handleResourceNotFoundException(exchange, (ResponseStatusException) ex);
            } else {
                LOG.error("Exception: ", ex);
                return handleGenericException(exchange);
            }
        } catch (final JsonProcessingException e) {
            LOG.error("=== Failed to map the exception at [" + exchange.getRequest().getPath().value() + "]", e);
            exchange.getResponse().setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
            return exchange.getResponse().setComplete();
        }
    }

    private Mono<Void> handleUnauthenticatedException(ServerWebExchange exchange, Throwable ex) throws JsonProcessingException {
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);

        final var errorResponse = ImmutableErrorResponse.builder()
                .code(ex.getClass().getSimpleName())
                .description(GENERIC_UNAUTHENTICATED_EXCEPTION.getMessage())
                .build();

        return Mono.from(writeResponse(exchange, errorResponse));
    }

    /** handle 405 error */
    private Mono<Void> handleMethodNotAllowedException(final ServerWebExchange exchange, final MethodNotAllowedException ex) throws JsonProcessingException {
        exchange.getResponse().setStatusCode(HttpStatus.METHOD_NOT_ALLOWED);
        exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);

        final var errorResponse = ImmutableErrorResponse.builder()
                .code(ex.getClass().getSimpleName())
                .description(GENERIC_METHOD_NOT_ALLOWED.withParams(ex.getHttpMethod()).getMessage())
                .build();

        return Mono.from(writeResponse(exchange, errorResponse));
    }

    /** handle 404 error */
    private Mono<Void> handleResourceNotFoundException(final ServerWebExchange exchange, final ResponseStatusException ex) throws JsonProcessingException {
        exchange.getResponse().setStatusCode(ex.getStatus());
        exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);

        final ErrorResponse errorResponse = ImmutableErrorResponse.builder()
                .code(ex.getClass().getSimpleName())
                .description(GENERIC_NOT_FOUND.getMessage())
                .build();

        return Mono.from(writeResponse(exchange, errorResponse));
    }

    /** handle any exception as a server error */
    private Mono<Void> handleGenericException(final ServerWebExchange exchange) throws JsonProcessingException {
        exchange.getResponse().setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
        exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);

        final ErrorResponse errorResponse = ImmutableErrorResponse.builder()
                .code(BaseException.class.getSimpleName())
                .description(GENERIC_ERROR.getMessage())
                .build();

        return Mono.from(writeResponse(exchange, errorResponse));
    }

    /** Write the given error response in the server response */
    private Mono<Void> writeResponse(final ServerWebExchange exchange, final ErrorResponse errorResponse) throws JsonProcessingException {
        final Mono<DataBuffer> body = Mono.just(dataBufferFactory.wrap(mapper.writeValueAsBytes(errorResponse)));
        return exchange.getResponse().writeWith(body);
    }

}
