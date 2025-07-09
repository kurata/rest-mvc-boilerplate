package br.com.aqueteron.boilerplate.customer;

import br.com.aqueteron.boilerplate.utils.AbstractApplicationTestStart;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class GetCustomerTest extends AbstractApplicationTestStart {

    @Test
    @Sql("/db/base_customers.sql")
    void shouldGetCustomerWithEmptyResultTest() throws Exception {
        LocalDate birthday = LocalDate.parse("2002-05-15", DateTimeFormatter.ISO_DATE);
        mockMvc()
                .perform(get("/api/customers/1"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\":1,\"nome\":\"Marcela Cláudia Maitê Duarte\",\"dataNascimento\":\"2002-05-15\",\"idade\":" + Period.between(birthday, LocalDate.now()).getYears() + "}"));
    }

    @Test
    void shouldGetCustomerNotFoundTest() throws Exception {
        mockMvc().perform(get("/api/customers/1")).andExpect(status().isNotFound());
    }

}
