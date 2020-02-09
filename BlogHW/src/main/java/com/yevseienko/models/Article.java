package com.yevseienko.models;

import java.math.BigInteger;
import java.util.Date;

public class Article {
  private BigInteger id;
  private String title;
  private String author;
  private BigInteger authorId;
  private Date date;
  private String text;
  private String subtitle;
  private String imageUrl;

  public Article(BigInteger id, String title, String author, BigInteger authorId, Date date, String text, String subtitle, String imageUrl) {
    this.id = id;
    this.title = title;
    this.author = author;
    this.authorId = authorId;
    this.date = date;
    this.text = text;
    this.subtitle = subtitle;
    this.imageUrl = imageUrl;
  }

  public Article(String title, BigInteger authorId, String body, String subtitle, String imageUrl) {
    this.title = title;
    this.authorId = authorId;
    this.text = body;
    this.subtitle = subtitle;
    this.imageUrl = imageUrl;
  }

  public BigInteger getId() {
    return id;
  }

  public String getTitle() {
    return title;
  }

  public String getAuthor() {
    return author;
  }

  public BigInteger getAuthorId() {
    return authorId;
  }

  public Date getDate() {
    return date;
  }

  public String getText() {
    return text;
  }

  public String getSubtitle() {
    return subtitle;
  }

  public String getImageUrl() {
    return imageUrl;
  }

  public void setId(BigInteger id) {
    this.id = id;
  }

  public boolean update(String title, String subtitle, String body, String imageUrl) {
    if (title == null || title.isEmpty() ||
        subtitle == null || subtitle.isEmpty() ||
        body == null || body.isEmpty() ||
        imageUrl == null || imageUrl.isEmpty()) {

      return false;
    }

    this.title = title;
    this.subtitle = subtitle;
    this.text = body;
    this.imageUrl = imageUrl;
    return true;
  }
}
