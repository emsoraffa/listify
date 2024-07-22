
package com.solberg.persistence.util;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import com.solberg.models.ListifyList;
import com.solberg.models.User;

public class ListifyListRowMapper implements RowMapper<ListifyList> {
  // TODO: listitems

  @Override
  public ListifyList mapRow(ResultSet rs, int rowNum) throws SQLException {
    ListifyList list = new ListifyList();
    list.setId(rs.getLong("id"));
    list.setListName(rs.getString("list_name"));

    User user = new User();
    user.setId(rs.getLong("user_id"));
    user.setName(rs.getString("username"));
    user.setEmail(rs.getString("email"));

    list.setUser(user);

    return list;
  }
}
