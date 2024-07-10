package com.solberg.dto;

import com.fasterxml.jackson.annotation.JsonProperty; // Import Jackson annotation
import java.util.List;

public class ListDto {
  private long id;
  private String listName;
  private List<CheckListItemDto> listItems;

  public ListDto() {
  }

  @JsonProperty("list_name")
  public String getListName() {
    return listName;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setListName(String listName) {
    this.listName = listName;
  }

  @JsonProperty("listitems") // Ensure this matches exactly what's sent from the frontend
  public List<CheckListItemDto> getListItems() {
    return listItems;
  }

  public void setListItems(List<CheckListItemDto> listItems) {
    this.listItems = listItems;
  }
}
