package com.solberg.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DashboardListDto {

  @JsonProperty("list_id")
  private Long listId;

  @JsonProperty("list_name")
  private String listName;

  private String author;

  @JsonProperty("list_items")
  private List<CheckListItemDto> listItems;

  public DashboardListDto() {

  }

  public DashboardListDto(Long listId, String listName, String author, List<CheckListItemDto> listItems) {
    this.listId = listId;
    this.listName = listName;
    this.author = author;
    this.listItems = listItems;
  }

  public String getListName() {
    return listName;
  }

  public void setListName(String listName) {
    this.listName = listName;
  }

  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public List<CheckListItemDto> getListItems() {
    return listItems;
  }

  public void setListItems(List<CheckListItemDto> listItems) {
    this.listItems = listItems;
  }

  public Long getListId() {
    return listId;
  }

  public void setListId(Long listId) {
    this.listId = listId;
  }

  @Override
  public String toString() {
    return "DashboardListDto{" +
        "listId=" + listId +
        ", listName='" + listName + '\'' +
        ", author='" + author + '\'' +
        ", listItems=" + listItems +
        '}';
  }
}
