package com.example.findlocal.services;

import com.example.findlocal.entity.User;
import com.example.findlocal.exception.UserNotFoundException;
import com.example.findlocal.repository.UserProfileRepository;
import com.example.findlocal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
     private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
         this.userRepository = userRepository;
     }

     public User createUser(String username, String email) {
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        return userRepository.save(user);
     }

     public User updateUser(Long userId, String username, String email) {
        User user = getUser(userId);
        user.setUsername(username);
        user.setEmail(email);
        return userRepository.save(user);
     }

     public User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(String.format("User not found with userId %d", userId)));
     }

    public User getUserByUsername(String userName) {
        return userRepository.findByUsername(userName);
    }
}
