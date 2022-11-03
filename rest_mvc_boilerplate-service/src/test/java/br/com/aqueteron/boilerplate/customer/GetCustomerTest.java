package br.com.aqueteron.boilerplate.customer;

import br.com.aqueteron.boilerplate.utils.AbstractApplicationTestStart;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

class GetCustomerTest extends AbstractApplicationTestStart {

    @Test
    @Sql("/db/base_customers.sql")
    void shouldGetCustomerWithEmptyResultTest() {
        LocalDate birthday = LocalDate.parse("2002-05-15", DateTimeFormatter.ISO_DATE);
        webTestClient()
                .get()
                .uri("/api/cliente/1")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .json("{\"id\":1,\"nome\":\"Marcela Cláudia Maitê Duarte\",\"dataNascimento\":\"2002-05-15\",\"idade\":"+ Period.between(birthday, LocalDate.now()).getYears() + "}");
    }

    @Test
    void shouldGetCustomerNotFoundTest() {
        webTestClient()
                .get()
                .uri("/api/cliente/1")
                .exchange()
                .expectStatus()
                .isNotFound();
    }

}
