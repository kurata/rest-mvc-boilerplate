package br.com.aqueteron.bp.service.it;

import br.com.aqueteron.bp.service.utils.AbstractApplicationStartTest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class GetCustomerIT extends AbstractApplicationStartTest {

    @Test
    @Sql("/db/base_customers.sql")
    void shouldGetCustomerWithEmptyResultTest() throws Exception {
        LocalDate birthday = LocalDate.parse("2002-05-15", DateTimeFormatter.ISO_DATE);
        mockMvc()
                .perform(get("/api/v1/customers/1"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\":1,\"name\":\"Marcela Cláudia Maitê Duarte\",\"birth\":\"2002-05-15\"}"));
    }

    @Test
    void shouldGetCustomerNotFoundTest() throws Exception {
        mockMvc().perform(get("/api/v1/customers/1")).andExpect(status().isNotFound());
    }

}
