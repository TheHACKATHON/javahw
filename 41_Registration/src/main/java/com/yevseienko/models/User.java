package com.yevseienko.models;

import java.util.Date;

public class User{
	private final String username;
	private final String passwordHash;
	private String email;
	private String firstName;
	private String lastName;
	private String gender;
	private Date birth;
	private boolean subscribe;
	private String cookie;

	public User(String username, String passwordHash, String cookie) {
		this.username = username;
		this.passwordHash = passwordHash;
		this.cookie = cookie;
	}

	public void setCookie(String cookie){
		this.cookie = cookie;
	}

	public void setAdditionalInfo(String email, String firstName, String lastName, String gender, Date birth, boolean subscribe){
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.gender = gender;
		this.birth = birth;
		this.subscribe = subscribe;
	}

	public String getUsername() {
		return username;
	}

	public String getPasswordHash() {
		return passwordHash;
	}

	public String getCookie() {
		return cookie;
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

	public String getGender() {
		return gender;
	}

	public Date getBirth() {
		return birth;
	}

	public boolean isSubscribe() {
		return subscribe;
	}
}
