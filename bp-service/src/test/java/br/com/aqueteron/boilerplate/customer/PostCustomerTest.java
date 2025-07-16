//package br.com.aqueteron.boilerplate.customer;
//
//import br.com.aqueteron.boilerplate.utils.AbstractApplicationTestStart;
//import org.junit.jupiter.api.Test;
//import org.springframework.http.MediaType;
//
//import java.time.LocalDate;
//import java.time.format.DateTimeFormatter;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//class PostCustomerTest extends AbstractApplicationTestStart {
//
//    @Test
//    void shouldPostCustomerTest() throws Exception {
//        LocalDate thirtyYearsAgo = LocalDate.now().minusYears(30l);
//
//        mockMvc()
//                .perform(
//                        post("/api/customers")
//                                .contentType(MediaType.APPLICATION_JSON)
//                                .content("{\"id\":null,\"nome\":\"Nome completo\",\"dataNascimento\":\"" + thirtyYearsAgo.format(DateTimeFormatter.ISO_DATE) + "\"}")
//                )
//                .andExpect(status().isCreated())
//                .andExpect(content().json("{\"id\":1,\"nome\":\"Nome completo\",\"dataNascimento\":\"" + thirtyYearsAgo.format(DateTimeFormatter.ISO_DATE) + "\",\"idade\":30}"));
//    }
//
//}
