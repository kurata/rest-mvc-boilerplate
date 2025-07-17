package br.com.aqueteron.bp.service.it;

import br.com.aqueteron.bp.service.utils.AbstractApplicationStartTest;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class GetCustomersIT extends AbstractApplicationStartTest {

    @Test
    void shouldGetCustomerWithEmptyResultTest() throws Exception {
        mockMvc()
                .perform(get("/api/v1/customers"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"result\":[],\"totalPages\":0,\"totalElements\":0}"));
    }

    @Test
    @Sql("/db/base_customers.sql")
    void shouldGetCustomerTest() throws Exception {
        mockMvc()
                .perform(get("/api/v1/customers"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"result\":[{\"id\":1,\"name\":\"Marcela Cláudia Maitê Duarte\",\"birth\":\"2002-05-15\"},{\"id\":2,\"name\":\"Leonardo Enzo Viana\",\"birth\":\"1995-01-08\"},{\"id\":3,\"name\":\"Victor Carlos Eduardo Cláudio Alves\",\"birth\":\"1981-05-21\"},{\"id\":4,\"name\":\"Nair Carla Sabrina Almada\",\"birth\":\"1979-04-13\"},{\"id\":5,\"name\":\"Betina Stella Moreira\",\"birth\":\"2004-06-04\"},{\"id\":6,\"name\":\"Luiza Carla Clarice Moura\",\"birth\":\"1992-05-11\"},{\"id\":7,\"name\":\"Antônia Josefa de Paula\",\"birth\":\"1993-05-19\"},{\"id\":8,\"name\":\"Geraldo Ian Raul das Neves\",\"birth\":\"1975-01-09\"},{\"id\":9,\"name\":\"Renato Gabriel da Conceição\",\"birth\":\"1982-03-21\"},{\"id\":10,\"name\":\"Breno César Assis\",\"birth\":\"1999-06-09\"}],\"totalPages\":1,\"totalElements\":10}"));
    }

}
