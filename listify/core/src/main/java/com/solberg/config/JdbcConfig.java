package com.solberg.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.solberg.persistence.ListDao;
import com.solberg.persistence.ListDaoImplementation;
import com.solberg.persistence.UserDao;
import com.solberg.persistence.UserDaoImplementation;

@Configuration
public class JdbcConfig {

  @Autowired
  private DataSource dataSource; // Autowired to use the DataSource configured in properties

  @Bean
  public NamedParameterJdbcTemplate namedParameterJdbcTemplate() {
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
