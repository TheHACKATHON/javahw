package com.yevseienko.models;

import java.util.Comparator;

public class SortByIdReverse implements Comparator<Homework>
{
  public int compare(Homework a, Homework b)
  {
    return b.getId() - a.getId();
  }
}
