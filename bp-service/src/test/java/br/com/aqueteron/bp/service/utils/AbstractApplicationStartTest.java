package br.com.aqueteron.bp.service.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.datafaker.Faker;
import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@SqlConfig(encoding = "UTF-8")
@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public abstract class AbstractApplicationStartTest {

    private static final String DATABASE_NAME = "bp_database";

    @Container
    static PostgreSQLContainer postgreSQL = new PostgreSQLContainer("postgres:9.6.12").withDatabaseName(DATABASE_NAME);

    @DynamicPropertySource
    static void containersProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQL::getJdbcUrl);
        registry.add("spring.datasource.username", postgreSQL::getUsername);
        registry.add("spring.datasource.password", postgreSQL::getPassword);
    }

    @LocalServerPort
    private int port;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    private Faker faker = new Faker();

    @AfterEach
    public void afterEach() {
        this.jdbcTemplate.execute("delete from " + DATABASE_NAME + ".customer");
        this.jdbcTemplate.execute("alter sequence " + DATABASE_NAME + ".customer_id_seq restart with 1");
    }

    protected int port() {
        return this.port;
    }

    protected MockMvc mockMvc() {
        return this.mockMvc;
    }

    protected ObjectMapper objectMapper() {
        return this.objectMapper;
    }

    protected Faker faker() {
        return this.faker;
    }

}
