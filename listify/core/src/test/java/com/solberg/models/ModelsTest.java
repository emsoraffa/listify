package com.solberg.models;

import org.junit.Test;
import static org.junit.Assert.*;

public class ModelsTest {

  @Test
  public void testShoppingList() {
    ListItem listItem = new ListItem("Milk");
    User user = new User("John Doe", "john@gmail.com");
    ShoppingList shoppingList = new ShoppingList(user, listItem);

    assertEquals("John Doe", shoppingList.getUser().getName());
  }
}
