package org.itstep.models;

public class Person {
  private final int id;
  private final String fname;
  private final String lname;
  private final String gender;

  public Person(int id, String fname, String lname, String gender) {
    this.id = id;
    this.fname = fname;
    this.lname = lname;
    this.gender = gender;
  }

  @Override
  public String toString() {
    return "Person{" +
        "id=" + id +
        ", fname='" + fname + '\'' +
        ", lname='" + lname + '\'' +
        ", gender='" + gender + '\'' +
        '}';
  }
}
