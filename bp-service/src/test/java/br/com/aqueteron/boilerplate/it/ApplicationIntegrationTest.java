package br.com.aqueteron.boilerplate.it;

import br.com.aqueteron.boilerplate.utils.PostgresContainer;
import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.SqlConfig;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootConfiguration
@SqlConfig(encoding = "UTF-8")
@Testcontainers
@ContextConfiguration(initializers = {PostgresContainer.Initializer.class})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ApplicationIntegrationTest {

    @Container
    public static PostgresContainer POSTGRES_SQL_CONTAINER = PostgresContainer.getInstance();

//    @Test
//    void contextLoad() {
//        //Test to test the context load
//    }

}
