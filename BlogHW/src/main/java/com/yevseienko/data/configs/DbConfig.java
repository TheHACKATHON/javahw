package com.yevseienko.data.configs;

import com.yevseienko.data.configs.interfaces.IConfig;

public class DbConfig implements IConfig {
  private final String dbDomain;
  private final String dbName;
  private final String serverTimeZone;
  private final String encoding;
  private final String dbUser;
  private final String dbPassword;

  public DbConfig(String dbDomain, String dbName, String serverTimeZone, String encoding, String dbUser, String dbPassword) {
    this.dbDomain = dbDomain;
    this.dbName = dbName;
    this.serverTimeZone = serverTimeZone;
    this.encoding = encoding;
    this.dbUser = dbUser;
    this.dbPassword = dbPassword;
  }

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

  // TODO: move to cfg
  public static DbConfig getDefault(){
    return new DbConfig(
        "step.netspasibo.space",
        "article_db",
        "Europe/Kiev",
        "utf8",
        "article_user",
        "@superpassword");
  }
}
