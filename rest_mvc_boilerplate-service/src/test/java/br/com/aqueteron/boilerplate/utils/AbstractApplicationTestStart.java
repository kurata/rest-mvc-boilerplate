package br.com.aqueteron.boilerplate.utils;

import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SqlConfig(encoding = "UTF-8")
@Testcontainers
@ContextConfiguration(initializers = {PostgresContainer.Initializer.class})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public abstract class AbstractApplicationTestStart {

    @LocalServerPort
    private int port;

    @Container
    public static PostgresContainer POSTGRES_SQL_CONTAINER = PostgresContainer.getInstance();

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public WebTestClient webTestClient() {
        return webTestClient;
    }

    @AfterEach
    public void afterEach() {
        this.jdbcTemplate.execute("delete from rest_mvc_boilerplate.customer");
        this.jdbcTemplate.execute("alter sequence rest_mvc_boilerplate.customer_id_seq restart with 1");
    }

}
