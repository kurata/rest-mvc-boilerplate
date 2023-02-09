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
                .andExpect(content().json("{\"result\":[{\"id\":1,\"nome\":\"Marcela Cláudia Maitê Duarte\",\"dataNascimento\":\"2002-05-15\",\"idade\":20},{\"id\":2,\"nome\":\"Leonardo Enzo Viana\",\"dataNascimento\":\"1995-01-08\",\"idade\":28},{\"id\":3,\"nome\":\"Victor Carlos Eduardo Cláudio Alves\",\"dataNascimento\":\"1981-05-21\",\"idade\":41},{\"id\":4,\"nome\":\"Nair Carla Sabrina Almada\",\"dataNascimento\":\"1979-04-13\",\"idade\":43},{\"id\":5,\"nome\":\"Betina Stella Moreira\",\"dataNascimento\":\"2004-06-04\",\"idade\":18},{\"id\":6,\"nome\":\"Luiza Carla Clarice Moura\",\"dataNascimento\":\"1992-05-11\",\"idade\":30},{\"id\":7,\"nome\":\"Antônia Josefa de Paula\",\"dataNascimento\":\"1993-05-19\",\"idade\":29},{\"id\":8,\"nome\":\"Geraldo Ian Raul das Neves\",\"dataNascimento\":\"1975-01-09\",\"idade\":48},{\"id\":9,\"nome\":\"Renato Gabriel da Conceição\",\"dataNascimento\":\"1982-03-21\",\"idade\":40},{\"id\":10,\"nome\":\"Breno César Assis\",\"dataNascimento\":\"1999-06-09\",\"idade\":23}],\"totalPages\":1,\"totalElements\":10}"));
    }

}
