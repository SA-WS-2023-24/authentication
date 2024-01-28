package com.htwberlin.userservice.user.controller;

import com.htwberlin.userservice.core.domain.model.User;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.AccessTokenResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
//    @GetMapping("/login")
//    public ResponseEntity<String> login() {
//        return ResponseEntity.ok("Login");
//    }
//
//    @GetMapping("/authorize")
//    public ResponseEntity<String> authorize() {
//        return ResponseEntity.ok("Authorize");
//    }

    @GetMapping("/access/{param}")
    public ResponseEntity<OAuth2User> access(@PathVariable String param, OAuth2AuthenticationToken token) {
        return ResponseEntity.ok(token.getPrincipal());
    }

    @GetMapping("/profile")
    public ResponseEntity<String> profile() {
        return ResponseEntity.ok("Profile");
    }

    @GetMapping("/")
    public ResponseEntity<String> user() {
        return ResponseEntity.ok("User");
    }

}
