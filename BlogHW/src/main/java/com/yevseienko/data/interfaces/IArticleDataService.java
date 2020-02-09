package com.yevseienko.data.interfaces;

import com.yevseienko.models.Article;

import java.math.BigInteger;
import java.util.List;

public interface IArticleDataService {
  List<Article> getArticles();
  Article getArticle(BigInteger id);
  BigInteger createArticle(Article article);
  boolean updateArticle(Article article);
  boolean deleteArticle(BigInteger id);
}
