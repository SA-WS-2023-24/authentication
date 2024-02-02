package com.htwberlin.userservice.core.domain.model;

import lombok.*;

@Builder
@Getter
@Setter
public class UserDTO {
  private String email;
  private String firstName;
  private String lastName;
  private Role role;
  private Address address;
}
