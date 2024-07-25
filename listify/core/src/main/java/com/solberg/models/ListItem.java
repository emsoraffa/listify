package com.solberg.models;

public class ListItem {
  Long id;
  String name;
  boolean state;
  int position;

  public ListItem() {
  }

  public ListItem(String name) {
    this.name = name;
  }

  public ListItem(String name, boolean state) {
    this.name = name;
    this.state = state;
  }

  public ListItem(Long id, String name, boolean state, int position) {
    this.id = id;
    this.name = name;
    this.state = state;
    this.position = position;
  }

  // Getters and setters
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public boolean isState() {
    return state;
  }

  public void setState(boolean state) {
    this.state = state;
  }

  public int getPosition() {
    return position;
  }

  public void setPosition(int position) {
    this.position = position;
  }

  @Override
  public String toString() {
    return "ListItem{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", state=" + state +
        ", position=" + position +
        '}';
  }
}
