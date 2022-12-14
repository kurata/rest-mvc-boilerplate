package br.com.aqueteron.boilerplate.utils;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.web.reactive.server.WebTestClient;
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

    @LocalServerPort
    private int port;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int port() {
        return this.port;
    }

    public MockMvc mockMvc() {
        return this.mockMvc;
    }

    @AfterEach
    public void afterEach() {
        this.jdbcTemplate.execute("delete from rest_mvc_boilerplate.customer");
        this.jdbcTemplate.execute("alter sequence rest_mvc_boilerplate.customer_id_seq restart with 1");
    }

}
