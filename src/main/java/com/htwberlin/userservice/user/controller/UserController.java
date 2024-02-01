package com.htwberlin.userservice.user.controller;

import com.htwberlin.userservice.core.domain.model.Address;
import com.htwberlin.userservice.core.domain.service.interfaces.IUserService;

import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Value;

import java.util.Map;

@RestController
@RequestMapping("/v1/")
public class UserController {

    @Value("${keycloak.realm}")
    private String kcRealm;

    private final IUserService userService;

    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user/logout")
    public ResponseEntity logout(HttpServletRequest req) {
        HttpSession session = req.getSession();
        this.userService.logoutUser(session, kcRealm);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/user/profile")
    public ResponseEntity<String> profile() {
        String username = this.userService.getUsername();
        return ResponseEntity.ok(username + "'s profile");
    }

    @GetMapping("/user/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("test");
    }

    @GetMapping("/user/cart")
    public ResponseEntity<Map<String, String>> cart(HttpSession session) {
        Map<String, String> cartId = this.userService.getCartId(session);
        return ResponseEntity.ok(cartId);
    }

    @PostMapping("/user/address/add")
    public ResponseEntity<Map<String, String>> address(@RequestBody Address address) {
        Map<String, String> mapAddr = this.userService.setAddress(address, kcRealm);
        return ResponseEntity.ok(mapAddr);
    }
}
