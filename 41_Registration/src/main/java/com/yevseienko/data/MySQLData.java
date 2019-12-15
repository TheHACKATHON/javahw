package com.yevseienko.data;

import com.yevseienko.data.config.MySQLDbConfig;
import com.yevseienko.models.User;

import java.sql.*;
import java.util.*;

public class MySQLData implements IData {
  //region enums

  private enum Procedure {
    GetGenders("sp_get_genders"),
    CreateUser("sp_create_user"),
    GetUsers("sp_get_users"),
    GetUserByCookie("sp_get_user_by_cookie"),
    GetUserByUsername("sp_get_user_by_username"),
    SetCookieToUser("sp_set_cookie_to_user"),
    GetUsernameCount("sp_get_username_count");

    private String title;

    Procedure(String title) {
      this.title = title;
    }

    @Override
    public String toString() {
      return title;
    }
  }

  //endregion
  //region fields

  private final MySQLDbConfig dbConfig;
  private static Map<String, Integer> genders;
  private static int defaultGender = 1;

  //endregion
  //region constructors

  public MySQLData(MySQLDbConfig config) {
    this.dbConfig = config;
  }

  static {

  }

  //endregion
  //region public methods

  @Override
  public ArrayList<User> getUsers() {
    return getUsers(Procedure.GetUsers.toString());
  }

  @Override
  public User getUserByCookie(String cookie) {
    List<User> users = getUsers(Procedure.GetUserByCookie.toString(), cookie);
    if (users != null && users.size() > 0) {
      return users.get(0);
    }
    return null;
  }

  @Override
  public User getUserByUsername(String username) {
    List<User> users = getUsers(Procedure.GetUserByUsername.toString(), username);
    if (users != null && users.size() > 0) {
      return users.get(0);
    }
    return null;
  }

  @Override
  public void addUser(User user) {
    int genderId = getGenders().getOrDefault(user.getGender(), defaultGender);
    execProcedure(
        Procedure.CreateUser.toString(),
        false,
        user.getUsername(),
        user.getPasswordHash(),
        user.getEmail(),
        user.getFirstName(),
        user.getLastName(),
        genderId,
        user.getBirth(),
        user.isSubscribe()
    );
  }

  @Override
  public void setCookieToUser(String cookie, String username) {
    execProcedure(
        Procedure.SetCookieToUser.toString(),
        false,
        username,
        cookie
    );
  }

  @Override
  public boolean isUsernameExist(String username) {
    List<Map<String, Object>> usernameCount = execProcedure(
        Procedure.GetUsernameCount.toString(),
        true,
        username
    );

    if(usernameCount != null && usernameCount.size() > 0){
      long count = (long)usernameCount.get(0).get("count");
      return count > 0;
    }

    return true;
  }

  //endregion
  //region private methods

  private ArrayList<User> getUsers(String procedureName, Object... params) {
    List<Map<String, Object>> users =
        execProcedure(
            procedureName,
            true,
            params
        );

    if (users != null && users.size() > 0) {
      ArrayList<User> usersResult = Mapper.User(users);
      if (usersResult.size() > 0) {
        return usersResult;
      }
    }
    return null;
  }

  private List<Map<String, Object>> execProcedure(String procedureName, boolean needResult, Object... params) {
    if (procedureName == null || "".equals(procedureName.trim())) {
      return null;
    }

    try {
      // load lib
      Class.forName("com.mysql.cj.jdbc.Driver");
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }

    String query = getProcedureQuery(procedureName, params);
    try (Connection connection = getConnection();
         CallableStatement preparedStmt = connection.prepareCall(query);
    ) {
      for (int i = 0; i < params.length; i++) {
        preparedStmt.setObject(i + 1, params[i]);
      }

      try (ResultSet rs = preparedStmt.executeQuery()) {
        if (needResult) {
          return getResult(rs);
        }
      } catch (Exception ex) {
        ex.printStackTrace();
      }
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return null;
  }

  private List<Map<String, Object>> getResult(ResultSet rs) throws SQLException {
    List<Map<String, Object>> resultList = new ArrayList<>();
    Map<String, Object> row;

    ResultSetMetaData metaData = rs.getMetaData();
    int columnCount = metaData.getColumnCount();

    while (rs.next()) {
      row = new HashMap<>();
      for (int i = 1; i <= columnCount; i++) {
        row.put(metaData.getColumnLabel(i), rs.getObject(i));
      }
      resultList.add(row);
    }
    return resultList;
  }

  private String getProcedureQuery(String procedureName, Object... params) {
    String queryParams = String.join("", Collections.nCopies(params.length, "?,"));

    if (queryParams.length() > 0) {
      queryParams = queryParams.substring(0, queryParams.length() - 1);
      // remove last ','
    }

    return String.format("CALL %s(%s)",
        procedureName,
        queryParams
    );
  }

  private Connection getConnection() throws SQLException {
    return DriverManager.getConnection(
        getConnectionString(),
        this.dbConfig.getDbUser(),
        this.dbConfig.getDbPassword());
  }

  private String getConnectionString() {
    // TODO: move engine to dbConfig
    return String.format("jdbc:mysql://%s/%s?serverTimezone=%s&characterEncoding=%s",
        this.dbConfig.getDbDomain(),
        this.dbConfig.getDbName(),
        this.dbConfig.getServerTimeZone(),
        this.dbConfig.getEncoding());
  }

  //endregion
  //region private static

  private Map<String, Integer> getGenders() {
    if (genders == null) {
      try (Connection connection = getConnection();
           CallableStatement preparedStmt = connection.prepareCall(
               String.format("CALL %s()", Procedure.GetGenders.toString()));
           ResultSet rs = preparedStmt.executeQuery()
      ) {
        genders = new HashMap<>();
        while (rs.next()) {
          genders.put(
              rs.getString(2),
              rs.getInt(1));
        }
      } catch (Exception ex) {
        ex.printStackTrace();
      }
    }
    return genders;
  }

  //endregion
}
