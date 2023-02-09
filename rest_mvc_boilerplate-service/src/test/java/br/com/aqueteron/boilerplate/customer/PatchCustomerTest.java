package br.com.aqueteron.boilerplate.customer;

import br.com.aqueteron.boilerplate.utils.AbstractApplicationTestStart;
import br.com.aqueteron.boilerplate.customer.api.CustomerApiSchema;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PatchCustomerTest extends AbstractApplicationTestStart {

    @Test
    @Sql("/db/base_customers.sql")
    void shouldPatchCustomerUpdatingNameTest() throws Exception {
        LocalDate birthday = LocalDate.parse("2002-05-15", DateTimeFormatter.ISO_DATE);
        mockMvc()
                .perform(
                        patch("/api/customers/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"id\":1,\"nome\":\"Nome completo\",\"dataNascimento\":null}")
                )
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\":1,\"nome\":\"Nome completo\",\"dataNascimento\":\"2002-05-15\",\"idade\":" + Period.between(birthday, LocalDate.now()).getYears() + "}"));

    }

    @Test
    void shouldPatchCustomerUpdatingNameNotFoundTest() throws Exception {
        mockMvc()
                .perform(
                        patch("/api/customers/1")
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
                        patch("/api/customers/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"id\":1,\"nome\":null,\"dataNascimento\":\"" + thirtyYearsAgo.format(DateTimeFormatter.ISO_DATE) + "\"}")
                )
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\":1,\"nome\":\"Marcela Cláudia Maitê Duarte\",\"dataNascimento\":\"" + thirtyYearsAgo.format(DateTimeFormatter.ISO_DATE) + "\",\"idade\":30}"));
    }

    @Test
    void shouldPatchCustomerUpdatingBirthDateNotFoundTest() throws Exception {
        LocalDate thirtyYearsAgo = LocalDate.now().minusYears(30l);
        mockMvc()
                .perform(
                        patch("/api/customers/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"id\":1,\"nome\":null,\"dataNascimento\":\"" + thirtyYearsAgo.format(DateTimeFormatter.ISO_DATE) + "\"}")
                )
                .andExpect(status().isNotFound());
    }

}
