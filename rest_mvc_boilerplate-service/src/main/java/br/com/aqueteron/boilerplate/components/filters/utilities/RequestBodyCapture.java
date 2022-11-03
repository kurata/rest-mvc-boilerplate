package br.com.aqueteron.boilerplate.components.filters.utilities;

import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import reactor.core.publisher.Flux;

import java.nio.charset.StandardCharsets;

public class RequestBodyCapture extends ServerHttpRequestDecorator {

    private final StringBuilder body = new StringBuilder();

    public RequestBodyCapture(final ServerHttpRequest delegate) {
        super(delegate);
    }

    @Override
    public Flux<DataBuffer> getBody() {
        return super.getBody().doOnNext(this::capture);
    }

    private void capture(DataBuffer buffer) {
        this.body.append(StandardCharsets.UTF_8.decode(buffer.asByteBuffer()));
    }

    public String getRequestBody() {
        return this.body.toString();
    }
}
