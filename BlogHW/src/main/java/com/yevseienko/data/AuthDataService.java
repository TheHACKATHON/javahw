package com.yevseienko.data;

import com.yevseienko.data.configs.DbConfig;
import com.yevseienko.data.interfaces.IAuthDataService;
import com.yevseienko.data.interfaces.IConnectionService;
import com.yevseienko.data.mappers.MapHelper;
import com.yevseienko.data.mappers.UserMapper;
import com.yevseienko.helpers.AppContext;
import com.yevseienko.helpers.Dictionary;
import com.yevseienko.helpers.Hasher;
import com.yevseienko.models.user.BasicUser;
import com.yevseienko.models.user.User;
import org.springframework.stereotype.Component;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.List;
import java.util.Map;

@Component
public class AuthDataService implements IAuthDataService {
  private IConnectionService connectionService;

  @Override
  public BasicUser login(String email, String password) {
    try (Connection connection = getConnection();
         CallableStatement preparedStmt = connection.prepareCall(String.format("call %s(?)", Dictionary.StoredProcedures.GET_USER_BY_EMAIL));
    ) {
      preparedStmt.setString(1, email);

      try (ResultSet rs = preparedStmt.executeQuery()) {
        List<Map<String, Object>> listResults = MapHelper.getList(rs);
        List<User> users = UserMapper.toUsers(listResults);
        if(users != null && !users.isEmpty()){
          User user = users.get(0);
          if(Hasher.verifyMd5(password, user.getPassword())){
            return UserMapper.toBasic(user);
          }
        }
      } catch (Exception ex) {
        ex.printStackTrace();
      }
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return null;
  }

  private Connection getConnection(){
    if(connectionService == null){
      connectionService = AppContext.getBean(IConnectionService.class);
      connectionService.setConfig(DbConfig.getDefault());
    }

    return connectionService.getConnection();
  }
}
