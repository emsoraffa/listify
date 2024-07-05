package com.solberg.models;

public class ListItem {
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

}
