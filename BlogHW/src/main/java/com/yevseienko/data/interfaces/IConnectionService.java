package com.yevseienko.data.interfaces;

import com.yevseienko.data.configs.interfaces.IConfig;

import java.sql.Connection;

public interface IConnectionService {
  Connection getConnection();
  String getConnectionString();
  void setConfig(IConfig config);
}
