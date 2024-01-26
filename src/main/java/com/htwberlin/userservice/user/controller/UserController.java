package com.htwberlin.userservice.user.controller;

//import com.htwberlin.userservice.core.domain.service.interfaces.IKeycloakAdminClientService;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
//@RequestMapping("/v1/")
public class UserController {

//    private final IKeycloakAdminClientService keycloakService;
//
//    @Autowired
//    public UserController(IKeycloakAdminClientService keycloakService) {
//        this.keycloakService = keycloakService;
//    }

//    @PostMapping(path = "/user")
//    public @ResponseBody String create() {
//        OAuth2User user = ((OAuth2User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
////        this.keycloakService.createUser();
//        return "user created: " + user.getName();
//    }
//
//    @GetMapping("/test")
//    public @ResponseBody String test() {
//        return "test";
//    }

    @GetMapping("/")
    public String index(@AuthenticationPrincipal Jwt jwt) {
        return String.format("Hello, %s!", jwt.getClaimAsString("preferred_username"));
    }

    @GetMapping("/protected/premium")
    public String premium(@AuthenticationPrincipal Jwt jwt) {
        return String.format("Hello, %s! You are a premium user!",
            jwt.getClaimAsString("preferred_username"));
    }
}
