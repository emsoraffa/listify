package com.solberg.springboot;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Profile("dev")
@RequestMapping("/dev")
public class DevLoginController {

  @GetMapping("/login")
  public ResponseEntity<Map<String, String>> mockLogin() {
    Map<String, String> token = new HashMap<>();
    token.put("token", "fake-jwt-token-for-dev-mode");
    return ResponseEntity.ok(token);
  }
}
