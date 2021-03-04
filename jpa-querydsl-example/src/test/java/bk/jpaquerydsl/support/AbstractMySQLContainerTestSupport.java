package bk.jpaquerydsl.support;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Map;

@Testcontainers
public abstract class AbstractMySQLContainerTestSupport {
    @Container
    static final MySQLContainer<?> MY_SQL_CONTAINER;

    static {
        MY_SQL_CONTAINER = new MySQLContainer<>("mysql:5.7.22");
        MY_SQL_CONTAINER.withDatabaseName("bk")
                        .withTmpFs(Map.of("/var/lib/mysql", "rw"))
                        .withCommand("--character-set-server=utf8mb4",
                                     "--disable-partition-engine-check",
                                     "--explicit_defaults_for_timestamp")
                        .start();
    }

    @DynamicPropertySource
    static void mysqlProperties(final DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url",
                     () -> MY_SQL_CONTAINER.getJdbcUrl() + "?zeroDateTimeBehavior=convertToNull");
        registry.add("spring.datasource.username", MY_SQL_CONTAINER::getUsername);
        registry.add("spring.datasource.password", MY_SQL_CONTAINER::getPassword);
    }
}
