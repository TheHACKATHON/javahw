package com.yevseienko.servlets;

import com.yevseienko.business.Business;
import com.yevseienko.models.User;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class HomeServlet extends HttpServlet {
  private Business business;

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    ServletContext servletContext = getServletContext();
    String authCookieName = servletContext.getInitParameter("authenticationCookieName");
    User user = business.isLogined(req.getCookies(), authCookieName);
    if (user != null) {
      req.setAttribute("loginedUser", user);
      req.setAttribute("users", business.getUsers());
      servletContext.getRequestDispatcher("/WEB-INF/resources/table.jsp").forward(req, resp);
    }
    resp.sendRedirect("/auth");
  }

  @Override
  public void init(ServletConfig config) throws ServletException {
    super.init(config);
    business = new Business();
  }
}
