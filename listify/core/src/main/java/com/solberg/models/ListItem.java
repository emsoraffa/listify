package com.solberg.models;

public class ListItem {
  private Long id;
  private String name;
  private Boolean state; // Defaults to false.

  public ListItem(String name) {
    this.name = name;
    this.state = false;
  }

  public ListItem(String name, Boolean state) {
    this.name = name;
    this.state = state;
  }

  public ListItem(String name, Boolean state, Long id) {
    this.name = name;
    this.state = state;
    this.id = id;
  }

  public ListItem() {

  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Boolean getState() {
    return state;
  }

  public void setState(Boolean state) {
    this.state = state;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  @Override
  public String toString() {
    return "ListItem{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", state=" + state +
        '}';
  }
}
