//package com.htwberlin.userservice.core.domain.service.impl;
//
//import com.htwberlin.userservice.core.domain.model.User;
//import com.htwberlin.userservice.core.domain.service.exception.UserNotFoundException;
//import com.htwberlin.userservice.core.domain.service.interfaces.IUserRepository;
//import com.htwberlin.userservice.core.domain.service.interfaces.IUserService;
//import org.springframework.stereotype.Service;
//
//import java.util.Optional;
//import java.util.UUID;
//
//@Service
//public class UserService implements IUserService {
//
//    private final IUserRepository userRepository;
//
//    UserService(IUserRepository userRepository) {
//        this.userRepository = userRepository;
//    }
//
//    public User createUser(User user) {
//        return userRepository.save(user);
//    }
//
//    @Override
//    public User updateUser(User user) throws UserNotFoundException {
//        if (!userRepository.existsById(user.getId())) {
//            throw new UserNotFoundException(user.getId());
//        }
//        return userRepository.save(user);
//    }
//
//    @Override
//    public void deleteUser(User user) {
//        userRepository.delete(user);
//    }
//
//    @Override
//    public User getUser(UUID id) throws UserNotFoundException {
//        Optional<User> retrievedUser = userRepository.findById(id);
//        return retrievedUser.orElseThrow(() -> new UserNotFoundException(id));
//    }
//
//    @Override
//    public Iterable<User> getAllUsers() {
//        return userRepository.findAll();
//    }
//}
