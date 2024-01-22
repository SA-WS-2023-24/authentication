package com.htwberlin.userservice.user.controller;

import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/v1/")
public class KeycloakController {
  @RequestMapping("/auth")
  public String handleRequest(Principal principal) {
    KeycloakAuthenticationToken token = (KeycloakAuthenticationToken) principal;
    KeycloakPrincipal keycloakPrincipal = (KeycloakPrincipal) token.getPrincipal();
    KeycloakSecurityContext session = keycloakPrincipal.getKeycloakSecurityContext();
    return token.toString();
  }
}
