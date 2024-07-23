package com.solberg.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class ListDto {
  @JsonProperty("id")
  private long id;

  @JsonProperty("list_name")
  private String listName;

  @JsonProperty("list_items")
  private List<CheckListItemDto> listItems;

  public ListDto() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getListName() {
    return listName;
  }

  public void setListName(String listName) {
    this.listName = listName;
  }

  public List<CheckListItemDto> getListItems() {
    return listItems;
  }

  public void setListItems(List<CheckListItemDto> listItems) {
    this.listItems = listItems;
  }

  @Override
  public String toString() {
    return "ListDto{" +
        "id=" + id +
        ", listName='" + listName + '\'' +
        ", listItems=" + listItems +
        '}';
  }
}
