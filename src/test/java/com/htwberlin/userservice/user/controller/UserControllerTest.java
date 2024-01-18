package com.htwberlin.userservice.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.htwberlin.userservice.core.domain.model.User;
import com.htwberlin.userservice.core.domain.service.exception.UserNotFoundException;
import com.htwberlin.userservice.core.domain.service.impl.UserService;
import com.htwberlin.userservice.user.advice.UserNotFoundAdvice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Mock
    UserService userService;

    @InjectMocks
    UserController controller;

    private MockMvc mockMvc;

    final List<User> users = new LinkedList<>();

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setup() {
//        mockMvc = MockMvcBuilders.standaloneSetup(controller)
//                .setControllerAdvice(UserNotFoundAdvice.class)
//                .build();
//
//        // 20 random users
//        for (int i = 0; i < 20; i++) {
//            User user = User.builder()
//                .id(UUID.randomUUID())
//                .firstName("Firstname " + i)
//                .lastName("Lastname " + i)
//                .email("email" + i + "@example.com")
//                .password("password" + i)
//                .birthDate(LocalDate.of(1990, 1, 1))
//                .paymentMethod("Paypal")
//                .isSubscribedToNewsletter(true)
//                .orderHistoty(null)
//                .build();
//
//            users.add(user);
//        }
    }

    @Test
    void getAllUsersTest() throws Exception {
//        when(userService.getAllUsers()).thenReturn(users);
//        String usersJson = objectMapper.writeValueAsString(users);
//
//        MvcResult res = mockMvc.perform(get("/v1/users")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andReturn();
//
//        String responseBody = res.getResponse().getContentAsString();
//
//        assertEquals(usersJson, responseBody);
    }

    @Test
    void getUserNotFoundTest() throws Exception {
//        UUID randomUuid = UUID.randomUUID();
//        when(userService.getUser(any(UUID.class))).thenThrow(new UserNotFoundException(randomUuid));
//
//        mockMvc.perform(get("/v1/user/{id}", randomUuid)
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isNotFound())
//                .andExpect(content().string("Could not find user " + randomUuid))
//                .andReturn();
    }


    @Test
    void getUserFoundTest() throws Exception {
//        User user = users.get(new Random().nextInt(users.size()));
//        String userJson = objectMapper.writeValueAsString(user);
//        when(userService.getUser(eq(user.getId()))).thenReturn(user);
//
//        MvcResult result = mockMvc.perform(get("/v1/user/{id}", user.getId())
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andReturn();
//
//        String content = result.getResponse().getContentAsString();
//
//        assertEquals(userJson, content);
    }

    @Test
    void postUserTest() throws Exception {
//
//        User user = users.get(new Random().nextInt(users.size()));
//        String userJson = objectMapper.writeValueAsString(user);
//        when(userService.createUser(user)).thenReturn(user);
//
//        MvcResult result = mockMvc.perform(post("/v1/user")
//                        .content(userJson)
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andReturn();
//
//        String content = result.getResponse().getContentAsString();
//
//        assertEquals(userJson, content);
    }


    @Test
    void postUserBadRequestTest() throws Exception {
//        mockMvc.perform(post("/v1/user")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isBadRequest())
//                .andReturn();
    }

    @Test
    void putBadRequestTest() throws Exception {
//        mockMvc.perform(put("/v1/user")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isBadRequest())
//                .andReturn();
    }

    @Test
    void putUserTest() throws Exception {
//        User user = users.get(users.size() / 2);
//        String userJson = objectMapper.writeValueAsString(user);
//        when(userService.updateUser(any(User.class))).thenReturn(user);
//
//        MvcResult result = mockMvc.perform(put("/v1/user")
//                        .content(userJson)
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andReturn();
//
//        String responseContent = result.getResponse().getContentAsString();
//
//        assertEquals(userJson, responseContent);
    }

    @Test
    void putUserNotFoundTest() throws Exception {
//        User user = users.get(0);
//        String userJson = objectMapper.writeValueAsString(user);
//        String notFoundResponse = "Could not find user " + user.getId().toString();
//
//        when(userService.updateUser(any(User.class))).thenThrow(new UserNotFoundException(user.getId()));
//
//        MvcResult result = mockMvc.perform(put("/v1/user")
//                        .content(userJson)
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isNotFound())
//                .andReturn();
//
//        String responseContent = result.getResponse().getContentAsString();
//
//        assertEquals(notFoundResponse, responseContent);
    }
}
