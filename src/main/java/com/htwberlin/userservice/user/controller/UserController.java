//package com.htwberlin.userservice.user.controller;
//
//import com.htwberlin.userservice.core.domain.model.User;
//import com.htwberlin.userservice.core.domain.service.interfaces.IUserService;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.UUID;
//
//@RestController
//@RequestMapping("/v1/")
//public class UserController {
//
//    private final IUserService userService;
//
//    public UserController(IUserService userService) {
//        this.userService = userService;
//    }
//
//    @PostMapping(path = "/user")
//    public @ResponseBody User create(@RequestBody User user) {
//        return userService.createUser(user);
//    }
//
//    @GetMapping("/user/{id}")
//    public User getUser(@PathVariable UUID id) {
//        return userService.getUser(id);
//    }
//
//    @PutMapping(path = "/user")
//    public @ResponseBody User update(@RequestBody User user) {
//        return userService.updateUser(user);
//    }
//
//    @DeleteMapping(path = "/user/{id}")
//    public @ResponseBody void delete(@RequestBody User user) {
//        userService.deleteUser(user);
//    }
//
//    @GetMapping("/users")
//    public @ResponseBody Iterable<User> getUsers() {
//        return userService.getAllUsers();
//    }
//}
