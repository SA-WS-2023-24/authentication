package com.htwberlin.userservice.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
