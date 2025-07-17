package br.com.aqueteron.bp.service.it;

import br.com.aqueteron.bp.service.utils.AbstractApplicationStartTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PatchCustomerIT extends AbstractApplicationStartTest {

    @Test
    @Sql("/db/base_customers.sql")
    void shouldPatchCustomerUpdatingNameTest() throws Exception {
        LocalDate birthday = LocalDate.parse("2002-05-15", DateTimeFormatter.ISO_DATE);
        mockMvc()
                .perform(
                        patch("/api/v1/customers/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"id\":1,\"name\":\"Nome completo\",\"birth\":null}")
                )
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\":1,\"name\":\"Nome completo\",\"birth\":\"2002-05-15\"}"));

    }

    @Test
    void shouldPatchCustomerUpdatingNameNotFoundTest() throws Exception {
        mockMvc()
                .perform(
                        patch("/api/v1/customers/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"id\":1,\"nome\":\"Nome completo\",\"dataNascimento\":null}")
                )
                .andExpect(status().isNotFound());
    }

    @Test
    @Sql("/db/base_customers.sql")
    void shouldPatchCustomerUpdatingBirthDateTest() throws Exception {
        LocalDate thirtyYearsAgo = LocalDate.now().minusYears(30l);
        mockMvc()
                .perform(
                        patch("/api/v1/customers/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"id\":1,\"name\":null,\"birth\":\"" + thirtyYearsAgo.format(DateTimeFormatter.ISO_DATE) + "\"}")
                )
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\":1,\"name\":\"Marcela Cláudia Maitê Duarte\",\"birth\":\"" + thirtyYearsAgo.format(DateTimeFormatter.ISO_DATE) + "\"}"));
    }

    @Test
    void shouldPatchCustomerUpdatingBirthDateNotFoundTest() throws Exception {
        LocalDate thirtyYearsAgo = LocalDate.now().minusYears(30l);
        mockMvc()
                .perform(
                        patch("/api/v1/customers/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"id\":1,\"nome\":null,\"dataNascimento\":\"" + thirtyYearsAgo.format(DateTimeFormatter.ISO_DATE) + "\"}")
                )
                .andExpect(status().isNotFound());
    }

}
