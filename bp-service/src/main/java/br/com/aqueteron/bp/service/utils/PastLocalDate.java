package br.com.aqueteron.bp.service.utils;

import jakarta.persistence.Embeddable;
import lombok.Builder;

import java.io.Serializable;
import java.time.LocalDate;

@Builder
@Embeddable
public class PastLocalDate implements Serializable {

    private final LocalDate value;

    public PastLocalDate(LocalDate value) {
        validate(value);
        this.value = value;
    }

    public PastLocalDate() {
        this(null);
    }

    public LocalDate value() {
        return value;
    }

    private void validate(final LocalDate value) {
        if (value != null && value.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException(String.format("Invalid PastLocalDate %s, must be before today." , value));
        }
    }
}
