package com.yevseienko.data.config;

public class MySQLDbConfig {
  //region fields

  private final String dbDomain;
  private final String dbName;
  private final String serverTimeZone;
  private final String encoding;
  private final String dbUser;
  private final String dbPassword;

  //endregion
  //region constructors

  public MySQLDbConfig(String dbDomain, String dbName, String serverTimeZone, String encoding, String dbUser, String dbPassword) {
    this.dbDomain = dbDomain;
    this.dbName = dbName;
    this.serverTimeZone = serverTimeZone;
    this.encoding = encoding;
    this.dbUser = dbUser;
    this.dbPassword = dbPassword;
  }

  //endregion
  //region getters

  public String getDbDomain() {
    return dbDomain;
  }

  public String getDbName() {
    return dbName;
  }

  public String getServerTimeZone() {
    return serverTimeZone;
  }

  public String getEncoding() {
    return encoding;
  }

  public String getDbUser() {
    return dbUser;
  }

  public String getDbPassword() {
    return dbPassword;
  }

  //endregion
}
