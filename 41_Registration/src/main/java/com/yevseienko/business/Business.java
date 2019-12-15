package com.yevseienko.business;

import com.yevseienko.data.IData;
import com.yevseienko.data.MySQLData;
import com.yevseienko.data.config.MySQLDbConfig;
import com.yevseienko.models.User;

import javax.servlet.http.Cookie;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import static org.apache.commons.text.StringEscapeUtils.escapeHtml4;

public class Business {
  //region fields

  private IData data;

  //endregion
  //region constructors

  public Business() {
    String dbDomain = "step.netspasibo.space";
    String dbName = "my_site_db";
    String dbUser = "root";
    String dbPassword = "2520LitresOfSecrets";
    String serverTimeZone = "Europe/Kiev";
    String encoding = "utf8";
    // TODO: move to web.xml
    // TODO: add constructor Business(IData)

    MySQLDbConfig dbConfig = new MySQLDbConfig(dbDomain, dbName, serverTimeZone, encoding, dbUser, dbPassword);
    data = new MySQLData(dbConfig);
  }

  //endregion
  //region public methods
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
    this.data.setCookieToUser(user.getCookie(), user.getUsername());
    return user.getCookie();
  }

  public User loginUser(String username, String password) {
    User user = data.getUserByUsername(username);
    if(user != null){
      if (MD5.verifyPassword(password, user.getPasswordHash())) {
        user.setCookie(generateCookie());
        data.setCookieToUser(user.getCookie(), user.getUsername());
        return user;
      }
    }
    return null;
  }

  public ArrayList<User> getUsers(){
    return data.getUsers();
  }

  //endregion
  //region private methods

  private String generateCookie() {
    return UUID.randomUUID().toString();
  }

  //endregion
}
