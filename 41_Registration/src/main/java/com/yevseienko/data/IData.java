package com.yevseienko.data;

import com.yevseienko.models.User;

import java.util.ArrayList;

public interface IData {
  ArrayList<User> getUsers();
  User getUserByCookie(String cookie);
  User getUserByUsername(String username);
  void addUser(User user);
  void setCookieToUser(String cookie, String username);
  boolean isUsernameExist(String username);
}
