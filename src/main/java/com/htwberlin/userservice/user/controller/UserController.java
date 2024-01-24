package com.htwberlin.userservice.user.controller;

import com.htwberlin.userservice.core.domain.service.interfaces.IKeycloakAdminClientService;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("/v1/")
public class UserController {

    private final IKeycloakAdminClientService keycloakService;

    @Autowired
    public UserController(IKeycloakAdminClientService keycloakService) {
        this.keycloakService = keycloakService;
    }

    @PostMapping(path = "/user")
    public @ResponseBody String create() {
        OAuth2User user = ((OAuth2User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        this.keycloakService.createUser();
        return "user created";
    }

    @GetMapping("/test")
    public @ResponseBody String test() {
        return this.keycloakService.getAccessToken();
    }
}
