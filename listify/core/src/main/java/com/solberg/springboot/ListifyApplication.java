
package com.solberg.springboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import com.solberg.persistence.ListDao;
import com.solberg.persistence.UserDao;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ComponentScan(basePackages = "com.solberg")
@SpringBootApplication
public class ListifyApplication implements CommandLineRunner {

  private static final Logger logger = LoggerFactory.getLogger(ListifyApplication.class);

  @Autowired
  private UserDao userDao;

  @Autowired
  private ListDao listDao;

  @Value("${spring.security.oauth2.client.registration.google.client-id}")
  private String googleClientId;

  @Value("${spring.security.oauth2.client.registration.google.client-secret}")
  private String googleClientSecret;

  @Override
  public void run(String... args) throws Exception {
    logger.info("Google Client ID: {}", googleClientId);
    logger.info("Google Client Secret: {}", googleClientSecret != null ? "*******" : "Not Set");

  }

  public static void main(String[] args) {
    SpringApplication.run(ListifyApplication.class, args);
  }

}
