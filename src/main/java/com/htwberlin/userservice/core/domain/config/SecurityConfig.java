package com.htwberlin.userservice.core.domain.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import java.io.IOException;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
  @Value("${keycloak.redirect-uri}")
  private String kcRedirectUri;

  @Value("${keycloak.logout-uri}")
  private String kcLogoutUri;

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
        .authorizeHttpRequests((authorize) -> authorize
            .requestMatchers("/v1/login/**", "v1/index")
            .permitAll()
            .anyRequest()
            .authenticated()
        )
//        .formLogin(formLogin -> formLogin
//            .loginPage("/v1/login")
//            .permitAll()
//        )
        .oauth2Login()
        .and()
        .logout(logout -> logout
            .logoutUrl("/v1/user/logout")
//            .logoutSuccessHandler(this::logoutSuccessHandler)
//        )
//        .oauth2Login(oauth2 -> oauth2
//            .loginProcessingUrl("/v1/login")
//            .userInfoEndpoint(userInfo -> userInfo
//                .oidcUserService(oidcUserService()))
        );

    return http.build();
  }

//  @Bean
//  public OAuth2UserService<OidcUserRequest, OidcUser> oidcUserService() {
//    final DefaultOAuth2UserService delegate = new DefaultOAuth2UserService();
//    return (userRequest) -> {
//      OidcUser oidcUser = (OidcUser) delegate.loadUser(userRequest);
//      return oidcUser;
//    };
//  }

  private void logoutSuccessHandler(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
    try {
      response.sendRedirect(kcLogoutUri);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}