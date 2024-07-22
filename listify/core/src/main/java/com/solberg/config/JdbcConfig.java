package com.solberg.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import com.solberg.persistence.ListDao;
import com.solberg.persistence.ListDaoImplementation;
import com.solberg.persistence.UserDao;
import com.solberg.persistence.UserDaoImplementation;

@Configuration
public class JdbcConfig {
  // TODO: change to production db
  @Bean
  public DataSource dataSource() {
    return new EmbeddedDatabaseBuilder()
        .setType(EmbeddedDatabaseType.H2)
        .addScript("classpath:jdbc/schema.sql")
        .build();
  }

  @Bean
  public NamedParameterJdbcTemplate namedParameterJdbcTemplate(DataSource dataSource) {
    return new NamedParameterJdbcTemplate(dataSource);
  }

  @Bean
  public UserDao userDao(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
    return new UserDaoImplementation(namedParameterJdbcTemplate);
  }

  @Bean
  public ListDao listDao(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
    return new ListDaoImplementation(namedParameterJdbcTemplate);
  }

}
