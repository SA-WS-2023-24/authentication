package com.htwberlin.userservice.core.domain.service.interfaces;

import com.htwberlin.userservice.core.domain.model.User;
import com.htwberlin.userservice.core.domain.model.Address;

import jakarta.servlet.http.HttpSession;

import java.util.Map;

public interface IUserService {
  User getUser();
  String getUsername();
  Map<String, String> getCartId(HttpSession session);
  void logoutUser(HttpSession session, String realm);
  void loginUser(String email, String password);
  Map<String, String> setAddress(Address address, String realm);
  void deleteAddress(Address adress);
}
