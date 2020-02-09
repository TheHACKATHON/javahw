package com.yevseienko.servlets;

import com.yevseienko.helpers.AppContext;
import com.yevseienko.data.interfaces.IArticleDataService;
import com.yevseienko.helpers.Dictionary;
import com.yevseienko.models.Article;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = Dictionary.URL.HOME + "index.html", name = "home")
public class HomeServlet extends HttpServlet {
  private static final String ARTICLES_ATTR = "articles";
  private IArticleDataService postService;

  @Override
  public void init() {
    postService = AppContext.getBean(IArticleDataService.class);
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    List<Article> articles = postService.getArticles();
    req.setAttribute(ARTICLES_ATTR, articles);
    req.getRequestDispatcher(Dictionary.JSP.HOME).forward(req, resp);
  }
}
