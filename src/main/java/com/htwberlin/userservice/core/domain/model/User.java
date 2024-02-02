package com.htwberlin.userservice.core.domain.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class User {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private Role role;
}