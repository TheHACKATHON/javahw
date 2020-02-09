package com.yevseienko.filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import java.io.IOException;

@WebFilter(filterName = "encodingFilter",
    servletNames = {"home", "login", "article", "admin"},
    initParams = {
        @WebInitParam(name = "encoding", value = "UTF8")
    })
public class EncodingFilter implements Filter {
  String encoding;

  @Override
  public void init(FilterConfig filterConfig) {
    encoding = filterConfig.getInitParameter("encoding");
  }

  @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
    servletResponse.setContentType("text/html");
    servletResponse.setCharacterEncoding(encoding);
    servletRequest.setCharacterEncoding(encoding);
    filterChain.doFilter(servletRequest, servletResponse);
  }

  @Override
  public void destroy() {

  }
}
