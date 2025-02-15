package org.prgrms.kdt.repository;

import com.zaxxer.hikari.HikariDataSource;
import javax.sql.DataSource;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@DirtiesContext
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringJUnitConfig
public abstract class DatabaseIntegrationTest {

  @Container
  private static final MySQLContainer mysqlContainer = (MySQLContainer)
      new MySQLContainer("mysql:8.0.19")
          .withInitScript("schema.sql");

  @Configuration
  @ComponentScan(basePackages = "org.prgrms.kdt.repository")
  static class Config {

    @Bean
    public DataSource dataSource() {
      mysqlContainer.start();

      return DataSourceBuilder.create()
          .driverClassName(mysqlContainer.getDriverClassName())
          .url(mysqlContainer.getJdbcUrl())
          .username(mysqlContainer.getUsername())
          .password(mysqlContainer.getPassword())
          .type(HikariDataSource.class)
          .build();
    }

    @Bean
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate(DataSource dataSource) {
      return new NamedParameterJdbcTemplate(dataSource);
    }

    @Bean
    public CustomerRepository customerRepository(
        NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
      return new JdbcCustomerRepository(namedParameterJdbcTemplate);
    }
  }
}