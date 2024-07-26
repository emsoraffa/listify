package com.solberg.springboot;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Profile("mobile-dev")
@RequestMapping("/dev")
public class DevLoginController {

  private static final Logger logger = LoggerFactory.getLogger(DevLoginController.class);

  @GetMapping("/login")
  public ResponseEntity<Map<String, String>> mockLogin() {
    Map<String, String> token = new HashMap<>();
    token.put("token", "fake-jwt-token-for-dev-mode");
    logger.debug("Fake token dispatched.");
    return ResponseEntity.ok(token);
  }
}
