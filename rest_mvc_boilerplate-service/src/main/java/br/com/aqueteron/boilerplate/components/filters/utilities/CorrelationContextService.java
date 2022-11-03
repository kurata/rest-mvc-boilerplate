package br.com.aqueteron.boilerplate.components.filters.utilities;

import org.springframework.stereotype.Service;

@Service
public class CorrelationContextService {

    private String correlationId;

    public String getCorrelationId() {
        return this.correlationId;
    }

    public void setCorrelationId(String correlationId) {
        this.correlationId = correlationId;
    }
}
