package com.htwberlin.userservice.core.domain.config;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KeycloakConfig {

  @Value("${keycloak.server-url}")
  private String kcServerUrl;

  @Value("${keycloak.init-realm}")
  private String kcInitRealm;

  @Value("${keycloak.init-username}")
  private String kcInitUsername;

  @Value("${keycloak.init-password}")
  private String kcInitPassword;

  @Value("${keycloak.init-client-id}")
  private String kcInitClientId;

  @Bean
  public Keycloak keycloak() {
    return KeycloakBuilder.builder()
        .serverUrl(kcServerUrl)
        .realm(kcInitRealm)
        .username(kcInitUsername)
        .password(kcInitPassword)
        .clientId(kcInitClientId)
        .build();
  }
}