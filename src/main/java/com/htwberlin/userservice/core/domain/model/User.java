package com.htwberlin.userservice.core.domain.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class User {
    private String username;
    private String password;
    private Role role = Role.USER;
}