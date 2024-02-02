package com.htwberlin.userservice.core.domain.service.interfaces;

import com.htwberlin.userservice.core.domain.model.Address;
import com.htwberlin.userservice.core.domain.model.UserDTO;

import jakarta.servlet.http.HttpSession;

import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;

import java.util.Map;

public interface IUserService {
  JwtAuthenticationConverter getJwtAuthenticationConverter();

  UserDTO getUser();
  String getUsername();
  String getCartId(HttpSession session);
  void logoutUser(HttpSession session);
  String loginUser(String email, String password);
  Map<String, String> setAddress(Address address);

  void deleteAddress();
}
