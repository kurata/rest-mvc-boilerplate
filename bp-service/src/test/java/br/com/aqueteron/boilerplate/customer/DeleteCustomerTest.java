//package br.com.aqueteron.boilerplate.customer;
//
//import br.com.aqueteron.boilerplate.utils.AbstractApplicationTestStart;
//import org.junit.jupiter.api.Test;
//import org.springframework.test.context.jdbc.Sql;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//class DeleteCustomerTest extends AbstractApplicationTestStart {
//
//    @Test
//    @Sql("/db/base_customers.sql")
//    void shouldDeleteCustomerTest() throws Exception {
//        mockMvc().perform(delete("/api/customers/1")).andExpect(status().isNoContent());
//    }
//
//    @Test
//    void shouldDeleteCustomerNotFoundTest() throws Exception {
//        mockMvc().perform(delete("/api/customers/1")).andExpect(status().isNotFound());
//    }
//
//}
