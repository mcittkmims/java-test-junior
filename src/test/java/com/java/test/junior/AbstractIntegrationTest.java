package com.java.test.junior;

import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@DirtiesContext
@Testcontainers
public abstract class AbstractIntegrationTest {

    @Container
    protected static final GenericContainer<?> pgCronContainer = new GenericContainer<>("postgres-cron:local")
            .withExposedPorts(5432)
            .withEnv("POSTGRES_DB", "marketplace")
            .withEnv("POSTGRES_USER", "admin")
            .withEnv("POSTGRES_PASSWORD", "admin")
            .waitingFor(Wait.forListeningPort());


    @DynamicPropertySource
    static void configure(DynamicPropertyRegistry registry) {
        String host = pgCronContainer.getHost();
        Integer port = pgCronContainer.getMappedPort(5432);

        registry.add("spring.datasource.url", () -> String.format("jdbc:postgresql://%s:%d/marketplace", host, port));
        registry.add("spring.datasource.username", () -> "admin");
        registry.add("spring.datasource.password", () -> "admin");
    }
}