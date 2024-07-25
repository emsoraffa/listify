package com.solberg.springboot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.solberg.dto.CheckListItemDto;
import com.solberg.dto.DashboardListDto;
import com.solberg.dto.ListDto;
import com.solberg.models.ListItem;
import com.solberg.models.ListifyList;
import com.solberg.models.User;
import com.solberg.persistence.ListDao;
import com.solberg.persistence.UserDao;

@RestController
@RequestMapping("/api")
public class ListifyController {

  private static final Logger logger = LoggerFactory.getLogger(ListifyController.class);

  @Autowired
  private UserDao userDao;

  @Autowired
  private ListDao listDao;

  @GetMapping("/list/{id}")
  public ResponseEntity<ListDto> getList(@PathVariable long id) {
    ListifyList list = listDao.findListById(id);

    if (list == null) {
      return ResponseEntity.notFound().build();
    }

    ListDto listDto = convertToListDto(list);

    logger.debug("Retrieved list: " + listDto.toString());
    return ResponseEntity.ok(listDto);
  }

  private ListDto convertToListDto(ListifyList list) {
    ListDto listDto = new ListDto();
    listDto.setId(list.getId());
    listDto.setListName(list.getName());
    listDto.setListItems(list.getListItems().stream()
        .map(item -> new CheckListItemDto(
            item.getId(),
            item.getName() != null ? item.getName() : "", // Handle null or provide a default value
            item.isState()))
        .collect(Collectors.toList()));
    return listDto;
  }

  @PostMapping("/list")
  public ResponseEntity<Map<String, Object>> postList(@RequestBody ListDto list,
      @AuthenticationPrincipal Jwt jwt) {
    logger.debug("Received list with name: " + list.getListName() + " and items: " + list.getListItems());
    User user = userDao.findUserByEmail(jwt.getClaimAsString("email"));

    if (user == null) {
      return ResponseEntity.status(404).body(Map.of("message", "User not found"));
    }

    logger.debug("Posted by " + user.toString());

    ListifyList userList;

    if (list.getId() != null) {
      userList = listDao.findListById(list.getId());
      logger.debug("Result from database query: " + userList.toString());
      if (userList == null || !userList.getUser().getId().equals(user.getId())) {
        logger.debug("list not found or access denied");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "List not found or access denied"));
      }
      userList.setName(list.getListName());
      userList.getListItems().clear();
      logger.debug("Overwriting list...");

    } else {
      userList = new ListifyList(user, list.getListName());
      logger.debug("Creating new list...");

    }

    if (list.getListItems() != null) {
      for (CheckListItemDto item : list.getListItems()) {
        ListItem listItem;
        if (item.getId() != null) {
          listItem = new ListItem(item.getText(), item.isChecked());
          listItem.setId(item.getId());
          logger.debug("Updated existing item: " + item.toString());
        } else {
          listItem = new ListItem(item.getText(), item.isChecked());
          logger.debug("Added new item: " + item.toString());
        }
        userList.addListItems(listItem);
      }
    } else {
      logger.debug("No new items to add");
    }
    listDao.saveList(userList);
    logger.debug("Successfully saved " + userList.getName());

    Map<String, Object> response = new HashMap<>();
    response.put("message", "Received " + list.getListItems().size() + " items");
    response.put("items", list.getListItems());
    response.put("id", userList.getId());

    return ResponseEntity.ok(response);
  }

  @GetMapping("/dashboard/lists")
  public ResponseEntity<Map<String, Object>> getUserLists(@AuthenticationPrincipal Jwt jwt) {

    String username = jwt.getClaimAsString("name");
    String email = jwt.getClaimAsString("email");

    User user = userDao.findUserByEmail(email);
    user = userDao.fetchUserLists(user);

    List<DashboardListDto> userListsDto = new ArrayList<>();

    for (ListifyList list : user.getListifyLists()) {
      List<CheckListItemDto> itemDtos = new ArrayList<>();
      for (ListItem item : list.getListItems()) {
        itemDtos.add(new CheckListItemDto(item.getId(), item.getName(), item.isState()));
      }
      userListsDto.add(new DashboardListDto(list.getId(), list.getName(), user.getName(), itemDtos));
    }

    logger.debug("User lists retrieved: " + userListsDto.toString());

    // TODO: retrieve collaborated lists.

    Map<String, Object> response = new HashMap<>();
    response.put("message", "Received " + user.getListifyLists().size() + " items");
    response.put("items", userListsDto);

    return ResponseEntity.ok(response);
  }

  @GetMapping("/user/details")
  public ResponseEntity<Map<String, String>> getUserDetails(@AuthenticationPrincipal Jwt jwt) {
    String email = jwt.getClaimAsString("email");

    User user = userDao.findUserByEmail(email);

    if (user == null) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    logger.debug("Fetching user details for: " + user);

    Map<String, String> userDetails = new HashMap<>();
    userDetails.put("name", user.getName());
    userDetails.put("email", user.getEmail());

    return ResponseEntity.ok(userDetails);
  }
}
