package com.htwberlin.userservice.core.domain.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
  @Value("${keycloak.redirect-uri}")
  private String kcRedirectUri;

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
        .csrf()
            .disable()
        .authorizeHttpRequests((authorize) -> authorize
//            .requestMatchers("/user/login", "/user/auth", "/user/access/*")
            .requestMatchers("/v1/product/**")
            .permitAll()
            .anyRequest()
            .authenticated()
        )
        .oauth2Login(oauth2 -> oauth2
//            .loginPage("/user/login")
//            .authorizationEndpoint()
//                .baseUri("/user/auth")
//                .and()
//            .redirectionEndpoint()
//                .baseUri("/user/access/*")
//                .and()
            .defaultSuccessUrl("/user/profile", true)
        );

    return http.build();
  }
}