package com.solberg.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ListifyList {
  private List<ListItem> listItems;
  private User user;

  public ListifyList(User user, ListItem... listItem) {
    this.user = user;
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
}
