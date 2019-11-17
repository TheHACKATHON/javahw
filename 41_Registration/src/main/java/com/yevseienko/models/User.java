package com.yevseienko.models;

import java.io.Serializable;
import java.time.LocalDate;

public class User implements Serializable {
	private final String username;
	private String passwordHash;
	private String email;
	private String firstName;
	private String lastName;
	private String gender;
	private LocalDate birth;
	private boolean subscribe;
	private String cookie;

	public User(String username) {
		this.username = username;
	}

	public void setCookie(String cookie){
		this.cookie = cookie;
	}

	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	public void setAdditionalInfo(String email, String firstName, String lastName, String gender, LocalDate birth, boolean subscribe){
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

	public LocalDate getBirth() {
		return birth;
	}

	public boolean isSubscribe() {
		return subscribe;
	}
}
