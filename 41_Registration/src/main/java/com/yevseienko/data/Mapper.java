package com.yevseienko.data;

import com.yevseienko.models.Converters;
import com.yevseienko.models.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class Mapper {
  //region public static methods

  public static ArrayList<User> User(List<Map<String, Object>> users) {
    ArrayList<User> usersResult = new ArrayList<>();
    for (Map<String, Object> user : users) {
      String username   = (String)user.get("username");
      String password   = (String)user.get("password");
      String email      = (String)user.get("email");
      String firstName  = (String)user.get("first_name");
      String lastName   = (String)user.get("last_name");
      String gender     = (String)user.get("gender");
      Date birth        = (Date)user.get("birthday");
      boolean subscribe = (boolean)user.get("subscribe");
      String cookie     = (String)user.get("cookie");


      User userResult = new User(username);
      userResult.setAdditionalInfo(email, firstName, lastName, gender, Converters.convertToLocalDateViaMillisecond(birth), subscribe);
      userResult.setCookie(cookie);
      userResult.setPasswordHash(password);
      usersResult.add(userResult);
    }

    return usersResult;
  }

  //endregion
}
