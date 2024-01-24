//package com.htwberlin.userservice.core.domain.config;
//
//import org.keycloak.admin.client.Keycloak;
//import org.keycloak.admin.client.KeycloakBuilder;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class KeycloakConfig {
//  @Value("${keycloak.auth-server-url}") String serverUrl;
//  @Value("${keycloak.realm}") String realm;
//  @Value("${keycloak.resource}") String clientId;
//  @Value("$keycloak.username") String username;
//  @Value("$keycloak.password") String password;
//  @Value("$keycloak.credentials.secret") String secret;
//
//  @Bean
//  public Keycloak keycloak() {
//    return KeycloakBuilder.builder()
//        .serverUrl(serverUrl)
//        .realm(realm)
//        .clientId(clientId)
//        .username(username)
//        .password(password)
//        .clientSecret(secret)
//        .build();
//  }
//}
