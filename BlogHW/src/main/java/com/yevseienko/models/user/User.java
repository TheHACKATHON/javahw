package com.yevseienko.models.user;

import java.math.BigInteger;

public class User extends BasicUser {
  private String password;

  public User(BigInteger id, String email, String firstName, String lastName, String password) {
    super(id, email, firstName, lastName);
    this.password = password;
  }

  public String getPassword() {
    return password;
  }
}