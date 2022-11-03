package br.com.aqueteron.boilerplate.exception;

import org.springframework.http.HttpStatus;

public class HttpBusinessException extends RuntimeException {

    private final String messageKey;

    private final HttpStatus httpStatus;

    public HttpBusinessException(final String message, final String messageKey, final HttpStatus httpStatus) {
        super(message);
        this.messageKey = messageKey;
        this.httpStatus = httpStatus;
    }

    public HttpBusinessException(final String message, final Throwable cause, final String messageKey, final HttpStatus httpStatus) {
        super(message, cause);
        this.messageKey = messageKey;
        this.httpStatus = httpStatus;
    }

    public String getMessageKey() {
        return messageKey;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
