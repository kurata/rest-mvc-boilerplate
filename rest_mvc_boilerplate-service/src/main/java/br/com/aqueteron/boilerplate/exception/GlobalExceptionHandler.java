package br.com.aqueteron.boilerplate.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Locale;

@Configuration
@Order(-2)
@Slf4j
public class GlobalExceptionHandler implements ErrorWebExceptionHandler {

    private static final String VALIDATION_ERROR_MESSAGE_KEY = "validation.error.message";

    private static final String UNABLE_LOAD_FIELD_ERROR_MESSAGE = "Unable to load field error message with key {}, to field {}.";

    private final ObjectMapper objectMapper;

    private final MessageSource messageSource;

    public GlobalExceptionHandler(final ObjectMapper objectMapper, final MessageSource messageSource) {
        this.objectMapper = objectMapper;
        this.messageSource = messageSource;
    }

    @Override
    public Mono<Void> handle(final ServerWebExchange serverWebExchange, final Throwable throwable) {
        log.warn(throwable.getMessage(), throwable);
        DataBuffer dataBuffer = null;
        DataBufferFactory bufferFactory = serverWebExchange.getResponse().bufferFactory();
        ServerHttpRequest serverHttpRequest = serverWebExchange.getRequest();
        ErrorResponse errorResponse = new ErrorResponse(
                serverHttpRequest.getMethod(),
                serverHttpRequest.getPath().toString(),
                throwable
        );
        serverWebExchange.getResponse().setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
        if (throwable instanceof HttpBusinessException) {
            HttpBusinessException httpBusinessException = (HttpBusinessException) throwable;
            errorResponse = new ErrorResponse(
                    serverHttpRequest.getMethod(),
                    serverHttpRequest.getPath().toString(),
                    httpBusinessException.getMessage(),
                    httpBusinessException.getMessageKey(),
                    httpBusinessException
            );
            serverWebExchange.getResponse().setStatusCode(httpBusinessException.getHttpStatus());
        }
        if (throwable instanceof WebExchangeBindException) {
            WebExchangeBindException webExchangeBindException = (WebExchangeBindException) throwable;
            errorResponse = new ErrorResponse(
                    serverHttpRequest.getMethod(),
                    serverHttpRequest.getPath().toString(),
                    this.messageSource.getMessage(VALIDATION_ERROR_MESSAGE_KEY, null, Locale.getDefault()),
                    VALIDATION_ERROR_MESSAGE_KEY,
                    webExchangeBindException
            );
            webExchangeBindException.getFieldErrors().stream()
                    .map(this::translateMessageKeyToMessage)
                    .forEach(errorResponse::addDetail);
            serverWebExchange.getResponse().setStatusCode(HttpStatus.BAD_REQUEST);
        }
        try {
            dataBuffer = bufferFactory.wrap(this.objectMapper.writeValueAsBytes(errorResponse));
            serverWebExchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);
        } catch (JsonProcessingException e) {
            dataBuffer = bufferFactory.wrap("Unable to load error body.".getBytes());
            serverWebExchange.getResponse().getHeaders().setContentType(MediaType.TEXT_PLAIN);
        }
        return serverWebExchange.getResponse().writeWith(Mono.just(dataBuffer));
    }

    private String translateMessageKeyToMessage(final FieldError fieldError) {
        String message;
        try {
            String defaultMessage = fieldError.getDefaultMessage();
            if( defaultMessage != null) {
                message = this.messageSource.getMessage(
                        defaultMessage,
                        List.of(fieldError.getField()).toArray(),
                        Locale.getDefault()
                );
            } else {
                message = "Unable to load field message error.";
            }
        } catch (NoSuchMessageException e) {
            message = fieldError.getDefaultMessage();
            log.warn(UNABLE_LOAD_FIELD_ERROR_MESSAGE, fieldError.getDefaultMessage(), fieldError.getField());
        }
        return message;
    }

}
