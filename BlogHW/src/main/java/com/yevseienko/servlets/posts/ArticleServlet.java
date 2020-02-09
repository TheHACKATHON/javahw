package com.yevseienko.servlets.posts;

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
import java.math.BigInteger;

@WebServlet(urlPatterns = Dictionary.URL.ARTICLE, name = "article")
public class ArticleServlet extends HttpServlet {
  private static final String ID_PARAM = "id";
  private static final String ARTICLE_ATTR = "article";

  private IArticleDataService service;

  @Override
  public void init() {
    service = AppContext.getBean(IArticleDataService.class);
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    String id = req.getParameter(ID_PARAM);
    Article article = null;
    if (id != null && !id.isEmpty()) {
      try {
        article = service.getArticle(new BigInteger(id));
      } catch (Exception ex) {
        // ignored
      }
    }
    if (article == null) {
      req.getRequestDispatcher(Dictionary.JSP.Error.CODE_404).forward(req, resp);
      return;
    }
    req.setAttribute(ARTICLE_ATTR, article);
    req.getRequestDispatcher(Dictionary.JSP.VIEW_ARTICLE).forward(req, resp);
  }
}
