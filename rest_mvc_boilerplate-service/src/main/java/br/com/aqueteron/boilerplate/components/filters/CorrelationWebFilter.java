package br.com.aqueteron.boilerplate.components.filters;

import br.com.aqueteron.boilerplate.components.ContextKeys;
import br.com.aqueteron.boilerplate.components.filters.utilities.CorrelationContextService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

@Component
@Order(1)
@Slf4j
public class CorrelationWebFilter implements WebFilter {

    private static final String CORRELATION_ID_LOG_VAR_NAME = "correlationId";

    private static final String CORRELATION_ID_HTTP_HEADER_KEY = "x-correlation-id";

    private final CorrelationContextService correlationContextService;

    @Autowired
    protected CorrelationWebFilter(CorrelationContextService correlationContextService) {
        this.correlationContextService = correlationContextService;
    }

    @Override
    public Mono<Void> filter(final ServerWebExchange serverWebExchange, final WebFilterChain webFilterChain) {
        String correlationId = getCorrelationIdFromHeader(serverWebExchange.getRequest());
        serverWebExchange.getResponse()
                .getHeaders()
                .add(CORRELATION_ID_HTTP_HEADER_KEY, correlationId);
        return webFilterChain.filter(serverWebExchange)
                .doFirst(() -> addCorrelationIdToLog(correlationId))
                .doAfterTerminate(() -> addCorrelationIdToLog(correlationId))
                .contextWrite(ctx -> ctx.put(ContextKeys.CORRELATION_ID, correlationId));
    }

    private void addCorrelationIdToLog(final String correlationId) {
        MDC.put(CORRELATION_ID_LOG_VAR_NAME, correlationId);
        log.trace("Requested with x-correlation-id {}", correlationId);
        this.correlationContextService.setCorrelationId(correlationId);
    }

    private String getCorrelationIdFromHeader(final ServerHttpRequest request) {
        List<String> headerValues = request.getHeaders().get(CORRELATION_ID_HTTP_HEADER_KEY);
        if (headerValues == null || headerValues.isEmpty()) {
            return UUID.randomUUID().toString();
        }
        return headerValues.get(0);
    }
}
