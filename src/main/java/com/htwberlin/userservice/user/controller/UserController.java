package com.htwberlin.userservice.user.controller;

import com.htwberlin.userservice.core.domain.service.interfaces.IKeycloakAdminClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/")
public class UserController {

    private final IKeycloakAdminClientService keycloakService;

    @Autowired
    public UserController(IKeycloakAdminClientService keycloakService) {
        this.keycloakService = keycloakService;
    }

    @PostMapping(path = "/user")
    public @ResponseBody void create() {
        keycloakService.createUser();
    }

    @GetMapping("/test")
    public @ResponseBody String test() {
        return "Hello World";
    }
}
