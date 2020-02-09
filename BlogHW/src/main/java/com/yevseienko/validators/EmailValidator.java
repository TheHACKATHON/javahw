package com.yevseienko.validators;

import com.yevseienko.validators.interfaces.IValidator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailValidator implements IValidator {
  private static final Pattern VALID_EMAIL_ADDRESS_REGEX =
      Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
  private static final String ERROR_TEXT = "Email not valid. Check it and try again.";

  public boolean isValid(String email) {
    Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
    return matcher.find();
  }

  @Override
  public String getErrorText() {
    return ERROR_TEXT;
  }
}
