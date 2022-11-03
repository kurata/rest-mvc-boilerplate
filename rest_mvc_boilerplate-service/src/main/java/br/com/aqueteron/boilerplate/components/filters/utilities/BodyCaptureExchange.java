package br.com.aqueteron.boilerplate.components.filters.utilities;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.ServerWebExchangeDecorator;

public class BodyCaptureExchange extends ServerWebExchangeDecorator {

    private final RequestBodyCapture requestBodyCapture;

    private final ResponseBodyCapture responseBodyCapture;

    public BodyCaptureExchange(ServerWebExchange exchange) {
        super(exchange);
        this.requestBodyCapture = new RequestBodyCapture(exchange.getRequest());
        this.responseBodyCapture = new ResponseBodyCapture(exchange.getResponse());
    }

    @Override
    public ServerHttpRequest getRequest() {
        return this.requestBodyCapture;
    }

    @Override
    public ServerHttpResponse getResponse() {
        return this.responseBodyCapture;
    }

    public String getRequestBody() {
        return this.requestBodyCapture.getRequestBody();
    }

    public String getResponseBody() {
        return this.responseBodyCapture.getResponseBody();
    }
}
