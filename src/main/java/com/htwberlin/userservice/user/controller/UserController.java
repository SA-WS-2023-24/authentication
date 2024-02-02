package com.htwberlin.userservice.user.controller;

import com.htwberlin.userservice.core.domain.model.Address;
import com.htwberlin.userservice.core.domain.model.User;
import com.htwberlin.userservice.core.domain.model.UserDTO;
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

    private final IUserService userService;

    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user/logout")
    public ResponseEntity logout(HttpSession session) {
        this.userService.logoutUser(session);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/user/login")
    public @ResponseBody String login(@RequestBody User user) {
        return this.userService.loginUser(user.getEmail(), user.getPassword());
    }

    @GetMapping("/user/profile")
    public @ResponseBody UserDTO profile() {
        return this.userService.getUser();
    }

    @GetMapping("/user/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("test");
    }

    @GetMapping("/user/cart")
    public @ResponseBody String cart(HttpSession session) {
        return this.userService.getCartId(session);
    }

    @PostMapping("/user/address/add")
    public ResponseEntity<Map<String, String>> address(@RequestBody Address address) {
        Map<String, String> mapAddr = this.userService.setAddress(address);
        return ResponseEntity.ok(mapAddr);
    }
}
