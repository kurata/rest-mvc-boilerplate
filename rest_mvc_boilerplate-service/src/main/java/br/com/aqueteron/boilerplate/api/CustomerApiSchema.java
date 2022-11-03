package br.com.aqueteron.boilerplate.api;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;

@Data
public class CustomerApiSchema implements Serializable {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private final Long id;

    @JsonProperty("nome")
    @NotEmpty(message = "attribute.validation.not_empty.message")
    private final String fullName;

    @JsonProperty("dataNascimento")
    @NotEmpty(message = "attribute.validation.not_empty.message")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private final LocalDate birthDate;

    @JsonProperty(value = "idade", access = JsonProperty.Access.READ_ONLY)
    private final Integer age;

    public CustomerApiSchema() {
        this(null, null, null);
    }

    public CustomerApiSchema(Long id, String fullName, LocalDate birthDate) {
        this.id = id;
        this.fullName = fullName;
        this.birthDate = birthDate;
        if (birthDate != null) {
            this.age = Period.between(birthDate, LocalDate.now()).getYears();
        } else {
            this.age = null;
        }
    }
}
