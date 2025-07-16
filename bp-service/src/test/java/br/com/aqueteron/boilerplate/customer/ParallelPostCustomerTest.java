//package br.com.aqueteron.boilerplate.customer;
//
//import br.com.aqueteron.boilerplate.customer.api.CustomerApiSchema;
//import br.com.aqueteron.boilerplate.utils.AbstractApplicationTestStart;
//import lombok.extern.slf4j.Slf4j;
//import net.datafaker.Faker;
//import org.junit.jupiter.api.Test;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.ResultActions;
//
//import java.time.LocalDate;
//import java.time.ZoneId;
//import java.time.format.DateTimeFormatter;
//import java.util.stream.IntStream;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@Slf4j
//class ParallelPostCustomerTest extends AbstractApplicationTestStart {
//
//    @Test
//    void shouldPostCustomerInParallelTest() throws Exception {
//        IntStream.range(0, 100).boxed().parallel().map(this::createCustomer).map(this::postCustomer).forEach(this::validateResult);
//
//        mockMvc()
//                .perform(get("/api/customers"))
//                .andExpect(status().isOk())
//                .andExpect(content().json("{\"totalPages\":10,\"totalElements\":100}"));
//    }
//
//    @Test
//    void shouldPostCustomerInParallelRunChallengeTest() throws Exception {
//        LocalDate thirtyYearsAgo = LocalDate.now().minusYears(30L);
//        IntStream.range(0, 10).boxed().parallel().map(this::createStaticCustomer).map(this::postCustomer).forEach(this::justExecute);
//
//        mockMvc()
//                .perform(get("/api/customers"))
//                .andExpect(status().isOk())
//                .andExpect(content().json("{\"result\":[{\"nome\":\"Static Customer\",\"dataNascimento\":\"" + thirtyYearsAgo.format(DateTimeFormatter.ISO_DATE) + "\",\"idade\":30}],\"totalPages\":1,\"totalElements\":1}"));
//    }
//
//    void justExecute(final ResultActions resultActions) {
//        resultActions.andReturn();
//    }
//
//    void validateResult(final ResultActions resultActions) {
//        try {
//            resultActions
//                    .andExpect(status().isCreated());
//        } catch (Exception e) {
//            log.error(e.getMessage(), e);
//            throw new RuntimeException(e);
//        }
//    }
//
//    CustomerApiSchema createStaticCustomer(final Integer countNumber) {
//        LocalDate thirtyYearsAgo = LocalDate.now().minusYears(30L);
//        return new CustomerApiSchema(
//                null,
//                "Static Customer",
//                thirtyYearsAgo
//        );
//    }
//
//    CustomerApiSchema createCustomer(final Integer countNumber) {
//        return new CustomerApiSchema(
//                null,
//                faker().name().fullName(),
//                LocalDate.ofInstant(faker().date().birthday().toInstant(), ZoneId.systemDefault())
//        );
//    }
//
//    ResultActions postCustomer(final CustomerApiSchema customer) {
//        try {
//            return mockMvc().perform(
//                    post("/api/customers")
//                            .contentType(MediaType.APPLICATION_JSON)
//                            .content(objectMapper().writeValueAsString(customer))
//            );
//        } catch (Exception e) {
//            log.error(e.getMessage(), e);
//            throw new RuntimeException(e);
//        }
//    }
//}
