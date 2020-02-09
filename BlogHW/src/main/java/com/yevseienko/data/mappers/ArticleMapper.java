package com.yevseienko.data.mappers;

import com.yevseienko.models.Article;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class ArticleMapper {
  public static ArrayList<Article> toArticle(List<Map<String, Object>> posts) {
    ArrayList<Article> result = new ArrayList<Article>();
    for (Map<String, Object> post : posts) {
      Article userResult = toArticle(post);
      result.add(userResult);
    }

    return result;
  }

  public static Article toArticle(Map<String, Object> post) {
    BigInteger id = (BigInteger) post.get("id");
    String title = (String) post.get("title");
    String text = (String) post.get("html_body");
    String author = (String) post.get("author");
    BigInteger authorId = (BigInteger) post.get("authorId");
    Date created = (Date) post.get("created");
    String imageUrl = (String) post.get("image_url");
    String subtitle = (String) post.get("subtitle");

    return new Article(id, title, author, authorId, created, text, subtitle, imageUrl);
  }
}
