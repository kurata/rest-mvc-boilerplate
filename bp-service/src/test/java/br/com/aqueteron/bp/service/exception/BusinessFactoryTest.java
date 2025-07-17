package br.com.aqueteron.bp.service.exception;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.http.HttpStatus;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@MockitoSettings
class BusinessFactoryTest {

    @InjectMocks
    private BusinessFactory businessFactory;

    @Mock
    private MessageSource messageSource;

    @Test
    void shouldBuild_withThrowable() {
        Throwable throwable = new Throwable("Throwable message");
        BusinessException businessException = this.businessFactory.build(throwable);

        assertNotNull(businessException);
        assertEquals(BusinessException.class, businessException.getClass());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, businessException.getHttpStatus());
        assertEquals(throwable.getMessage(), businessException.getMessage());
        assertNull(businessException.getMessageKey());
        assertEquals(throwable, businessException.getCause());
    }

    @Test
    void shouldBuild_withThrowableMessageKeyAndObject() {
        Throwable throwable = new Throwable("Throwable message");
        String messageKey = "messageKey";
        String object =  "object";
        String message = "message";
        when(this.messageSource.getMessage(eq(messageKey), any(), eq(Locale.getDefault()))).thenReturn(message);

        BusinessException businessException = this.businessFactory.build(throwable, messageKey, object);

        assertNotNull(businessException);
        assertEquals(BusinessException.class, businessException.getClass());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, businessException.getHttpStatus());
        assertEquals(message, businessException.getMessage());
        assertEquals(messageKey, businessException.getMessageKey());
        assertEquals(throwable, businessException.getCause());
    }

    @Test
    void shouldBuild_withThrowableMessageKeyAndObjectButMessageNotFound() {
        Throwable throwable = new Throwable("Throwable message");
        String messageKey = "messageKey";
        String object =  "object";
        String message = "Unable to load business exception message.";
        when(this.messageSource.getMessage(eq(messageKey), any(), eq(Locale.getDefault()))).thenThrow(NoSuchMessageException.class);

        BusinessException businessException = this.businessFactory.build(throwable, messageKey, object);

        assertNotNull(businessException);
        assertEquals(BusinessException.class, businessException.getClass());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, businessException.getHttpStatus());
        assertEquals(message, businessException.getMessage());
        assertEquals(messageKey, businessException.getMessageKey());
        assertEquals(throwable, businessException.getCause());
    }

    @Test
    void shouldBuild_withMessageKeyAndObject() {
        String messageKey = "messageKey";
        String object =  "object";
        String message = "message";
        when(this.messageSource.getMessage(eq(messageKey), any(), eq(Locale.getDefault()))).thenReturn(message);

        BusinessException businessException = this.businessFactory.build(messageKey, object);

        assertNotNull(businessException);
        assertEquals(BusinessException.class, businessException.getClass());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, businessException.getHttpStatus());
        assertEquals(message, businessException.getMessage());
        assertEquals(messageKey, businessException.getMessageKey());
        assertNull(businessException.getCause());

    }

    @Test
    void shouldBuild_withHttpStatusMessageKeyAndObject() {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        String messageKey = "messageKey";
        String object =  "object";
        String message = "message";
        when(this.messageSource.getMessage(eq(messageKey), any(), eq(Locale.getDefault()))).thenReturn(message);

        BusinessException businessException = this.businessFactory.build(httpStatus, messageKey, object);

        assertNotNull(businessException);
        assertEquals(BusinessException.class, businessException.getClass());
        assertEquals(httpStatus, businessException.getHttpStatus());
        assertEquals(message, businessException.getMessage());
        assertEquals(messageKey, businessException.getMessageKey());
        assertNull(businessException.getCause());
    }

    @Test
    void shouldBuild_withHttpStatusMessageKeyAndObjectButMessageNotFound() {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        String messageKey = "messageKey";
        String object =  "object";
        String message = "Unable to load business exception message.";
        when(this.messageSource.getMessage(eq(messageKey), any(), eq(Locale.getDefault()))).thenThrow(NoSuchMessageException.class);

        BusinessException businessException = this.businessFactory.build(httpStatus, messageKey, object);

        assertNotNull(businessException);
        assertEquals(BusinessException.class, businessException.getClass());
        assertEquals(httpStatus, businessException.getHttpStatus());
        assertEquals(message, businessException.getMessage());
        assertEquals(messageKey, businessException.getMessageKey());
        assertNull(businessException.getCause());
    }
}
