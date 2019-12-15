package com.yevseienko.models;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class Converters {
  public static LocalDate convertToLocalDateViaMillisecond(Date dateToConvert) {
    return Instant.ofEpochMilli(dateToConvert.getTime())
        .atZone(ZoneId.systemDefault())
        .toLocalDate();
  }
}
