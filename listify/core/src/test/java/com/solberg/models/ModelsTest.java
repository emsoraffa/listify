package com.solberg.models;

import org.junit.Test;
import static org.junit.Assert.*;

public class ModelsTest {

  @Test
  public void testListifyList() {
    ListItem listItem = new ListItem("Milk");
    User user = new User("John Doe", "john@gmail.com");
    ListifyList ListifyList = new ListifyList(user, listItem);

    assertEquals("John Doe", ListifyList.getUser().getName());
  }
}
