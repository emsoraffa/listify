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
      // Create new list
      String insertQuery = "INSERT INTO listify_lists (list_name, user_id) VALUES (:list_name, :user_id)";
      SqlParameterSource insertParameters = new MapSqlParameterSource()
          .addValue("list_name", list.getName())
          .addValue("user_id", list.getUser().getId());
      jdbcTemplate.update(insertQuery, insertParameters);

      // Set the generated ID back to the list
      Long generatedId = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", new MapSqlParameterSource(),
          Long.class);
      list.setId(generatedId);
    }

    // Upsert list items
    for (ListItem item : list.getListItems()) {
      if (item.getId() == null || !listItemExists(item.getId())) {
        logger.debug("Inserting new item " + item.getName());
        // Insert new list item
        String insertItemQuery = "INSERT INTO list_items (name, state, listify_list_id) VALUES (:name, :state, :list_id)";
        SqlParameterSource itemParameters = new MapSqlParameterSource()
            .addValue("name", item.getName())
            .addValue("state", item.getState())
            .addValue("list_id", list.getId());
        jdbcTemplate.update(insertItemQuery, itemParameters);
      } else {
        logger.debug("Updating existing item:" + item.getName());
        // Update existing list item
        String updateItemQuery = "UPDATE list_items SET name = :name, state = :state WHERE id = :id";
        SqlParameterSource itemParameters = new MapSqlParameterSource()
            .addValue("name", item.getName())
            .addValue("state", item.getState())
            .addValue("id", item.getId());
        jdbcTemplate.update(updateItemQuery, itemParameters);
      }
    }

    // Delete items that are no longer in the list
    List<Long> itemIds = list.getListItems().stream()
        .map(ListItem::getId)
        .filter(Objects::nonNull)
        .collect(Collectors.toList());

    if (!itemIds.isEmpty()) {
      String deleteQuery = "DELETE FROM list_items WHERE listify_list_id = :list_id AND id NOT IN (:item_ids)";
      SqlParameterSource deleteParameters = new MapSqlParameterSource()
          .addValue("list_id", list.getId())
          .addValue("item_ids", itemIds);
      jdbcTemplate.update(deleteQuery, deleteParameters);
    } else {
      // If no items, delete all items associated with the list
      String deleteQuery = "DELETE FROM list_items WHERE listify_list_id = :list_id";
      SqlParameterSource deleteParameters = new MapSqlParameterSource()
          .addValue("list_id", list.getId());
      jdbcTemplate.update(deleteQuery, deleteParameters);
      logger.debug("fack");
    }

    logger.debug("Saved list " + list.toString() + " to database.");
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
