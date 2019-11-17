package com.yevseienko.business;

import com.yevseienko.data.IData;
import com.yevseienko.data.XmlData;
import com.yevseienko.models.User;

import javax.servlet.http.Cookie;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import static org.apache.commons.text.StringEscapeUtils.escapeHtml4;

public class Business {
  private IData data;

  public Business() {
    data = new XmlData();
  }

  public User isLogined(Cookie[] cookies, String authCookieName) {
    if (cookies != null) {
      Optional<Cookie> cookieOpt = Arrays.stream(cookies).filter(c -> escapeHtml4(c.getName()).equals(authCookieName)).findFirst();
      if (cookieOpt.isPresent()) {
        String userCookie = cookieOpt.get().getValue();
        User user = this.data.getUserByCookie(userCookie);
        return user;
      }
    }
    return null;
  }

  public String registerUser(User user, String password) {
    if(data.isUsernameExist(user.getUsername())){
      throw new IllegalArgumentException("Username already exist");
    }
    String passwordHash = MD5.hash(password);
    String cookie = generateCookie();
    user.setCookie(cookie);
    user.setPasswordHash(passwordHash);
    this.data.addUser(user);
    return user.getCookie();
  }

  public User loginUser(String username, String password) {
    User user = data.getUserByUsername(username);
    if(user != null){
      if (MD5.verifyPassword(password, user.getPasswordHash())) {
        user.setCookie(generateCookie());
        data.setCookieToUser(user.getCookie(), username);
      }
      return user;
    }
    return null;
  }

  public ArrayList<User> getUsers(){
    return data.getUsers();
  }

  private String generateCookie() {
    return UUID.randomUUID().toString();
  }
}

