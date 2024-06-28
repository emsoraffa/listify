package com.solberg.persistence;

import javax.sql.DataSource;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.solberg.models.User;

public class DataHandler {

  private final NamedParameterJdbcTemplate jdbcTemplate;

  public DataHandler(NamedParameterJdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;

  }

  public void registerUser(User user) {
    if (!userExistsByEmail(user.getEmail())) {
      String query = "INSERT INTO users (name, email) VALUES (:name, :email)";
      SqlParameterSource namedParameters = new MapSqlParameterSource()
          .addValue("name", user.getName())
          .addValue("email", user.getEmail());
      jdbcTemplate.update(query, namedParameters);
    }
  }

  private boolean userExistsByEmail(String email) {
    String query = "SELECT COUNT(*) FROM users WHERE email = :email";
    SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("email", email);
    Integer count = jdbcTemplate.queryForObject(query, namedParameters, Integer.class);
    return count != null && count > 0;
  }
}
