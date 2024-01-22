//package com.htwberlin.userservice.core.domain.service.impl;
//
//import com.htwberlin.userservice.core.domain.model.User;
//
//public class KeycloakUserBuilder {
//  private User user;
//
//  public KeycloakUserBuilder(User user) {
//    this.user = user;
//  }
//
//  public String buildPOSTRequest() {
//    StringBuilder sb = new StringBuilder();
//    sb.append("username:");
//    sb.append(user.getEmail());
//    sb.append("enabled");
//    return sb.toString();
//  }
//}
