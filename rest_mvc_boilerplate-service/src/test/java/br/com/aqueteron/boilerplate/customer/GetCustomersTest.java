package br.com.aqueteron.boilerplate.customer;

import br.com.aqueteron.boilerplate.utils.AbstractApplicationTestStart;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;
import java.time.Period;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class GetCustomersTest extends AbstractApplicationTestStart {

    @Test
    void shouldGetCustomerWithEmptyResultTest() throws Exception {
        mockMvc()
                .perform(get("/api/customers"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"result\":[],\"totalPages\":0,\"totalElements\":0}"));
    }

    @Test
    @Sql("/db/base_customers.sql")
    void shouldGetCustomerTest() throws Exception {
        mockMvc()
                .perform(get("/api/customers"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"result\":[],\"totalPages\":0,\"totalElements\":0}"));
    }

}
