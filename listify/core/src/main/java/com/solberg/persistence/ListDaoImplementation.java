package com.solberg.persistence;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.solberg.models.ListItem;
import com.solberg.models.ListifyList;
import com.solberg.models.User;

public class ListDaoImplementation implements ListDao {
  private final NamedParameterJdbcTemplate jdbcTemplate;

  private static final Logger logger = LoggerFactory.getLogger(ListDaoImplementation.class);

  public ListDaoImplementation(NamedParameterJdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;

  }

  public void saveList(ListifyList list) {
    if (list.getId() != null && listExists(list.getId())) {
      // Update existing list
      String updateQuery = "UPDATE listify_lists SET list_name = :list_name WHERE id = :id";
      SqlParameterSource updateParameters = new MapSqlParameterSource()
          .addValue("list_name", list.getName())
          .addValue("id", list.getId());
      jdbcTemplate.update(updateQuery, updateParameters);
    } else {
      // Insert new list and fetch ID
      String insertQuery = "INSERT INTO listify_lists (list_name, user_id) VALUES (:list_name, :user_id)";
      KeyHolder keyHolder = new GeneratedKeyHolder();
      jdbcTemplate.update(insertQuery, new MapSqlParameterSource()
          .addValue("list_name", list.getName())
          .addValue("user_id", list.getUser().getId()), keyHolder, new String[] { "ID" });
      list.setId(keyHolder.getKey().longValue());
    }

    // Handle list items
    list.getListItems().forEach(item -> {
      if (item.getId() == null || !listItemExists(item.getId())) {
        // Insert new list item
        String insertItemQuery = "INSERT INTO list_items (name, state, listify_list_id) VALUES (:name, :state, :list_id)";
        jdbcTemplate.update(insertItemQuery, new MapSqlParameterSource()
            .addValue("name", item.getName())
            .addValue("state", item.getState())
            .addValue("list_id", list.getId()));
      } else {
        // Update existing list item
        String updateItemQuery = "UPDATE list_items SET name = :name, state = :state WHERE id = :id";
        jdbcTemplate.update(updateItemQuery, new MapSqlParameterSource()
            .addValue("name", item.getName())
            .addValue("state", item.getState())
            .addValue("id", item.getId()));
      }
    });

    // Delete items no longer in the list only if itemIds is not empty
    List<Long> itemIds = list.getListItems().stream()
        .map(ListItem::getId)
        .filter(Objects::nonNull)
        .collect(Collectors.toList());

    if (!itemIds.isEmpty()) {
      String deleteQuery = "DELETE FROM list_items WHERE listify_list_id = :list_id AND id NOT IN (:item_ids)";
      jdbcTemplate.update(deleteQuery, new MapSqlParameterSource()
          .addValue("list_id", list.getId())
          .addValue("item_ids", itemIds));
    }
  }

  private boolean listItemExists(Long id) {
    String query = "SELECT COUNT(*) FROM list_items WHERE id = :id";
    SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("id", id);
    Integer count = jdbcTemplate.queryForObject(query, namedParameters, Integer.class);
    return count != null && count > 0;
  }

  private boolean listExists(Long id) {
    String query = "SELECT COUNT(*) FROM listify_lists WHERE id = :id";
    SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("id", id);
    Integer count = jdbcTemplate.queryForObject(query, namedParameters, Integer.class);
    return count != null && count > 0;
  }

  public ListifyList findListById(long id) {
    String listQuery = "SELECT ll.id AS list_id, ll.list_name AS list_name, " +
        "u.id AS user_id, u.name AS user_name, u.email AS user_email " +
        "FROM listify_lists ll " +
        "JOIN users u ON u.id = ll.user_id " +
        "WHERE ll.id = :id";
    SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("id", id);
    try {
      ListifyList list = jdbcTemplate.queryForObject(listQuery, namedParameters, new ListifyListRowMapper());
      if (list != null) {
        String listItemQuery = "SELECT id AS list_item_id, name AS list_item_name, state AS list_item_state " +
            "FROM list_items " +
            "WHERE listify_list_id = :list_id";
        SqlParameterSource itemParameters = new MapSqlParameterSource().addValue("list_id", id);
        List<ListItem> items = jdbcTemplate.query(listItemQuery, itemParameters, new ListItemRowMapper());
        list.setListItems(items);
      }
      return list;
    } catch (EmptyResultDataAccessException e) {
      return null;
    }
  }

  public List<ListifyList> findListsByUserId(Long userId) {
    String query = "SELECT ll.id AS list_id, ll.list_name AS list_name, " +
        "u.id AS user_id, u.name AS user_name, u.email AS user_email " +
        "FROM listify_lists ll " +
        "JOIN users u ON u.id = ll.user_id " +
        "WHERE u.id = :user_id";
    SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("user_id", userId);
    return jdbcTemplate.query(query, namedParameters, new ListifyListRowMapper());

  }

  private static final class ListifyListRowMapper implements RowMapper<ListifyList> {
    @Override
    public ListifyList mapRow(ResultSet rs, int rowNum) throws SQLException {
      ListifyList listifyList = new ListifyList();
      listifyList.setId(rs.getLong("list_id"));
      listifyList.setName(rs.getString("list_name"));

      User user = new User();
      user.setId(rs.getLong("user_id"));
      user.setName(rs.getString("user_name"));
      user.setEmail(rs.getString("user_email"));
      user.addListifyList(listifyList);

      listifyList.setUser(user);

      return listifyList;
    }
  }

  private static final class ListItemRowMapper implements RowMapper<ListItem> {
    @Override
    public ListItem mapRow(ResultSet rs, int rowNum) throws SQLException {
      ListItem listItem = new ListItem();
      listItem.setId(rs.getLong("list_item_id"));
      listItem.setName(rs.getString("list_item_name"));
      listItem.setState(rs.getBoolean("list_item_state"));
      return listItem;

    }
  }

}
