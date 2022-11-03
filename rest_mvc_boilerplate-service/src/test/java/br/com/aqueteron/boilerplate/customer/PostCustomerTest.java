package br.com.aqueteron.boilerplate.customer;

import br.com.aqueteron.boilerplate.utils.AbstractApplicationTestStart;
import br.com.aqueteron.boilerplate.api.CustomerApiSchema;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

class PostCustomerTest extends AbstractApplicationTestStart {

    @Test
    void shouldPostCustomerTest() {
        LocalDate thirtyYearsAgo = LocalDate.now().minusYears(30l);
        webTestClient()
                .post()
                .uri("/api/cliente")
                .bodyValue(new CustomerApiSchema(null, "Nome completo", thirtyYearsAgo))
                .exchange()
                .expectStatus()
                .isCreated()
                .expectBody()
                .json("{\"id\":1,\"nome\":\"Nome completo\",\"dataNascimento\":\"" + thirtyYearsAgo.format(DateTimeFormatter.ISO_DATE) + "\",\"idade\":30}");
    }

}
