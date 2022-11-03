package br.com.aqueteron.boilerplate.exception;

import lombok.Data;
import org.springframework.http.HttpMethod;

import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
public class ErrorResponse implements Serializable {

    private final HttpMethod httpMethod;

    private final String path;

    private final String message;

    private final String messageKey;

    private final Set<String> details;

    private final String throwableClassName;

    private final String stackTrace;

    private final ZonedDateTime timestamp;

    public ErrorResponse(final HttpMethod method, final String path, final Throwable throwable) {
        this(method, path, throwable.getMessage(), null, throwable);
    }

    public ErrorResponse(final HttpMethod method, final String path, final String message, final String messageKey, final Throwable throwable) {
        this.httpMethod = method;
        this.path = path;
        this.message = message;
        this.messageKey = messageKey;
        this.details = new HashSet<>();
        this.throwableClassName = throwable.getClass().getSimpleName();
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        throwable.printStackTrace(printWriter);
        this.stackTrace = stringWriter.toString();
        this.timestamp = ZonedDateTime.now();
    }

    public void addDetail(final String detail) {
        this.details.add(detail);
    }
}
