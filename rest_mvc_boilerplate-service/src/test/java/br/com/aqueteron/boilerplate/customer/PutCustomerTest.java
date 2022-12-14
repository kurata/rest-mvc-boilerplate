package br.com.aqueteron.boilerplate.customer;

import br.com.aqueteron.boilerplate.utils.AbstractApplicationTestStart;
import br.com.aqueteron.boilerplate.customer.api.CustomerApiSchema;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PutCustomerTest extends AbstractApplicationTestStart {

    @Test
    @Sql("/db/base_customers.sql")
    void shouldPutCustomerTest() throws Exception {
        LocalDate thirtyYearsAgo = LocalDate.now().minusYears(30l);

        mockMvc()
                .perform(
                        put("/api/customers/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"id\":1,\"nome\":\"Nome completo\",\"dataNascimento\":\"" + thirtyYearsAgo.format(DateTimeFormatter.ISO_DATE) + "\"}")
                )
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\":1,\"nome\":\"Nome completo\",\"dataNascimento\":\"" + thirtyYearsAgo.format(DateTimeFormatter.ISO_DATE) + "\",\"idade\":30}"));
    }

    @Test
    void shouldPutCustomerNotFoundTest() throws Exception {
        LocalDate thirtyYearsAgo = LocalDate.now().minusYears(30l);

        mockMvc()
                .perform(
                        put("/api/customers/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"id\":1,\"nome\":\"Nome completo\",\"dataNascimento\":\"" + thirtyYearsAgo.format(DateTimeFormatter.ISO_DATE) + "\"}")
                )
                .andExpect(status().isNotFound());
    }

}
