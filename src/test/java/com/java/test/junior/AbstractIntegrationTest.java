package com.java.test.junior;

import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@DirtiesContext
@Testcontainers
public abstract class AbstractIntegrationTest {

    @Container
    private static final PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:latest")
                    .withDatabaseName("marketplace")
                    .withUsername("admin")
                    .withPassword("admin");


    @DynamicPropertySource
    static void configure(DynamicPropertyRegistry registry) {
        String host = postgresContainer.getHost();
        Integer port = postgresContainer.getMappedPort(5432);

        registry.add("spring.datasource.url", () -> String.format("jdbc:postgresql://%s:%d/marketplace", host, port));
        registry.add("spring.datasource.username", () -> "admin");
        registry.add("spring.datasource.password", () -> "admin");
    }
}