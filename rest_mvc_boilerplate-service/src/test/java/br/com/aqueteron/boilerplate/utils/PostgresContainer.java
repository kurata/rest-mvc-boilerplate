package br.com.aqueteron.boilerplate.utils;

import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.containers.PostgreSQLContainer;

public class PostgresContainer extends PostgreSQLContainer<PostgresContainer> {

    private static PostgresContainer CONTAINER;

    public PostgresContainer() {
        super("postgres:latest");
    }

    public static PostgresContainer getInstance() {
        if (CONTAINER == null) {
            CONTAINER = new PostgresContainer()
                    .withDatabaseName("rest_mvc_boilerplate");
        }
        return CONTAINER;
    }


    public static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        public void initialize(final ConfigurableApplicationContext configurableApplicationContext) {
            PostgresContainer container = PostgresContainer.getInstance();
            System.out.println(container.getJdbcUrl());
            TestPropertyValues.of(
                    "spring.datasource.url=" + container.getJdbcUrl(),
                    "spring.datasource.username=" + container.getUsername(),
                    "spring.datasource.password=" + container.getPassword()
            ).applyTo(configurableApplicationContext.getEnvironment());
        }
    }

    @Override
    public void start() {
        super.start();
    }

    @Override
    public void stop() {
        //do nothing, JVM handles shut down
    }

}
