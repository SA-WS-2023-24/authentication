package com.htwberlin.userservice.user.controller;

import com.htwberlin.userservice.core.domain.service.interfaces.IKeycloakAdminClientService;

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
        this.keycloakService.createUser();
        return "user created";
    }

    @GetMapping("/test")
    public @ResponseBody String test() {
        return this.keycloakService.getAccessToken();
    }
}
