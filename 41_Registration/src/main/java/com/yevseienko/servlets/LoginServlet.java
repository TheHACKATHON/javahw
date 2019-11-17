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
import java.util.Map;

import static org.apache.commons.text.StringEscapeUtils.escapeHtml4;

@WebServlet(urlPatterns = "/login")
public class LoginServlet extends HttpServlet {
  private Business business;

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
    try {
      ServletContext servletContext = getServletContext();
      Map<String, String[]> map = req.getParameterMap();
      String[] defaultValue = new String[1];
      defaultValue[0] = "";

      String username = escapeHtml4(map.getOrDefault("username", defaultValue)[0]);
      String passwd = escapeHtml4(map.getOrDefault("password", defaultValue)[0]);
      String error = "";

      String authCookieName = servletContext.getInitParameter("authenticationCookieName");
      User user = business.loginUser(username, passwd);
      if(user != null) {
        resp.addCookie(new Cookie(authCookieName, user.getCookie()));
      } else {
        error = "Your password is incorrect";
      }
      resp.sendRedirect("/auth?error=" + error);
    } catch (
        IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void init(ServletConfig config) throws ServletException {
    super.init(config);
    business = new Business();
  }
}
