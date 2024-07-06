package com.solberg.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ListifyList {
  private String listName;
  private List<ListItem> listItems;
  private User user;

  public ListifyList(User user, String listName) {
    this.user = user;
    this.listName = listName;
    listItems = new ArrayList<>();
  }

  public ListifyList(User user, String listName, ListItem... listItem) {
    this.user = user;
    this.listName = listName;
    this.listItems = new ArrayList<>(Arrays.asList(listItem));
  }

  public User getUser() {
    return user;
  }

  public static void main(String[] args) {
    System.out.println("Hello World!");
  }

  public void addListItems(ListItem... ListItem) {
    for (ListItem listItem : listItems) {
      listItems.add(listItem);
    }
  }

  public String getListName() {
    return listName;
  }

  public void setListName(String listName) {
    this.listName = listName;
  }

  public List<ListItem> getListItems() {
    return listItems;
  }

  public void setListItems(List<ListItem> listItems) {
    this.listItems = listItems;
  }

  public void setUser(User user) {
    this.user = user;
  }
}
