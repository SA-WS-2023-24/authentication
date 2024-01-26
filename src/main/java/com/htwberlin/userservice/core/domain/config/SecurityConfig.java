package com.htwberlin.userservice.core.domain.config;

import org.keycloak.adapters.authorization.integration.jakarta.ServletPolicyEnforcerFilter;
import org.keycloak.adapters.authorization.spi.ConfigurationResolver;
import org.keycloak.adapters.authorization.spi.HttpRequest;
import org.keycloak.representations.adapters.config.PolicyEnforcerConfig;
import org.keycloak.util.JsonSerialization;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.web.authentication.BearerTokenAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;

import java.io.IOException;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
  String jwkSetUri;

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.
        authorizeRequests((authorize) -> authorize
            .anyRequest()
            .authenticated()
        )
        .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt)
        .addFilterAfter(createPolicyEnforceFilter(), BearerTokenAuthenticationFilter.class);

    return http.build();
  }

  private ServletPolicyEnforcerFilter createPolicyEnforceFilter() {
    PolicyEnforcerConfig config;

    try {
      config = JsonSerialization
          .readValue(
              getClass().getResourceAsStream("/policy-enforcer.json"),
          PolicyEnforcerConfig.class);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return new ServletPolicyEnforcerFilter(new ConfigurationResolver() {
      @Override
      public PolicyEnforcerConfig resolve(HttpRequest httpRequest) {
        return config;
      }
    });
  }

  @Bean
  JwtDecoder jwtDecoder() {
    return NimbusJwtDecoder.withJwkSetUri(this.jwkSetUri).build();
  }
}