package com.yevseienko.servlets.admin;

import com.yevseienko.data.interfaces.IArticleDataService;
import com.yevseienko.helpers.AppContext;
import com.yevseienko.helpers.Dictionary;
import com.yevseienko.helpers.ServletArticle;
import com.yevseienko.models.Article;
import com.yevseienko.models.user.BasicUser;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigInteger;

@WebServlet(urlPatterns = Dictionary.URL.ADMIN_ARTICLE, name = "admin-article")
public class AdminArticleServlet extends HttpServlet {
  private static final String ID_PARAM = "id";
  private static final String TITLE_PARAM = "title";
  private static final String SUBTITLE_PARAM = "subtitle";
  private static final String IMAGE_PARAM = "imageUrl";
  private static final String HTML_BODY_PARAM = "htmlData";

  private static final String ARTICLE_ATTR = "article";
  private static final String ERROR_ATTR = "error";

  private IArticleDataService articleService;

  @Override
  public void init() {
    articleService = AppContext.getBean(IArticleDataService.class);
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    String id = req.getParameter(ID_PARAM);
    Article article = ServletArticle.getArticleById(id, articleService);
    req.setAttribute(ARTICLE_ATTR, article);
    req.getRequestDispatcher(Dictionary.JSP.EDIT_ARTICLE).forward(req, resp);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    String id = req.getParameter(ID_PARAM);
    String title = req.getParameter(TITLE_PARAM);
    String subtitle = req.getParameter(SUBTITLE_PARAM);
    String imageUrl = req.getParameter(IMAGE_PARAM);
    String body = req.getParameter(HTML_BODY_PARAM);

    Article article = ServletArticle.getArticleById(id, articleService);
    HttpSession session = req.getSession();
    BasicUser user = (BasicUser)session.getAttribute(Dictionary.SessionAttribute.USER);

    boolean result = false;
    if(article != null){
      if(user.getId().equals(article.getAuthorId())){
        result = article.update(title, subtitle, body, imageUrl);
        if(result){
          result = articleService.updateArticle(article);
        }
      } else {
        req.getRequestDispatcher(Dictionary.JSP.Error.CODE_401).forward(req, resp);
      }
    } else {
      article = new Article(title, user.getId(), body, subtitle, imageUrl);
      article.setId(articleService.createArticle(article));
      result = !article.getId().equals(BigInteger.ZERO);
    }

    if(result){
      resp.sendRedirect(String.format("%s?%s=%d", Dictionary.URL.ARTICLE, ID_PARAM, article.getId()));
    } else {
      req.setAttribute(ERROR_ATTR, "Unexpected error, pls try later.");
      req.setAttribute(ARTICLE_ATTR, article);
      req.getRequestDispatcher(Dictionary.JSP.EDIT_ARTICLE).forward(req, resp);
    }
  }

  @Override
  protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    super.doDelete(req, resp);
  }
}
