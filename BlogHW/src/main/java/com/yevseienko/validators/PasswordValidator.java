package com.yevseienko.validators;

import com.yevseienko.validators.interfaces.IValidator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordValidator implements IValidator {
  private static final Pattern VALID_PASSWORD_REGEX =
      Pattern.compile("^((?=.*[a-z])(?=.*d)(?=.*[A-Z]).{6,32})$");
  private static final String ERROR_TEXT = "Password rules are:\n" +
      "At least a upper case character (A-Z)\n" +
      "At least a lower case character (a-z)\n" +
      "At least a digit (0-9)\n" +
      "At least 6 symbols\n" +
      "At most 32 symbols";

  @Override
  public boolean isValid(String password) {
    Matcher matcher = VALID_PASSWORD_REGEX.matcher(password);
    return matcher.find();
  }

  @Override
  public String getErrorText() {
    return ERROR_TEXT;
  }
}
