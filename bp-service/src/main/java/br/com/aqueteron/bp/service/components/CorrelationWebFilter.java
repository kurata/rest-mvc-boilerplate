package br.com.aqueteron.bp.service.components;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.UUID;

@RequiredArgsConstructor
@Component
@Order(1)
@Slf4j
public class CorrelationWebFilter extends HttpFilter {

    private static final String CORRELATION_ID_LOG_VAR_NAME = "correlationId";

    private static final String CORRELATION_ID_HTTP_HEADER_KEY = "x-correlation-id";

    @Override
    public void doFilter(final HttpServletRequest request, final HttpServletResponse response, final FilterChain chain) throws IOException, ServletException {
        if (request.getServletPath().startsWith("/api")) {
            String correlationId = extractCorrelationIdFromHeader(request);
            addCorrelationIdInLog(correlationId);
            response.addHeader(CORRELATION_ID_HTTP_HEADER_KEY, correlationId);
        }
        chain.doFilter(request, response);
        MDC.remove(CORRELATION_ID_LOG_VAR_NAME);
    }

    private void addCorrelationIdInLog(final String correlationId) {
        MDC.put(CORRELATION_ID_LOG_VAR_NAME, correlationId);
        log.trace("Requested with x-correlation-id {}", correlationId);
    }

    private String extractCorrelationIdFromHeader(final HttpServletRequest request) {
        String headerValues = request.getHeader(CORRELATION_ID_HTTP_HEADER_KEY);
        if (headerValues == null || headerValues.isEmpty()) {
            return UUID.randomUUID().toString();
        }
        return headerValues;
    }
}
