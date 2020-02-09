package com.yevseienko.servlets.admin;

import com.yevseienko.helpers.Dictionary;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(urlPatterns = Dictionary.URL.LOGOUT, name = "logout")
public class LogoutServlet extends HttpServlet {
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    HttpSession session = req.getSession(true);
    session.invalidate();
    resp.sendRedirect(Dictionary.URL.HOME);
  }
}
