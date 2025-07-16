package br.com.aqueteron.boilerplate.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.datafaker.Faker;
import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SqlConfig(encoding = "UTF-8")
@Testcontainers
@ContextConfiguration(initializers = {PostgresContainer.Initializer.class})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public abstract class AbstractApplicationTestStart {

    @Container
    public static PostgresContainer POSTGRES_SQL_CONTAINER = PostgresContainer.getInstance();

    private MockMvc mockMvc;

    private JdbcTemplate jdbcTemplate;

    private ObjectMapper objectMapper;

    private Faker faker;

    protected MockMvc mockMvc() {
        return this.mockMvc;
    }

    protected ObjectMapper objectMapper() {
        return this.objectMapper;
    }

    protected Faker faker() {
        if (this.faker == null) {
            this.faker = new Faker();
        }
        return this.faker;
    }

    @AfterEach
    public void afterEach() {
        this.jdbcTemplate.execute("delete from bp_database.customer");
        this.jdbcTemplate.execute("alter sequence bp_database.customer_id_seq restart with 1");
    }

}
