package com.htwberlin.userservice.core.domain.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class CookieAccessTokenFilter extends OncePerRequestFilter {
  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    Cookie[] cookies = request.getCookies();
    if (cookies != null) {
      for (Cookie cookie : cookies) {
        if (cookie.getName().equals("access_token")) {
          String authHeader = "Bearer " + cookie.getValue();
          request = new HttpServletRequestWrapper(request) {
            @Override
            public String getHeader(String name) {
              if (name.equals("Authorization")) {
                return authHeader;
              }
              return super.getHeader(name);
            }
          };
        }
        break;
      }
    }
    filterChain.doFilter(request, response);
  }
}
