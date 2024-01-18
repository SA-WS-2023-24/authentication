package com.htwberlin.userservice.core.domain.service.exception;

import java.util.UUID;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(UUID id) {
      super("Could not find user with ID: " + id.toString());
    }
}
