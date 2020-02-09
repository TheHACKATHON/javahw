package com.yevseienko.data.interfaces;

import com.yevseienko.models.user.BasicUser;

public interface IAuthDataService {
  BasicUser login(String email, String password);
}
