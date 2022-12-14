package br.com.aqueteron.boilerplate.exception;

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
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.Locale;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static final String VALIDATION_ERROR_MESSAGE_KEY = "validation.error.message";

    private static final String RESPONSE_STATUS_EXCEPTION_MESSAGE_KEY = "response_status.exception.message";

    private static final String UNABLE_LOAD_FIELD_ERROR_MESSAGE = "Unable to load field error message with key {}, to field {}.";

    private final ObjectMapper objectMapper;

    private final MessageSource messageSource;

    public GlobalExceptionHandler(final ObjectMapper objectMapper, final MessageSource messageSource) {
        this.objectMapper = objectMapper;
        this.messageSource = messageSource;
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleExceptions(final Exception exception, final WebRequest webRequest) {
        log.warn(exception.getMessage(), exception);
        ServletWebRequest servletWebRequest = ((ServletWebRequest) webRequest);
        return ResponseEntity.internalServerError().body(new ErrorResponse(
                servletWebRequest.getHttpMethod(),
                servletWebRequest.getContextPath(),
                exception
        ));

    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ErrorResponse> handleResponseStatusException(final ResponseStatusException responseStatusException, final WebRequest webRequest) {
        log.warn(responseStatusException.getMessage(), responseStatusException);
        ServletWebRequest servletWebRequest = ((ServletWebRequest) webRequest);
        return ResponseEntity.status(responseStatusException.getStatus()).body(new ErrorResponse(
                servletWebRequest.getHttpMethod(),
                servletWebRequest.getContextPath(),
                responseStatusException.getMessage(),
                RESPONSE_STATUS_EXCEPTION_MESSAGE_KEY,
                responseStatusException
        ));
    }

    @ExceptionHandler(HttpBusinessException.class)
    public ResponseEntity<ErrorResponse> handleHttpBusinessException(final HttpBusinessException httpBusinessException, final WebRequest webRequest) {
        log.warn(httpBusinessException.getMessage(), httpBusinessException);
        ServletWebRequest servletWebRequest = ((ServletWebRequest) webRequest);
        return ResponseEntity
                .status(httpBusinessException.getHttpStatus())
                .body(new ErrorResponse(
                        servletWebRequest.getHttpMethod(),
                        servletWebRequest.getContextPath(),
                        httpBusinessException.getMessage(),
                        httpBusinessException.getMessageKey(),
                        httpBusinessException
                ));
    }

    @ExceptionHandler(WebExchangeBindException.class)
    public ResponseEntity<ErrorResponse> handleWebExchangeBindException(final WebExchangeBindException webExchangeBindException, final WebRequest webRequest) {
        log.warn(webExchangeBindException.getMessage(), webExchangeBindException);
        ServletWebRequest servletWebRequest = ((ServletWebRequest) webRequest);
        ErrorResponse errorResponse = new ErrorResponse(
                servletWebRequest.getHttpMethod(),
                servletWebRequest.getContextPath(),
                this.messageSource.getMessage(VALIDATION_ERROR_MESSAGE_KEY, null, Locale.getDefault()),
                VALIDATION_ERROR_MESSAGE_KEY,
                webExchangeBindException
        );
        webExchangeBindException.getFieldErrors().stream()
                .map(this::translateMessageKeyToMessage)
                .forEach(errorResponse::addDetail);
        return ResponseEntity.badRequest().body(errorResponse);
    }

    private String translateMessageKeyToMessage(final FieldError fieldError) {
        String message;
        try {
            String defaultMessage = fieldError.getDefaultMessage();
            if (defaultMessage != null) {
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
