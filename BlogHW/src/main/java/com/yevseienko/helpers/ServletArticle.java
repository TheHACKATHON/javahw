package com.yevseienko.helpers;

import com.yevseienko.data.interfaces.IArticleDataService;
import com.yevseienko.models.Article;

import java.math.BigInteger;

public class ServletArticle {
  public static Article getArticleById(String id, IArticleDataService service){
    Article article = null;
    if (id != null && !id.isEmpty()) {
      try {
        article = service.getArticle(new BigInteger(id));
      } catch (Exception ex) {
        // ignored
      }
    }
    return article;
  }

}
