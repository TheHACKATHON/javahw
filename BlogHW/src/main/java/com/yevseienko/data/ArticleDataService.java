package com.yevseienko.data;

import com.yevseienko.data.configs.DbConfig;
import com.yevseienko.data.interfaces.IConnectionService;
import com.yevseienko.data.interfaces.IArticleDataService;
import com.yevseienko.data.mappers.MapHelper;
import com.yevseienko.data.mappers.ArticleMapper;
import com.yevseienko.helpers.AppContext;
import com.yevseienko.helpers.Dictionary;
import com.yevseienko.models.Article;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.List;
import java.util.Map;


@Component
public class ArticleDataService implements IArticleDataService {
  private static final int POSTS_ON_PAGE = 10;
  private IConnectionService connectionService;

  @Override
  public List<Article> getArticles() {
    try (Connection connection = getConnection();
         CallableStatement preparedStmt = connection.prepareCall(String.format("call %s(?)", Dictionary.StoredProcedures.GET_ARTICLES));
    ) {
      preparedStmt.setInt(1, POSTS_ON_PAGE);

      try (ResultSet rs = preparedStmt.executeQuery()) {
        List<Map<String, Object>> listResults = MapHelper.getList(rs);
        return ArticleMapper.toArticle(listResults);
      } catch (Exception ex) {
        ex.printStackTrace();
      }
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return null;
  }

  @Override
  public Article getArticle(BigInteger id) {
    try (Connection connection = getConnection();
         CallableStatement preparedStmt = connection.prepareCall(String.format("call %s(?)", Dictionary.StoredProcedures.GET_ARTICLE));
    ) {
      preparedStmt.setLong(1, id.longValue());

      try (ResultSet rs = preparedStmt.executeQuery()) {
        List<Map<String, Object>> listResults = MapHelper.getList(rs);
        List<Article> articles = ArticleMapper.toArticle(listResults);
        if (articles != null && !articles.isEmpty()) {
          return articles.get(0);
        }
        return null;

      } catch (Exception ex) {
        ex.printStackTrace();
      }
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return null;
  }

  @Override
  public BigInteger createArticle(Article article) {
    try (Connection connection = getConnection();
         CallableStatement preparedStmt = connection.prepareCall(String.format("call %s(?, ?, ?, ?, ?)", Dictionary.StoredProcedures.CREATE_ARTICLE));
    ) {
      preparedStmt.setString(1, article.getTitle());
      preparedStmt.setString(2, article.getSubtitle());
      preparedStmt.setString(3, article.getImageUrl());
      preparedStmt.setString(4, article.getText());
      preparedStmt.setLong(5, article.getAuthorId().longValue());

      try (ResultSet rs = preparedStmt.executeQuery()) {
        if(rs.next()){
          return (BigInteger)rs.getObject(1);
        }
      } catch (Exception ex) {
        ex.printStackTrace();
      }
    } catch (Exception ex) {
      ex.printStackTrace();
    }

    return BigInteger.valueOf(0L);
  }

  @Override
  public boolean updateArticle(Article article) {
    try (Connection connection = getConnection();
         CallableStatement preparedStmt = connection.prepareCall(String.format("call %s(?, ?, ?, ?, ?)", Dictionary.StoredProcedures.UPDATE_ARTICLE));
    ) {
      preparedStmt.setLong(1, article.getId().longValue());
      preparedStmt.setString(2, article.getTitle());
      preparedStmt.setString(3, article.getSubtitle());
      preparedStmt.setString(4, article.getImageUrl());
      preparedStmt.setString(5, article.getText());
      preparedStmt.execute();
      return true;
    } catch (Exception ex) {
      ex.printStackTrace();
    }

    return false;
  }

  @Override
  public boolean deleteArticle(BigInteger id) {
    try (Connection connection = getConnection();
         CallableStatement preparedStmt = connection.prepareCall(String.format("call %s(?)", Dictionary.StoredProcedures.DELETE_ARTICLE));
    ) {
      preparedStmt.setLong(1, id.longValue());
      preparedStmt.execute();
      return true;
    } catch (Exception ex) {
      ex.printStackTrace();
    }

    return false;
  }

  private Connection getConnection() {
    if (connectionService == null) {
      connectionService = AppContext.getBean(IConnectionService.class);
      connectionService.setConfig(DbConfig.getDefault());
    }

    return connectionService.getConnection();
  }
}
