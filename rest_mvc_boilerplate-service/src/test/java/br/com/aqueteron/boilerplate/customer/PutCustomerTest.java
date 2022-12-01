package br.com.aqueteron.boilerplate.customer;

import br.com.aqueteron.boilerplate.utils.AbstractApplicationTestStart;
import br.com.aqueteron.boilerplate.customer.api.CustomerApiSchema;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

class PutCustomerTest extends AbstractApplicationTestStart {

    @Test
    @Sql("/db/base_customers.sql")
    void shouldPutCustomerTest() {
        LocalDate thirtyYearsAgo = LocalDate.now().minusYears(30l);
        webTestClient()
                .put()
                .uri("/api/customers/1")
                .bodyValue(new CustomerApiSchema(1l, "Nome completo", thirtyYearsAgo))
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .json("{\"id\":1,\"nome\":\"Nome completo\",\"dataNascimento\":\"" + thirtyYearsAgo.format(DateTimeFormatter.ISO_DATE) + "\",\"idade\":30}");
    }

    @Test
    void shouldPutCustomerNotFoundTest() {
        LocalDate thirtyYearsAgo = LocalDate.now().minusYears(30l);
        webTestClient()
                .put()
                .uri("/api/customers/1")
                .bodyValue(new CustomerApiSchema(1l, "Nome completo", thirtyYearsAgo))
                .exchange()
                .expectStatus()
                .isNotFound();
    }

}
