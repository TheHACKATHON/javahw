package com.yevseienko.servlets;

import com.yevseienko.business.Business;
import com.yevseienko.models.User;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/auth")
public class AuthServlet extends HttpServlet {
  Business business;

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    ServletContext servletContext = getServletContext();
    try {
      String authCookieName = servletContext.getInitParameter("authenticationCookieName");
      String error = req.getParameter("error");
      req.setAttribute("error", error != null ? error : "");
      User user = business.isLogined(req.getCookies(), authCookieName);
      if (user != null) {
        resp.sendRedirect("/");
        return;
      }
      servletContext.getRequestDispatcher("/WEB-INF/resources/auth.jsp").forward(req, resp);
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
