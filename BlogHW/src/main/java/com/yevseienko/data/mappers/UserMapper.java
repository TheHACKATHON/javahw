package com.yevseienko.data.mappers;

import com.yevseienko.models.user.BasicUser;
import com.yevseienko.models.user.User;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UserMapper {
  public static BasicUser toBasic(User user){
    return new BasicUser(user.getId(), user.getEmail(), user.getFirstName(), user.getLastName());
  }

  public static ArrayList<User> toUsers(List<Map<String, Object>> users) {
    ArrayList<User> result = new ArrayList<User>();
    for (Map<String, Object> user : users) {
      User userResult = toUser(user);
      result.add(userResult);
    }

    return result;
  }

  public static User toUser(Map<String, Object> user) {
    BigInteger id = (BigInteger) user.get("id");
    String email = (String) user.get("email");
    String password = (String) user.get("password");
    String first_name = (String) user.get("first_name");
    String last_name = (String) user.get("last_name");

    return new User(id, email, first_name, last_name, password);
  }
}
