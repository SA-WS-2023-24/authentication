package com.htwberlin.userservice.core.domain.service.impl;

import com.htwberlin.userservice.core.domain.service.interfaces.IUserService;

import jakarta.servlet.http.HttpSession;
import org.keycloak.admin.client.Keycloak;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.security.core.context.SecurityContextHolder;

@Service
public class UserService implements IUserService {
    private Keycloak keycloak;

    public UserService(Keycloak keycloak) {
      this.keycloak = keycloak;
    }

    @Override
    public String getUsername() {
        Authentication auth = this.getAuthentication();
        String username = auth.getName();
        return username;
    }

    @Override
    public String getCartId(HttpSession session) {
        String username = this.getUsername();
        if (username == null) {
            return session.getId();
        }
        return this.getKeycloakId();
    }

    @Override
    public void logoutUser(HttpSession session, String realm) {
        this.logoutKeycloakUser(realm);
        session.invalidate();
        SecurityContextHolder.clearContext();
    }

    private SecurityContext getSecurityContext() {
        SecurityContext context = SecurityContextHolder.getContext();
        return context;
    }

    private Authentication getAuthentication() {
        Authentication auth = this.getSecurityContext().getAuthentication();
        return auth;
    }

    private OAuth2User getOAuth2User() {
        Authentication auth = this.getAuthentication();
        OAuth2User user = (OAuth2User) auth.getPrincipal();
        return user;
    }

    private String getKeycloakId() {
        OAuth2User user = this.getOAuth2User();
        String keycloakId = user.getAttribute("sub");
        return keycloakId;
    }

    private void logoutKeycloakUser(String realm) {
        String id = this.getKeycloakId();
        this.keycloak.realm(realm).users().get(id).logout();
    }

    @Override
    public void loginUser(String email, String password) {

    }
}
