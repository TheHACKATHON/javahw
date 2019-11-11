package com.yevseienko.servlets;

import com.yevseienko.models.User;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

public class Business {
	private static String authCookieName = "atoken";


	public static boolean isLogined(ServletContext servletContext, HttpServletRequest req){
		String rootPath = servletContext.getRealPath(File.separator);
		Cookie[] cookies = req.getCookies();
		if(cookies != null){
			Optional<Cookie> cookieOpt = Arrays.stream(cookies).filter(c -> c.getName().equals(authCookieName)).findFirst();
			if(cookieOpt.isPresent()){
				String userCookie = cookieOpt.get().getValue();
				User user = Json.getUserByCookie(userCookie, rootPath);
				return user != null;
			}
		}
		return false;
	}


	// return user cookie
	public static String registerUser(String name, String email, String firstName, String lastName, String gender, Date birth, boolean subscribe, String password, String rootPath){
		String hash = MD5.hash(password);
		String cookie = UUID.randomUUID().toString();
		User user = new User(name, hash, cookie);
		user.setAdditionalInfo(email, firstName, lastName, gender, birth, subscribe);
		Json.addUser(user, rootPath);
		return cookie;
	}
}

class MD5{
	private static String salt = "HZF622KA5A6TUQII";

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

class Json {
	private static String usersPath = Paths.get("./WEB-INF", "resources", "database", "users.json").toString();

	public static User getUserByCookie(String cookie, String rootPath) {
		JSONArray jsonUsers = getJsonUsers(rootPath);
		if(jsonUsers != null){
			for (Object userObj: jsonUsers) {
				JSONObject userJson = (JSONObject) userObj;
				userJson = (JSONObject) userJson.get("user");
				String cookieUser = (String)userJson.get("cookie");
				if(cookieUser.equals(cookie)){
					String username = (String)userJson.get("username");
					String passwordHash = (String)userJson.get("passwordHash");
					return new User(username, passwordHash, cookie);
				}
			}
		}
		return null;
	}

	public static void addUser(User user, String rootPath) {
		JSONArray jsonUsers = getJsonUsers(rootPath);

		JSONObject userJson = new JSONObject();
		userJson.put("username", user.getUsername());
		userJson.put("passwordHash", user.getPasswordHash());
		userJson.put("cookie", user.getCookie());

		JSONObject userUserJson = new JSONObject();
		userUserJson.put("user", userJson);

		jsonUsers.add(userUserJson);
		save(jsonUsers, rootPath);
	}

	private static void save(JSONArray users, String root){
		try (FileWriter file = new FileWriter(root + usersPath)) {
			file.write(users.toJSONString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static JSONArray getJsonUsers(String rootPath) {
		try (FileReader reader = new FileReader(rootPath + usersPath)) {
			return (JSONArray) new JSONParser().parse(reader);
//			int id = Integer.parseInt(idStr);
//			Optional<Homework> hwOpt = result.stream().filter(h -> h.getId() == (id)).findFirst();
//			Homework hw;
//			if(hwOpt.isPresent()){
//				hw = hwOpt.get();
//			} else {
//				hw = new Homework(id, homeworkName);
//				result.add(hw);
//			}
//			hw.addTask(taskName, "/" + configFile.getParentFile().getName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}

