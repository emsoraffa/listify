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
    logger.debug("Attempting to save\n" + list.getListItems() + "\nto Database.");

    // Update existing list or insert a new one and fetch ID
    if (list.getId() != null && listExists(list.getId())) {
      String updateQuery = "UPDATE listify_lists SET list_name = :list_name WHERE id = :id";
      SqlParameterSource updateParameters = new MapSqlParameterSource()
          .addValue("list_name", list.getName())
          .addValue("id", list.getId());
      jdbcTemplate.update(updateQuery, updateParameters);
      logger.debug("Existing list updated.");
    } else {
      String insertQuery = "INSERT INTO listify_lists (list_name, user_id) VALUES (:list_name, :user_id)";
      KeyHolder keyHolder = new GeneratedKeyHolder();
      jdbcTemplate.update(insertQuery, new MapSqlParameterSource()
          .addValue("list_name", list.getName())
          .addValue("user_id", list.getUser().getId()), keyHolder, new String[] { "ID" });
      list.setId(keyHolder.getKey().longValue());
      logger.debug("New list inserted to Database with id:" + list.getId());
    }

    // Handle list items
    int position = 0;
    for (ListItem item : list.getListItems()) {
      if (item.getId() == null) {
        // Insert new list item and fetch ID
        String insertItemQuery = "INSERT INTO list_items (name, state, listify_list_id, position) VALUES (:name, :state, :list_id, :position)";
        KeyHolder itemKeyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(insertItemQuery, new MapSqlParameterSource()
            .addValue("name", item.getName())
            .addValue("state", item.isState())
            .addValue("list_id", list.getId())
            .addValue("position", position), itemKeyHolder, new String[] { "ID" });
        item.setId(Objects.requireNonNull(itemKeyHolder.getKey()).longValue());
        logger.debug("Inserted new listitem record: " + item);
      } else {
        // Update existing list item
        String updateItemQuery = "UPDATE list_items SET name = :name, state = :state, position = :position WHERE id = :id";
        jdbcTemplate.update(updateItemQuery, new MapSqlParameterSource()
            .addValue("name", item.getName())
            .addValue("state", item.isState())
            .addValue("position", position)
            .addValue("id", item.getId()));
        logger.debug("Updated existing listitem record: " + item);
      }
      position++;
    }

    logger.debug("Saved list " + list.toString() + " to database.");
  }

  private boolean listItemExists(Long itemId) {
    String query = "SELECT COUNT(*) FROM list_items WHERE id = :id";
    SqlParameterSource parameters = new MapSqlParameterSource().addValue("id", itemId);
    Integer count = jdbcTemplate.queryForObject(query, parameters, Integer.class);
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
        List<ListItem> items = getListItems(id);
        list.setListItems(items);
      }
      return list;
    } catch (EmptyResultDataAccessException e) {
      return null;
    }
  }

  public List<ListItem> getListItems(long listId) {
    String listItemQuery = "SELECT id AS list_item_id, name AS list_item_name, state AS list_item_state, position " +
        "FROM list_items " +
        "WHERE listify_list_id = :list_id " +
        "ORDER BY position";
    SqlParameterSource itemParameters = new MapSqlParameterSource().addValue("list_id", listId);
    return jdbcTemplate.query(listItemQuery, itemParameters, new ListItemRowMapper());
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
