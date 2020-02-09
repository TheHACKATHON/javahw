package com.yevseienko.servlets;

import com.yevseienko.business.Business;
import com.yevseienko.models.User;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Map;

import static org.apache.commons.text.StringEscapeUtils.escapeHtml4;

@WebServlet(urlPatterns = "/registration")
public class RegistrationServlet extends HttpServlet {
  private Business business;

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    try {
      Map<String, String[]> map = req.getParameterMap();
      String[] defaultValue = new String[1];
      defaultValue[0] = "";

      String passwd = escapeHtml4(map.getOrDefault("password", defaultValue)[0]);
      String confPasswd = escapeHtml4(map.getOrDefault("conf-password", defaultValue)[0]);

      if(!passwd.equals(confPasswd)){
        throw new IllegalArgumentException("Passwords don't match");
      }

      String username = escapeHtml4(map.getOrDefault("username", defaultValue)[0]);
      String email = escapeHtml4(map.getOrDefault("email", defaultValue)[0]);
      String firstName = escapeHtml4(map.getOrDefault("first", defaultValue)[0]);
      String lastName = escapeHtml4(map.getOrDefault("last", defaultValue)[0]);
      String gender = escapeHtml4(map.getOrDefault("gender", defaultValue)[0]);
      String birth = escapeHtml4(map.getOrDefault("birth", defaultValue)[0]);
      String sub = escapeHtml4(map.getOrDefault("subscribe", defaultValue)[0]);

      User user = new User(username);
      user.setAdditionalInfo(email, firstName, lastName, gender, LocalDate.parse(birth), !sub.isEmpty());
      String cookie = business.registerUser(user, passwd);
      ServletContext servletContext = getServletContext();
      String cookieName = servletContext.getInitParameter("authenticationCookieName");
      resp.addCookie(new Cookie(cookieName, cookie));
      resp.sendRedirect("/");
    } catch (IllegalArgumentException ex){
      resp.sendRedirect("/auth?error=" + ex.getMessage());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void init(ServletConfig config) throws ServletException {
    super.init(config);
    business = new Business();
  }
}