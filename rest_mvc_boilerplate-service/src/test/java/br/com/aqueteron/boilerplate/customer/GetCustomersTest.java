package br.com.aqueteron.boilerplate.customer;

import br.com.aqueteron.boilerplate.utils.AbstractApplicationTestStart;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;

class GetCustomersTest extends AbstractApplicationTestStart {

    @Test
    void shouldGetCustomerWithEmptyResultTest() {
        webTestClient()
                .get()
                .uri("/api/cliente")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .json("{\"result\":[],\"totalPages\":0,\"totalElements\":0}");
    }

    @Test
    @Sql("/db/base_customers.sql")
    void shouldGetCustomerTest() {
        webTestClient()
                .get()
                .uri("/api/cliente")
                .exchange()
                .expectStatus()
                .isOk();
    }

}
