package com.solberg.springboot;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.solberg.models.ListItem;

@CrossOrigin
@RestController
public class ListifyController {

  @GetMapping("/test")
  public ListItem testList() {
    return new ListItem("test");
  }
}
