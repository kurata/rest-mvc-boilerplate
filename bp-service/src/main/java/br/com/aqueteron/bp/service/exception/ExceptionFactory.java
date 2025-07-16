package br.com.aqueteron.bp.service.exception;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Locale;

@RequiredArgsConstructor
@Component
@Slf4j
public class ExceptionFactory {

    private static final String UNABLE_LOAD_BUSINESS_EXCEPTION_MESSAGE = "Unable to load business exception message.";

    private static final String UNABLE_LOAD_BUSINESS_EXCEPTION_LOG_MESSAGE = "Unable to load business exception message for key {}.";

    private final MessageSource messageSource;

    public BusinessException build(final Throwable throwable) {
        return new BusinessException(
                HttpStatus.INTERNAL_SERVER_ERROR,
                throwable.getMessage(),
                null,
                throwable
        );
    }

    public BusinessException build(final Throwable throwable, final String messageKey, final Object... objects) {
        String message;
        try {
            message = this.messageSource.getMessage(messageKey, objects, Locale.getDefault());
        } catch (NoSuchMessageException e) {
            message = UNABLE_LOAD_BUSINESS_EXCEPTION_MESSAGE;
            log.warn(UNABLE_LOAD_BUSINESS_EXCEPTION_LOG_MESSAGE, messageKey);
        }
        return new BusinessException(
                HttpStatus.INTERNAL_SERVER_ERROR,
                message,
                messageKey,
                throwable
        );
    }

    public BusinessException build(final String messageKey, final Object... objects) {
        return build(HttpStatus.INTERNAL_SERVER_ERROR, messageKey, objects);
    }

    public BusinessException build(final HttpStatus httpStatus, final String messageKey, final Object... objects) {
        String message;
        try {
            message = this.messageSource.getMessage(messageKey, objects, Locale.getDefault());
        } catch (NoSuchMessageException e) {
            message = UNABLE_LOAD_BUSINESS_EXCEPTION_MESSAGE;
            log.warn(UNABLE_LOAD_BUSINESS_EXCEPTION_LOG_MESSAGE, messageKey);
        }
        return new BusinessException(
                httpStatus,
                message,
                messageKey
        );
    }
}
