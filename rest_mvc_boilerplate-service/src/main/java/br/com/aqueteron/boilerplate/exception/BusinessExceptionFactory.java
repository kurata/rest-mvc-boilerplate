package br.com.aqueteron.boilerplate.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
@Slf4j
public class BusinessExceptionFactory {

    private static final String UNABLE_LOAD_BUSINESS_EXCEPTION_MESSAGE = "Unable to load business exception message.";

    private static final String UNABLE_LOAD_BUSINESS_EXCEPTION_LOG_MESSAGE = "Unable to load business exception message for key {}.";

    private final MessageSource messageSource;

    @Autowired
    public BusinessExceptionFactory(final MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public HttpBusinessException build(final Throwable throwable) {
        return new HttpBusinessException(
                throwable.getMessage(),
                throwable,
                null,
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    public HttpBusinessException build(final Throwable throwable, final String messageKey, final Object... objects) {
        String message;
        try {
            message = this.messageSource.getMessage(messageKey, objects, Locale.getDefault());
        } catch (NoSuchMessageException e) {
            message = UNABLE_LOAD_BUSINESS_EXCEPTION_MESSAGE;
            log.warn(UNABLE_LOAD_BUSINESS_EXCEPTION_LOG_MESSAGE, messageKey);
        }
        return new HttpBusinessException(
                message,
                throwable,
                messageKey,
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    public HttpBusinessException build(final String messageKey, final Object... objects) {
        return build(HttpStatus.INTERNAL_SERVER_ERROR, messageKey, objects);
    }

    public HttpBusinessException build(final HttpStatus httpStatus, final String messageKey, final Object... objects) {
        String message;
        try {
            message = this.messageSource.getMessage(messageKey, objects, Locale.getDefault());
        } catch (NoSuchMessageException e) {
            message = UNABLE_LOAD_BUSINESS_EXCEPTION_MESSAGE;
            log.warn(UNABLE_LOAD_BUSINESS_EXCEPTION_LOG_MESSAGE, messageKey);
        }
        return new HttpBusinessException(
                message,
                messageKey,
                httpStatus
        );
    }
}
