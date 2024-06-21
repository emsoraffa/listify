package com.solberg.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin
@SpringBootApplication
public class ListifyApplication {
  public static void main(String[] args) {
    SpringApplication.run(ListifyApplication.class, args);

  }
}
