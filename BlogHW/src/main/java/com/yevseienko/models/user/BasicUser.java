package com.yevseienko.models.user;

import java.math.BigInteger;

public class BasicUser {
  private BigInteger id;
  private String email;
  private String firstName;
  private String lastName;

  public BasicUser(BigInteger id, String email, String firstName, String lastName) {
    this.id = id;
    this.email = email;
    this.firstName = firstName;
    this.lastName = lastName;
  }

  public BigInteger getId() {
    return id;
  }

  public String getEmail() {
    return email;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }
}
