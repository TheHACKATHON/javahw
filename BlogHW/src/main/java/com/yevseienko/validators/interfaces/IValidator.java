package com.yevseienko.validators.interfaces;

public interface IValidator {
  boolean isValid(String value);
  String getErrorText();
}
