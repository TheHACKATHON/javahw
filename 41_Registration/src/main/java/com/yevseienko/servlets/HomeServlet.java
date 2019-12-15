package com.yevseienko.servlets;

import com.yevseienko.business.Business;
import com.yevseienko.data.XmlData;
import com.yevseienko.models.User;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

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

    ServletContext servletContext = config.getServletContext();
    String contextPathStr = servletContext.getRealPath(File.separator);
    initXml(servletContext, contextPathStr);
  }

  private void initXml(ServletContext servletContext, String contextPathStr){
    final String separator = ">";
    String pathDestructedStr = servletContext.getInitParameter("usersXmlFile");
    String[] pathDestructed = pathDestructedStr.split(separator);
    Path usersXmlPath = Paths.get(pathDestructed[0], Arrays.copyOfRange(pathDestructed, 1, pathDestructed.length)).normalize();
    String usersXmlPathStr = File.separator + usersXmlPath.toString();

    XmlData.loadData(contextPathStr, usersXmlPathStr);
  }
}
