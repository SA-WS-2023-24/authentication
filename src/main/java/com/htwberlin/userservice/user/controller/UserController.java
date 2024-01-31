package com.htwberlin.userservice.user.controller;

import com.htwberlin.userservice.core.domain.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.representations.AccessTokenResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/v1/")
public class UserController {
    @Value("${keycloak.server-url}")
    private String kcServerUrl;

    @Value("${keycloak.realm}")
    private String kcRealm;

    @Value("${keycloak.client-id}")
    private String kcClientId;

    @Value("${keycloak.logout-uri}")
    private String kcLogoutUri;

    private final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    private Keycloak keycloak;

    public UserController(Keycloak keycloak) {
        this.keycloak = keycloak;
    }

    @GetMapping("/login/{email}/{password}")
    public ResponseEntity<String> login(@PathVariable String email, @PathVariable String password) {
//    @PostMapping("/login")
//    public ResponseEntity<String> login(@RequestBody String email, @RequestBody String password) {
        LOGGER.debug("login: " + email + " " + password);
        String token;
        Keycloak loginKeycloak = KeycloakBuilder.builder()
            .serverUrl(kcServerUrl)
            .realm(kcRealm)
            .clientId(kcClientId)
            .username(email)
            .password(password)
            .grantType("password")
            .build();
        token = loginKeycloak.tokenManager().getAccessToken().getToken();
        LOGGER.debug("token: " + token);
        loginKeycloak.close();
        SecurityContextHolder.getContext().setAuthentication(null);
        return ResponseEntity.ok(token);
    }

    @GetMapping("/user/logout")
    public ResponseEntity<String> logout(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        OAuth2User user = (OAuth2User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String id = user.getAttribute("sub");
        this.keycloak.realm(kcRealm).users().get(id).logout();
        HttpSession session = req.getSession();
        session.invalidate();
        SecurityContextHolder.clearContext();
        return ResponseEntity.ok("logout");
    }

    @GetMapping("/user/profile")
    public ResponseEntity<String> profile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
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
