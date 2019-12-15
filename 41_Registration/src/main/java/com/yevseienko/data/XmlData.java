package com.yevseienko.data;

import com.yevseienko.models.User;

import java.io.*;
import java.util.ArrayList;
import java.util.Optional;

public class XmlData implements IData {
  //region static fields

  private static String usersPath;
  private static String rootPath;

  //endregion
  //region public static methods

  public static void loadData(String contextPath, String usersPath) {
    if (!contextPath.isEmpty() && !usersPath.isEmpty()) {
      XmlData.usersPath = usersPath;
      XmlData.rootPath = contextPath;
    }
  }

  //endregion
  //region public methods

  @Override
  public User getUserByUsername(String username) {
    ArrayList<User> users = getUsers();
    Optional<User> userOptional = users.stream().filter(u -> u.getUsername().equalsIgnoreCase(username)).findFirst();
    if (userOptional.isPresent()) {
      User user = userOptional.get();
      return user;
    }
    return null;
  }

  @Override
  public User getUserByCookie(String cookie) {
    ArrayList<User> users = getUsers();
    Optional<User> userOptional = users.stream().filter(u -> u.getCookie().equals(cookie)).findFirst();
    if (userOptional.isPresent()) {
      User user = userOptional.get();
      return user;
    }
    return null;
  }

  @Override
  public void setCookieToUser(String cookie, String username){
    ArrayList<User> users = getUsers();
    Optional<User> userOptional = users.stream().filter(u -> u.getUsername().equals(username)).findFirst();
    if (userOptional.isPresent()) {
      User user = userOptional.get();
      user.setCookie(cookie);
      saveUsers(users);
    }
  }

  @Override
  public boolean isUsernameExist(String username) {
    ArrayList<User> users = getUsers();
    return users.stream().anyMatch(u -> u.getUsername().equalsIgnoreCase(username));
  }

  @Override
  public void addUser(User user) {
    ArrayList<User> users = getUsers();
    users.add(user);
    saveUsers(users);
  }

  @Override
  public ArrayList<User> getUsers() {
    try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(rootPath + usersPath))) {
      ArrayList<User> users = (ArrayList<User>) objectInputStream.readObject();
      return users != null ? users : new ArrayList<>();
    } catch (IOException | ClassNotFoundException e) {
      e.printStackTrace();
    }
    return new ArrayList<>();
  }

  //endregion
  //region private methods

  private void saveUsers(ArrayList<User> users) {
    try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(rootPath + usersPath))) {
      objectOutputStream.writeObject(users);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  //endregion
}
