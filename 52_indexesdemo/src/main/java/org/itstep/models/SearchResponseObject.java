package org.itstep.models;

import java.util.ArrayList;

public class SearchResponseObject {
  private final ArrayList<Person> personList;
  private final long requestTime;
  private final int personCount;

  public SearchResponseObject(ArrayList<Person> personList, long requestTime, int personCount) {
    this.personList = personList;
    this.requestTime = requestTime;
    this.personCount = personCount;
  }
}
