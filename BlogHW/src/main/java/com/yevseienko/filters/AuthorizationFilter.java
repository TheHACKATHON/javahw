package com.yevseienko.filters;

import com.yevseienko.helpers.Dictionary;
import com.yevseienko.models.user.BasicUser;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(filterName = "authorizationFilter",
    servletNames = { "admin", "admin-article", "delete-article" })
public class AuthorizationFilter implements Filter {

  @Override
  public void init(FilterConfig filterConfig) {

  }

  @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
    HttpServletRequest req = (HttpServletRequest) servletRequest;
    HttpServletResponse resp = (HttpServletResponse) servletResponse;
    HttpSession session = req.getSession(true);
    BasicUser user = (BasicUser)session.getAttribute(Dictionary.SessionAttribute.USER);
    if(user == null){
      resp.sendRedirect(Dictionary.URL.LOGIN);
      return;
    }
    filterChain.doFilter(req, resp);
  }

  @Override
  public void destroy() {

  }
}
