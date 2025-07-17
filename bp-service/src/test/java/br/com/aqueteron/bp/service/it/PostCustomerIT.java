package br.com.aqueteron.bp.service.it;

import br.com.aqueteron.bp.service.utils.AbstractApplicationStartTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PostCustomerIT extends AbstractApplicationStartTest {

    @Test
    void shouldPostCustomerTest() throws Exception {
        LocalDate thirtyYearsAgo = LocalDate.now().minusYears(30l);

        mockMvc()
                .perform(
                        post("/api/v1/customers")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"id\":null,\"name\":\"Nome completo\",\"birth\":\"" + thirtyYearsAgo.format(DateTimeFormatter.ISO_DATE) + "\"}")
                )
                .andExpect(status().isCreated())
                .andExpect(content().json("{\"id\":1,\"name\":\"Nome completo\",\"birth\":\"" + thirtyYearsAgo.format(DateTimeFormatter.ISO_DATE) + "\"}"));
    }

}
