package com.htwberlin.userservice.core.domain.service.interfaces;

import jakarta.servlet.http.HttpSession;

public interface IUserService {
  String getUsername();
  String getCartId(HttpSession session);
  void logoutUser(HttpSession session, String realm);
  void loginUser(String email, String password);
}
