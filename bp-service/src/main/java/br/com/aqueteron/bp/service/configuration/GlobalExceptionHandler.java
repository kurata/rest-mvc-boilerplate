package br.com.aqueteron.bp.service.configuration;

import br.com.aqueteron.bp.api.model.ErrorResponseApiSchema;
import br.com.aqueteron.bp.service.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private final ErrorResponseMapper errorResponseMapper;

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponseApiSchema> handleException(final BusinessException businessException) {
        log.warn(businessException.getMessage(), businessException);
        return ResponseEntity
                .status(businessException.getHttpStatus())
                .body(buildErrorResponseApiSchema(businessException.getHttpStatus(), businessException));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponseApiSchema> handleException(final IllegalArgumentException illegalArgumentException) {
        log.warn(illegalArgumentException.getMessage());
        return ResponseEntity
                .badRequest()
                .body(buildErrorResponseApiSchema(HttpStatus.BAD_REQUEST, illegalArgumentException));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseApiSchema> handleException(final Exception exception) {
        log.warn(exception.getMessage(), exception);
        return ResponseEntity
                .internalServerError()
                .body(buildErrorResponseApiSchema(HttpStatus.INTERNAL_SERVER_ERROR, exception));
    }

    private ErrorResponseApiSchema buildErrorResponseApiSchema(final HttpStatus httpStatus, final Exception exception) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(httpStatus, exception.getMessage());
        problemDetail.setProperty("trace", ExceptionUtils.getStackTrace(exception));
        return this.errorResponseMapper.toErrorResponseApiSchema(problemDetail);
    }

}
