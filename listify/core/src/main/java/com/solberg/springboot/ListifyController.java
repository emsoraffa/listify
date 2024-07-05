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
import org.springframework.beans.factory.annotation.Autowired;
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
import com.solberg.persistence.DataHandler;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api")
public class ListifyController {

  private static final Logger logger = LoggerFactory.getLogger(ListifyController.class);

  @Autowired
  private DataHandler dataHandler;

  @GetMapping("/test")
  public ListItem testList() {
    return new ListItem("test");
  }

  @PostMapping("/list")
  public ResponseEntity<Map<String, Object>> postList(@RequestBody List<Map<String, Object>> items,
      @AuthenticationPrincipal Jwt jwt) {
    logger.debug("Received items: " + items);
    User user = dataHandler.findUserByEmail(jwt.getClaimAsString("email"));

    if (user == null) {
      return ResponseEntity.status(404).body(Map.of("message", "User not found"));
    }

    ListifyList userList = new ListifyList(user);

    for (Map<String, Object> item : items) {
      String text = (String) item.get("text");
      Boolean checked = (Boolean) item.get("checked");

      userList.addListItems(new ListItem(text, checked));

    }

    dataHandler.saveList(userList);

    Map<String, Object> response = new HashMap<>();
    response.put("message", "Received " + items.size() + " items");
    response.put("items", items);

    return ResponseEntity.ok(response);
  }

  @GetMapping("/dashboard/lists")
  public List<Map<String, Object>> getUserLists(@AuthenticationPrincipal Jwt jwt) {

    String username = jwt.getClaimAsString("name");
    String email = jwt.getClaimAsString("email");

    logger.debug("Accessed by: " + username.toString());

    Map<String, Object> list = new HashMap<>();
    list.put("author", "John Doe"); // Author's name
    list.put("listitems", Collections.singletonList("Milk")); // List of items

    // Return a list containing the map
    return Collections.singletonList(list);
  }
}
