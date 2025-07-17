package br.com.aqueteron.bp.service.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BusinessException extends RuntimeException {

    private final String messageKey;

    private final HttpStatus httpStatus;

    public BusinessException(final HttpStatus httpStatus, final String message, final String messageKey) {
        this(httpStatus, message, messageKey, null);
    }

    public BusinessException(final HttpStatus httpStatus, final String message, final String messageKey, final Throwable cause) {
        super(message, cause);
        this.httpStatus = httpStatus;
        this.messageKey = messageKey;
    }

}
