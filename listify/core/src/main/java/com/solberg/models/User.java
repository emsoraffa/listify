package com.solberg.models;

import java.util.ArrayList;
import java.util.List;

public class User {
  // TODO: add more attributes like gender, locale etc.
  private Long id;
  private String name;
  private String email;
  private List<ListifyList> listifyLists;

  public User() {
    this.listifyLists = new ArrayList<>();
  }

  public User(String name, String email) {
    this();
    this.name = name;
    this.email = email;
  }

  public User(Long id, String name, String email, List<ListifyList> listifyLists) {
    this(name, email);
    this.id = id;
    this.listifyLists = listifyLists;
  }

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

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public List<ListifyList> getListifyLists() {
    return listifyLists;
  }

  public void setListifyLists(List<ListifyList> listifyLists) {
    this.listifyLists = listifyLists;
  }

  public void addListifyList(ListifyList listifyList) {
    this.listifyLists.add(listifyList);
  }

  @Override
  public String toString() {
    return "User{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", email='" + email + '\'' +
        ", listifyLists=" + listifyLists +
        '}';
  }
}
