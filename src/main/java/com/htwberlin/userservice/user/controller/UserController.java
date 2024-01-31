package com.htwberlin.userservice.user.controller;

import com.htwberlin.userservice.core.domain.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.representations.AccessTokenResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/")
public class UserController {
    @Value("${keycloak.server-url}")
    private String kcServerUrl;

    @Value("${keycloak.realm}")
    private String kcRealm;

    @Value("${keycloak.client-id}")
    private String kcClientId;

    private Keycloak keycloak;

    public UserController(Keycloak keycloak) {
        this.keycloak = keycloak;
    }

    @PostMapping("/login")
    public ResponseEntity<AccessTokenResponse> login(@RequestBody User form) {
        AccessTokenResponse token;
        Keycloak keycloak = KeycloakBuilder.builder()
            .serverUrl(kcServerUrl)
            .realm(kcRealm)
            .clientId(kcClientId)
            .username(form.getEmail())
            .password(form.getPassword())
            .grantType("password")
            .build();
        token = keycloak.tokenManager().getAccessToken();
        keycloak.close();
        return ResponseEntity.ok(token);
    }

    @GetMapping("/user/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) throws ServletException {
        request.logout();
        this.keycloak.realm(kcRealm).users().get("init@test.com").logout();
        return ResponseEntity.ok("redirect:/v1/index");
    }

    @GetMapping("/user/profile")
    public ResponseEntity<String> profile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        OAuth2User principal = (OAuth2User) authentication.getPrincipal();
        String username = authentication.getName();
        String cartId = principal.getAttribute("cartId");
        return ResponseEntity.ok(username + "'s profile");
    }

    @GetMapping("/user/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("test");
    }

    @GetMapping("/index")
    public ResponseEntity<String> user() {
        return ResponseEntity.ok("index");
    }

    @GetMapping("/user/cart")
    public ResponseEntity<String> cart(HttpSession session) {
        String cartId;
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            OAuth2User principal = (OAuth2User) authentication.getPrincipal();
            cartId = principal.getAttribute("sub");
        } catch (Exception e) {
            cartId = session.getId();
        }
        return ResponseEntity.ok("cartId: " + cartId);
    }

}
