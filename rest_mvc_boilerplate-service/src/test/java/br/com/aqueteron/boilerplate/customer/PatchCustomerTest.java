package br.com.aqueteron.boilerplate.customer;

import br.com.aqueteron.boilerplate.utils.AbstractApplicationTestStart;
import br.com.aqueteron.boilerplate.api.CustomerApiSchema;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

class PatchCustomerTest extends AbstractApplicationTestStart {

    @Test
    @Sql("/db/base_customers.sql")
    void shouldPatchCustomerUpdatingNameTest() {
        LocalDate birthday = LocalDate.parse("2002-05-15", DateTimeFormatter.ISO_DATE);
        webTestClient()
                .patch()
                .uri("/api/cliente/1")
                .bodyValue(new CustomerApiSchema(1l, "Nome completo", null))
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .json("{\"id\":1,\"nome\":\"Nome completo\",\"dataNascimento\":\"2002-05-15\",\"idade\":" + Period.between(birthday, LocalDate.now()).getYears() + "}");

    }

    @Test
    void shouldPatchCustomerUpdatingNameNotFoundTest() {
        LocalDate birthday = LocalDate.parse("2002-05-15", DateTimeFormatter.ISO_DATE);
        webTestClient()
                .patch()
                .uri("/api/cliente/1")
                .bodyValue(new CustomerApiSchema(1l, "Nome completo", null))
                .exchange()
                .expectStatus()
                .isNotFound();

    }

    @Test
    @Sql("/db/base_customers.sql")
    void shouldPatchCustomerUpdatingBirthDateTest() {
        LocalDate thirtyYearsAgo = LocalDate.now().minusYears(30l);

        webTestClient()
                .patch()
                .uri("/api/cliente/1")
                .bodyValue(new CustomerApiSchema(1l, null, thirtyYearsAgo))
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .json("{\"id\":1,\"nome\":\"Marcela Cláudia Maitê Duarte\",\"dataNascimento\":\"" + thirtyYearsAgo.format(DateTimeFormatter.ISO_DATE) + "\",\"idade\":30}");
    }

    @Test
    void shouldPatchCustomerUpdatingBirthDateNotFoundTest() {
        LocalDate thirtyYearsAgo = LocalDate.now().minusYears(30l);

        webTestClient()
                .patch()
                .uri("/api/cliente/1")
                .bodyValue(new CustomerApiSchema(1l, null, thirtyYearsAgo))
                .exchange()
                .expectStatus()
                .isNotFound();
    }
}
