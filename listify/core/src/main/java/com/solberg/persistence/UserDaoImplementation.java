package com.solberg.persistence;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import com.solberg.models.User;

public class UserDaoImplementation implements UserDao {
  // TODO: index email column for performance.
  private final NamedParameterJdbcTemplate jdbcTemplate;

  private static final Logger logger = LoggerFactory.getLogger(UserDaoImplementation.class);

  @Autowired
  ListDao listDao;

  public UserDaoImplementation(NamedParameterJdbcTemplate jdbcTemplate) {
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

  public User fetchUserLists(User user) {
    logger.debug("Fetching " + user + " lists.");
    user.setListifyLists(listDao.findListsByUserId(user.getId()));
    return user;
  }

}
