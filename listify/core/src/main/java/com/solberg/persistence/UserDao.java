package com.solberg.persistence;

import com.solberg.models.User;

public interface UserDao {

  public void registerUser(User user);

  public User findUserByEmail(String email);
}
