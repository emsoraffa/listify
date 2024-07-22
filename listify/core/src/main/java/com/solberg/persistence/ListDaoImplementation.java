package com.solberg.persistence;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import com.solberg.models.ListifyList;

public class ListDaoImplementation implements ListDao {
  private final NamedParameterJdbcTemplate jdbcTemplate;

  private static final Logger logger = LoggerFactory.getLogger(ListDaoImplementation.class);

  public ListDaoImplementation(NamedParameterJdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;

  }

  public void saveList(ListifyList list) {
    // TODO: save listitems
    if (list.getId() != null && listExists(list.getId())) {
      // Update existing list
      String updateQuery = "UPDATE listify_lists SET list_name = :list_name WHERE id = :id";
      SqlParameterSource updateParameters = new MapSqlParameterSource()
          .addValue("list_name", list.getListName())
          .addValue("id", list.getId());
      jdbcTemplate.update(updateQuery, updateParameters);
    } else {
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

  public ListifyList findListById(long id) {
    // TODO:add user field and listItems.
    String query = "SELECT * FROM listify_lists WHERE id = :id";
    SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("id", id);
    try {
      return jdbcTemplate.queryForObject(query, namedParameters, new BeanPropertyRowMapper<>(ListifyList.class));
    } catch (EmptyResultDataAccessException e) {
      return null;
    }
  }

}
