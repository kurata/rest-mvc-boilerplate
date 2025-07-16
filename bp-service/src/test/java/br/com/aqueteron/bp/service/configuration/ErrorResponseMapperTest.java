package br.com.aqueteron.bp.service.configuration;

import br.com.aqueteron.bp.api.model.ErrorResponseApiSchema;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ErrorResponseMapperTest {

    private final ErrorResponseMapper errorResponseMapper = new ErrorResponseMapperImpl();

    @Test
    void shouldToErrorResponseApiSchema() {
        ProblemDetail problemDetail = mock(ProblemDetail.class);
        when(problemDetail.getType()).thenReturn(URI.create("http://aqueteron.bp.service"));
        when(problemDetail.getTitle()).thenReturn("Error Response");
        when(problemDetail.getStatus()).thenReturn(HttpStatus.INTERNAL_SERVER_ERROR.value());
        when(problemDetail.getDetail()).thenReturn("Error detail");
        when(problemDetail.getInstance()).thenReturn(URI.create("http://aqueteron.bp.service"));
        Map<String, Object> properties = new HashMap<>();
        properties.put("propertyKey", "propertyValue");
        when(problemDetail.getProperties()).thenReturn(properties);

        ErrorResponseApiSchema errorResponseApiSchema = errorResponseMapper.toErrorResponseApiSchema(problemDetail);

        assertEquals(URI.create("http://aqueteron.bp.service"), errorResponseApiSchema.getType());
        assertEquals("Error Response", errorResponseApiSchema.getTitle());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), errorResponseApiSchema.getStatus());
        assertEquals("Error detail", errorResponseApiSchema.getDetail());
        assertEquals(URI.create("http://aqueteron.bp.service"), errorResponseApiSchema.getInstance());
        Map<String,String> propertiesResponse =  errorResponseApiSchema.getProperties();
        assertNotNull(propertiesResponse);
    }

}
