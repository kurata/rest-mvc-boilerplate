package br.com.aqueteron.bp.service.it;

import br.com.aqueteron.bp.service.utils.AbstractApplicationStartTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PutCustomerIT extends AbstractApplicationStartTest {

    @Test
    @Sql("/db/base_customers.sql")
    void shouldPutCustomerTest() throws Exception {
        LocalDate thirtyYearsAgo = LocalDate.now().minusYears(30l);

        mockMvc()
                .perform(
                        put("/api/v1/customers/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"id\":1,\"name\":\"Nome completo\",\"birth\":\"" + thirtyYearsAgo.format(DateTimeFormatter.ISO_DATE) + "\"}")
                )
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\":1,\"name\":\"Nome completo\",\"birth\":\"" + thirtyYearsAgo.format(DateTimeFormatter.ISO_DATE) + "\"}"));
    }

    @Test
    void shouldPutCustomerNotFoundTest() throws Exception {
        LocalDate thirtyYearsAgo = LocalDate.now().minusYears(30l);

        mockMvc()
                .perform(
                        put("/api/v1/customers/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"id\":1,\"name\":\"Nome completo\",\"birth\":\"" + thirtyYearsAgo.format(DateTimeFormatter.ISO_DATE) + "\"}")
                )
                .andExpect(status().isNotFound());
    }

}
