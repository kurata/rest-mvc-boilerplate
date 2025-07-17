package br.com.aqueteron.bp.service.it;

import br.com.aqueteron.bp.service.utils.AbstractApplicationStartTest;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class DeleteCustomerIT extends AbstractApplicationStartTest {

    @Test
    @Sql("/db/base_customers.sql")
    void shouldDeleteCustomerTest() throws Exception {
        mockMvc().perform(delete("/api/v1/customers/1")).andExpect(status().isNoContent());
    }

    @Test
    void shouldDeleteCustomerNotFoundTest() throws Exception {
        mockMvc().perform(delete("/api/v1/customers/1")).andExpect(status().isNotFound());
    }

}
