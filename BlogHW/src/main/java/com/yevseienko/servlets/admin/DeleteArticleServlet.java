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

@WebServlet(urlPatterns = Dictionary.URL.DELETE_ARTICLE, name = "delete-article")
public class DeleteArticleServlet extends HttpServlet {
  private static final String ID_PARAM = "id";

  private IArticleDataService articleService;

  @Override
  public void init() {
    articleService = AppContext.getBean(IArticleDataService.class);
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    String id = req.getParameter(ID_PARAM);
    Article article = ServletArticle.getArticleById(id, articleService);

    HttpSession session = req.getSession();
    BasicUser user = (BasicUser)session.getAttribute(Dictionary.SessionAttribute.USER);

    if(user.getId().equals(article.getAuthorId())){
      articleService.deleteArticle(article.getId());
      resp.sendRedirect(Dictionary.URL.ADMIN);
    } else {
      req.getRequestDispatcher(Dictionary.JSP.Error.CODE_401).forward(req, resp);
    }
  }
}
