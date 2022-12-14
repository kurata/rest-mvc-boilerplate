package br.com.aqueteron.boilerplate.components.filters;

import br.com.aqueteron.boilerplate.components.ContextKeys;
import br.com.aqueteron.boilerplate.components.filters.utilities.CorrelationContextService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Component
@Order(1)
@Slf4j
public class CorrelationWebFilter extends HttpFilter {

    private static final String CORRELATION_ID_LOG_VAR_NAME = "correlationId";

    private static final String CORRELATION_ID_HTTP_HEADER_KEY = "x-correlation-id";

    private final CorrelationContextService correlationContextService;

    @Autowired
    protected CorrelationWebFilter(CorrelationContextService correlationContextService) {
        this.correlationContextService = correlationContextService;
    }

    @Override
    public void doFilter(final HttpServletRequest request, final HttpServletResponse response, final FilterChain chain) throws IOException, ServletException {
        String correlationId = getCorrelationIdFromHeader(request);
        addCorrelationIdToLog(correlationId);
        response.addHeader(CORRELATION_ID_HTTP_HEADER_KEY, correlationId);
        chain.doFilter(request, response);
    }

    private void addCorrelationIdToLog(final String correlationId) {
        MDC.put(CORRELATION_ID_LOG_VAR_NAME, correlationId);
        log.trace("Requested with x-correlation-id {}", correlationId);
        this.correlationContextService.setCorrelationId(correlationId);
    }

    private String getCorrelationIdFromHeader(final HttpServletRequest request) {
        String headerValues = request.getHeader(CORRELATION_ID_HTTP_HEADER_KEY);
        if (headerValues == null || headerValues.isEmpty()) {
            return UUID.randomUUID().toString();
        }
        return headerValues;
    }
}
