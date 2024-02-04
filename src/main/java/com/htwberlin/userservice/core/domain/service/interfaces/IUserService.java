package com.htwberlin.userservice.core.domain.service.interfaces;

import com.htwberlin.userservice.core.domain.model.Address;
import com.htwberlin.userservice.core.domain.model.User;
import com.htwberlin.userservice.core.domain.model.UserDTO;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;

import java.util.Map;

public interface IUserService {
  JwtAuthenticationConverter getJwtAuthenticationConverter();

  UserDTO getUser(String accessToken, String refreshToken);
  String getUsername();
  String getCartId(HttpSession session);
  void logoutUser(HttpSession session);
  Map<String, String> loginUser(User user);
  Map<String, String> setAddress(Address address);

  void deleteAddress();
}
