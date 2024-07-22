package com.solberg.persistence;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.solberg.models.ListifyList;

public interface ListDao {

  // FIX: lookinto id type, should be long?
  public ListifyList findListById(long id);

  public void saveList(ListifyList list);
}
