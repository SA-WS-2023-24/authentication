package com.htwberlin.userservice.core.domain.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
        .authorizeHttpRequests((authorize) -> authorize
            .requestMatchers("/user/login", "/user/authorize", "/user/access/*")
            .permitAll()
            .anyRequest()
            .authenticated()
        )
        .oauth2Login(oauth2 -> oauth2
            .defaultSuccessUrl("/user/profile")
            .loginPage("/user/login")
            .authorizationEndpoint()
            .baseUri("/user/authorize")
            .and()
            .redirectionEndpoint()
            .baseUri("/user/access/*")
        );

    return http.build();
  }
}