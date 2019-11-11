package com.yevseienko.servlets;

import com.yevseienko.models.Business;
import com.yevseienko.models.Homework;
import com.yevseienko.models.SortByIdReverse;
import com.yevseienko.models.Task;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Optional;

public class HomeServlet extends HttpServlet {
  private static Comparator<Homework> sort = new SortByIdReverse();
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
          throws ServletException, IOException {

    String menuPosCookieName= "menuPosition";
    String menuPosDefaultCookieValue= "left";
    Cookie[] cookies = req.getCookies();
    String reqMenuPosCookie = menuPosDefaultCookieValue;
    Optional<Cookie> cookie = Arrays.stream(cookies).filter(c -> c.getName().equals(menuPosCookieName)).findFirst();
    if(cookie.isPresent()){
      reqMenuPosCookie = cookie.get().getValue();
    } else {
      resp.addCookie(new Cookie(menuPosCookieName, menuPosDefaultCookieValue));
    }

    ServletContext servletContext = getServletContext();
    String contextPath = servletContext.getRealPath(File.separator);
    String configFile = servletContext.getInitParameter("configFile");
    String lookForFile = servletContext.getInitParameter("lookForFile");

    ArrayList<Homework> homeworks = Business.getHomeworkList(contextPath, configFile, lookForFile);
    homeworks.sort(sort);
    req.setAttribute("homeworks", homeworks);

    int reqTaskId = 0;
    int reqHomeworkId = 0;
    String reqTaskPath = "";
    String reqTaskName = "";

    try
    {
      int id = Integer.parseInt(req.getParameter("id"));
      int taskId = Integer.parseInt(req.getParameter("task"));
      Optional<Homework> hwOpt = homeworks.stream()
              .filter(h -> h.getId() == id).findFirst();

      if(hwOpt.isPresent()){
        Homework hw = hwOpt.get();
        Optional<Task> taskOpt = hw.getTasks().stream()
                .filter(t -> t.getId() == taskId).findFirst();

        if(taskOpt.isPresent()){
          Task task = taskOpt.get();
          reqHomeworkId= hw.getId();
          reqTaskPath=task.getPath();
          reqTaskId=  task.getId();
          reqTaskName=task.getName();
        }
      }
    } catch (Exception e){
      e.printStackTrace();
    }
    req.setAttribute("homeworkId", reqHomeworkId);
    req.setAttribute("taskPath", reqTaskPath);
    req.setAttribute("taskId", reqTaskId);
    req.setAttribute("taskName", reqTaskName);
    req.setAttribute("menuPosition", reqMenuPosCookie);
    servletContext.getRequestDispatcher("/home.jsp").forward(req, resp);
  }
}
