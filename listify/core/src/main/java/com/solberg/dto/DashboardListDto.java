package com.solberg.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DashboardListDto {

  @JsonProperty("list_id")
  private Long list_id;
  private String list_name;
  private String author;
  private List<CheckListItemDto> list_items;

  public DashboardListDto() {

  }

  public DashboardListDto(Long list_id, String list_name, String author, List<CheckListItemDto> list_items) {
    this.list_id = list_id;
    this.list_name = list_name;
    this.author = author;
    this.list_items = list_items;
  }

  public String getList_name() {
    return list_name;
  }

  public void setList_name(String list_name) {
    this.list_name = list_name;
  }

  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public List<CheckListItemDto> getList_items() {
    return list_items;
  }

  public void setList_items(List<CheckListItemDto> list_items) {
    this.list_items = list_items;
  }

  public Long getList_id() {
    return list_id;
  }

  public void setList_id(Long list_id) {
    this.list_id = list_id;
  }

  @Override
  public String toString() {
    return "DashboardListDto{" +
        "list_id=" + list_id +
        ", list_name='" + list_name + '\'' +
        ", author='" + author + '\'' +
        '}';
  }

}
