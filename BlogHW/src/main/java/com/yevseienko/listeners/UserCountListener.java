package com.yevseienko.listeners;

import com.yevseienko.helpers.Dictionary;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@WebListener("userCount")
public class UserCountListener implements HttpSessionListener {
  private static int total = 0;
  private static int current = 0;

  public void sessionCreated(HttpSessionEvent e) {
    total++;
    current++;

    ServletContext context = e.getSession().getServletContext();
    context.setAttribute(Dictionary.SessionAttribute.TOTAL_USERS_COUNT, total);
    context.setAttribute(Dictionary.SessionAttribute.CURRENT_USERS_COUNT, current);
  }

  public void sessionDestroyed(HttpSessionEvent e) {
    current--;
    ServletContext context = e.getSession().getServletContext();
    context.setAttribute(Dictionary.SessionAttribute.CURRENT_USERS_COUNT, current);
  }
}
