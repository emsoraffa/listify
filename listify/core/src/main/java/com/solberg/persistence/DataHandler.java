package com.solberg.persistence;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.solberg.models.ListifyList;
import com.solberg.models.User;

public class DataHandler {
  // TODO: consider separation of concerns, creating separate DaO classes.
  private final NamedParameterJdbcTemplate jdbcTemplate;

  private static final Logger logger = LoggerFactory.getLogger(DataHandler.class);

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

  public User findUserByEmail(String email) {
    String query = "SELECT * FROM users WHERE email = :email";
    SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("email", email);

    try {
      return jdbcTemplate.queryForObject(query, namedParameters, new BeanPropertyRowMapper<>(User.class));
    } catch (EmptyResultDataAccessException e) {
      return null;
    }
  }

  public ListifyList findListById(long id) {
    String query = "SELECT * FROM listify_lists WHERE id = :id";
    SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("id", id);

    try {
      return jdbcTemplate.queryForObject(query, namedParameters, new BeanPropertyRowMapper<>(ListifyList.class));
    } catch (EmptyResultDataAccessException e) {
      return null;
    }

  }

  public void saveList(ListifyList list) {
    if (list.getId() != null && listExists(list.getId())) {
      // Update existing list
      String updateQuery = "UPDATE listify_lists SET list_name = :list_name WHERE id = :id";
      SqlParameterSource updateParameters = new MapSqlParameterSource()
          .addValue("list_name", list.getListName())
          .addValue("id", list.getId());
      jdbcTemplate.update(updateQuery, updateParameters);
    } else {
      // Insert new list
      String insertQuery = "INSERT INTO listify_lists (list_name, user_id) VALUES (:list_name, :user_id)";
      SqlParameterSource insertParameters = new MapSqlParameterSource()
          .addValue("list_name", list.getListName())
          .addValue("user_id", list.getUser().getId());
      jdbcTemplate.update(insertQuery, insertParameters);
    }
  }

  private boolean listExists(Long id) {
    String query = "SELECT COUNT(*) FROM listify_lists WHERE id = :id";
    SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("id", id);
    Integer count = jdbcTemplate.queryForObject(query, namedParameters, Integer.class);
    return count != null && count > 0;
  }

  public ListifyList findListById(int id) {
    String query = "SELECT * FROM listify_lists WHERE id = :id";
    SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("id", id);
    try {
      return jdbcTemplate.queryForObject(query, namedParameters, new BeanPropertyRowMapper<>(ListifyList.class));
    } catch (EmptyResultDataAccessException e) {
      return null;
    }
  }
}
