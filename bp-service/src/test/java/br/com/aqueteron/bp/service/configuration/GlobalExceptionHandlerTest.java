package br.com.aqueteron.bp.service.configuration;

import br.com.aqueteron.bp.api.model.ErrorResponseApiSchema;
import br.com.aqueteron.bp.service.exception.BusinessException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GlobalExceptionHandlerTest {

    @InjectMocks
    private GlobalExceptionHandler globalExceptionHandler;

    @Mock
    private ErrorResponseMapper errorResponseMapper;

    @Test
    void shouldHandleBusinessException() {
        BusinessException businessException = ExceptionStub.createBusinessException();
        ErrorResponseApiSchema errorResponseApiSchema = new ErrorResponseApiSchema();
        errorResponseApiSchema.setStatus(businessException.getHttpStatus().value());
        errorResponseApiSchema.setDetail(businessException.getMessage());
        when(errorResponseMapper.toErrorResponseApiSchema(any())).thenReturn(errorResponseApiSchema);

        ResponseEntity<ErrorResponseApiSchema> responseEntity = this.globalExceptionHandler.handleException(businessException);

        assertNotNull(responseEntity);
        assertEquals(businessException.getHttpStatus().value(), responseEntity.getStatusCode().value());
        ErrorResponseApiSchema errorResponseApiSchemaResponse = responseEntity.getBody();
        assertNotNull(errorResponseApiSchemaResponse);
        assertEquals(errorResponseApiSchema, errorResponseApiSchemaResponse);
    }

    @Test
    void shouldHandleException() {
        Exception exception = ExceptionStub.createException();
        ErrorResponseApiSchema errorResponseApiSchema = new ErrorResponseApiSchema();
        errorResponseApiSchema.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        errorResponseApiSchema.setDetail(exception.getMessage());
        when(errorResponseMapper.toErrorResponseApiSchema(any())).thenReturn(errorResponseApiSchema);

        ResponseEntity<ErrorResponseApiSchema> responseEntity = this.globalExceptionHandler.handleException(exception);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        ErrorResponseApiSchema errorResponseApiSchemaResponse = responseEntity.getBody();
        assertNotNull(errorResponseApiSchemaResponse);
        assertEquals(errorResponseApiSchema, errorResponseApiSchemaResponse);
    }

    @Test
    void shouldHandleIllegalArgumentException() {
        IllegalArgumentException illegalArgumentException = ExceptionStub.createIllegalArgumentException();
        ErrorResponseApiSchema errorResponseApiSchema = new ErrorResponseApiSchema();
        errorResponseApiSchema.setStatus(HttpStatus.BAD_REQUEST.value());
        errorResponseApiSchema.setDetail(illegalArgumentException.getMessage());
        when(errorResponseMapper.toErrorResponseApiSchema(any())).thenReturn(errorResponseApiSchema);

        ResponseEntity<ErrorResponseApiSchema> responseEntity = this.globalExceptionHandler.handleException(illegalArgumentException);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        ErrorResponseApiSchema errorResponseApiSchemaResponse = responseEntity.getBody();
        assertNotNull(errorResponseApiSchemaResponse);
        assertEquals(errorResponseApiSchema, errorResponseApiSchemaResponse);
    }

}
