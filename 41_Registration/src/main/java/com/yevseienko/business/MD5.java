package com.yevseienko.business;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5{
	private static String salt = "HZF622KA5A6TUQII";
	// static salt

	public static String hash(String password){
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update((salt + password).getBytes());
			byte[] digest = md.digest();
			return DatatypeConverter.printHexBinary(digest).toUpperCase();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static boolean verifyPassword(String password, String hash){
		return hash.equals(hash(password));
	}

}
