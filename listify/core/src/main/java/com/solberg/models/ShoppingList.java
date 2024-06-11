package com.solberg.models;

import java.util.ArrayList;
import java.util.List;

public class ShoppingList {
  private List<ListItem> listItems;
  private User user;

  public ShoppingList(List<ListItem> ListItems, User user) {
    this.listItems = ListItems;
    this.user = user;
  }

  public static void main(String[] args) {
    System.out.println("Hello World!");
  }
}
