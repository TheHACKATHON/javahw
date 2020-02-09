import java.io.Serializable;
import java.time.LocalDate;

public class User implements Serializable {
	//region fields

	private String username;
	private String passwordHash;
	private String email;
	private String firstName;
	private String lastName;
	private String gender;
	private LocalDate birth;
	private boolean subscribe;
	private String cookie;

	//endregion
	//region constructors

	public User(String username) {
		this.username = username;
	}

	//endregion
	//region public methods

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

	//endregion
	//region getters

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

	//endregion
}