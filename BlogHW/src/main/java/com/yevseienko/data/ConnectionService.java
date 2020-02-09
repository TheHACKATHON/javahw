package com.yevseienko.data;

import com.yevseienko.data.configs.interfaces.IConfig;
import com.yevseienko.data.interfaces.IConnectionService;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Component
public class ConnectionService implements IConnectionService {
  private IConfig config;
  private String connectionString;

  @Override
  public Connection getConnection() {
    if (this.config == null){
      return null;
    }

    try {
      return DriverManager.getConnection(
          getConnectionString(),
          this.config.getDbUser(),
          this.config.getDbPassword());
    } catch (SQLException e) {
      e.printStackTrace();
      // TODO: log to db
    }
    return null;
  }

  @Override
  public String getConnectionString() {
    if(this.config == null){
      return null;
    }

    if(connectionString == null){
      connectionString = String.format("jdbc:mysql://%s/%s?serverTimezone=%s&characterEncoding=%s&noAccessToProcedureBodies=true",
          this.config.getDbDomain(),
          this.config.getDbName(),
          this.config.getServerTimeZone(),
          this.config.getEncoding());
    }

    return connectionString;
  }

  public void setConfig(IConfig config) {
    this.config = config;
  }
}
