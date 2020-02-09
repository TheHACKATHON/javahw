package com.yevseienko.helpers;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hasher {
  private static final String STATIC_SALT = "7y7!wzuj#a53sjwp";

  public static String md5(String content){
    try {
      MessageDigest md = MessageDigest.getInstance("MD5");
      md.update((STATIC_SALT + content).getBytes());
      byte[] digest = md.digest();
      return DatatypeConverter.printHexBinary(digest).toUpperCase();
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }
    return null;
  }

  public static boolean verifyMd5(String password, String hash){
    String passwordHash = md5(password);
    return hash.equals(passwordHash);
  }
}

// TODO: add dynamic salt, store in db
// TODO: use argon2 hash function
