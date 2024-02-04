package com.htwberlin.userservice.user.controller;

import com.htwberlin.userservice.core.domain.model.Address;
import com.htwberlin.userservice.core.domain.model.User;
import com.htwberlin.userservice.core.domain.model.UserDTO;
import com.htwberlin.userservice.core.domain.service.interfaces.IUserService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Value;

import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/v1/")
public class UserController {

    private final static Logger LOGGER = LoggerFactory.getLogger(UserController.class);

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
public ResponseEntity<Map<String, String>> login(@RequestBody User user, HttpServletResponse res) {
    Map<String, String> tokens = this.userService.loginUser(user);

    Cookie accessTokenCookie = new Cookie("access_token", tokens.get("access_token"));
    accessTokenCookie.setHttpOnly(true);
    accessTokenCookie.setPath("/");
    accessTokenCookie.setDomain("localhost");
    res.addCookie(accessTokenCookie);
//    String accessTokenCookie = String.format("access_token=%s; HttpOnly; Secure; SameSite=None; Path=/; Domain=localhost", tokens.get("access_token"));
//    res.addHeader("Set-Cookie", accessTokenCookie);

    Cookie refreshTokenCookie = new Cookie("refresh_token", tokens.get("refresh_token"));
    refreshTokenCookie.setHttpOnly(true);
    refreshTokenCookie.setPath("/");
    refreshTokenCookie.setDomain("localhost");
    res.addCookie(refreshTokenCookie);
//    String refreshTokenCookie = String.format("refresh_token=%s; HttpOnly; Secure; SameSite=None; Path=/; Domain=localhost", tokens.get("refresh_token"));
//    res.addHeader("Set-Cookie", refreshTokenCookie);

    return ResponseEntity.ok(tokens);
}
    @GetMapping("/user/profile")
    public ResponseEntity<UserDTO> profile(@CookieValue(name = "access_token", required = false) String accessToken, @CookieValue(name = "refresh_token", required = false) String refreshToken) {
        if (accessToken == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        UserDTO user = this.userService.getUser(accessToken, refreshToken);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/user/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("test");
    }

    @GetMapping("/user/cart")
    public @ResponseBody Map<String, String> cart(HttpServletRequest req) {
        LOGGER.debug("requesting cartId...");
        HttpSession session = req.getSession();
        String cartId = this.userService.getCartId(session);
        return Map.of("cartdId", cartId);
    }

    @PostMapping("/user/address/add")
    public ResponseEntity<Map<String, String>> address(@RequestBody Address address) {
        Map<String, String> mapAddr = this.userService.setAddress(address);
        return ResponseEntity.ok(mapAddr);
    }
}
