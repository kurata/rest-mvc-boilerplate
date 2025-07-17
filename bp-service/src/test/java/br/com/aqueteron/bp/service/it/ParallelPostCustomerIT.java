package br.com.aqueteron.bp.service.it;

import br.com.aqueteron.bp.api.model.CustomerApiSchema;
import br.com.aqueteron.bp.service.utils.AbstractApplicationStartTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.stream.IntStream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
class ParallelPostCustomerIT extends AbstractApplicationStartTest {

    @Test
    void shouldPostCustomerInParallelTest() throws Exception {
        IntStream.range(0, 100).boxed().parallel().map(this::createCustomer).map(this::postCustomer).forEach(this::validateResult);

        mockMvc()
                .perform(get("/api/v1/customers"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"totalPages\":2,\"totalElements\":100}"));
    }

    @Test
    void shouldPostCustomerInParallelRunChallengeTest() throws Exception {
        LocalDate thirtyYearsAgo = LocalDate.now().minusYears(30L);
        IntStream.range(0, 10).boxed().parallel().map(this::createStaticCustomer).map(this::postCustomer).forEach(this::justExecute);

        mockMvc()
                .perform(get("/api/v1/customers"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"result\":[{\"name\":\"Static Customer\",\"birth\":\"" + thirtyYearsAgo.format(DateTimeFormatter.ISO_DATE) + "\"}],\"totalPages\":1,\"totalElements\":1}"));
    }

    void justExecute(final ResultActions resultActions) {
        resultActions.andReturn();
    }

    void validateResult(final ResultActions resultActions) {
        try {
            resultActions
                    .andExpect(status().isCreated());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    CustomerApiSchema createStaticCustomer(final Integer countNumber) {
        LocalDate thirtyYearsAgo = LocalDate.now().minusYears(30L);
        CustomerApiSchema customerApiSchema = new CustomerApiSchema();
        customerApiSchema.setName("Static Customer");
        customerApiSchema.setBirth(thirtyYearsAgo);
        return customerApiSchema;
    }

    CustomerApiSchema createCustomer(final Integer countNumber) {
        CustomerApiSchema customerApiSchema = new CustomerApiSchema();
        customerApiSchema.setName(faker().name().fullName());
        customerApiSchema.setBirth(faker().timeAndDate().birthday());
        return customerApiSchema;
    }

    ResultActions postCustomer(final CustomerApiSchema customer) {
        try {
            return mockMvc().perform(
                    post("/api/v1/customers")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper().writeValueAsString(customer))
            );
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }
}
