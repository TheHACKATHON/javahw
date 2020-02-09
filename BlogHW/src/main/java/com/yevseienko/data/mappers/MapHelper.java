package com.yevseienko.data.mappers;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapHelper {
  public static List<Map<String, Object>> getList(ResultSet rs) throws SQLException {
    List<Map<String, Object>> resultList = new ArrayList<>();
    Map<String, Object> row;

    ResultSetMetaData metaData = rs.getMetaData();
    int columnCount = metaData.getColumnCount();

    while (rs.next()) {
      row = new HashMap<String, Object>();
      for (int i = 1; i <= columnCount; i++) {
        row.put(metaData.getColumnLabel(i), rs.getObject(i));
      }
      resultList.add(row);
    }
    return resultList;
  }
}
