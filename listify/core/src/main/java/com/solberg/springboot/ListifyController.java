package com.solberg.springboot;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.ContentCachingRequestWrapper;

import com.solberg.models.ListItem;

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

    // Create a response map
    Map<String, Object> response = new HashMap<>();
    response.put("message", "Received " + items.size() + " items");
    response.put("items", items);

    return ResponseEntity.ok(response);
  }

}
