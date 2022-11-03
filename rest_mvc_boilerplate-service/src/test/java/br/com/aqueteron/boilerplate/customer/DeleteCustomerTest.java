package br.com.aqueteron.boilerplate.customer;

import br.com.aqueteron.boilerplate.utils.AbstractApplicationTestStart;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;

class DeleteCustomerTest extends AbstractApplicationTestStart {

    @Test
    @Sql("/db/base_customers.sql")
    void shouldDeleteCustomerTest() {
        webTestClient()
                .delete()
                .uri("/api/cliente/1")
                .exchange()
                .expectStatus()
                .isOk();
    }

    @Test
    void shouldDeleteCustomerNotFoundTest() {
        webTestClient()
                .delete()
                .uri("/api/cliente/1")
                .exchange()
                .expectStatus()
                .isNotFound();
    }
}
