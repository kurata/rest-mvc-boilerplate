package br.com.aqueteron.bp.service.utils;

import jakarta.persistence.Embeddable;
import lombok.Builder;

import java.io.Serializable;

@Builder
@Embeddable
public class SimpleTextAttribute implements Serializable {

    private final String value;

    public SimpleTextAttribute(final String value) {
        validate(value);
        this.value = value;
    }

    protected SimpleTextAttribute() {
        this(null);
    }

    public String value() {
        return this.value;
    }

    private void validate(final String value) {
        if (value != null && !value.matches("^[a-zA-Zà-úÀ-Ú'\\s.]+$")) {
            throw new IllegalArgumentException(String.format("String %s is invalid for SimpleTextAttribute.", value));
        }
    }

}
