package com.solberg.springboot;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.ContentCachingRequestWrapper;

import com.solberg.models.ListItem;
import com.solberg.models.ListifyList;
import com.solberg.models.User;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api")
public class ListifyController {

  private static final Logger logger = LoggerFactory.getLogger(ListifyController.class);

  @GetMapping("/test")
  public ListItem testList() {
    return new ListItem("test");
  }

  @PostMapping("/list")
  public ResponseEntity<Map<String, Object>> postList(@RequestBody List<String> items) {
    logger.debug("Received items: " + items);

    Map<String, Object> response = new HashMap<>();
    response.put("message", "Received " + items.size() + " items");
    response.put("items", items);

    return ResponseEntity.ok(response);
  }

  @GetMapping("/dashboard/lists")
  public List<Map<String, Object>> getUserLists(@AuthenticationPrincipal Jwt jwt) {
    // Access JWT claims directly
    String username = jwt.getClaimAsString("name");
    String email = jwt.getClaimAsString("email");

    // Log or process information as needed
    logger.debug("Accessed by: " + username.toString());

    Map<String, Object> list = new HashMap<>();
    list.put("author", "John Doe"); // Author's name
    list.put("listitems", Collections.singletonList("Milk")); // List of items

    // Return a list containing the map
    return Collections.singletonList(list);
  }
}
