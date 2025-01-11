package com.example.findlocal.services;

import com.example.findlocal.entity.User;
import com.example.findlocal.repository.UserProfileRepository;
import com.example.findlocal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
     private final UserRepository userRepository;
     private final UserProfileRepository userProfileRepository;

    @Autowired
    public UserService(UserRepository userRepository, UserProfileRepository userProfileRepository) {
         this.userRepository = userRepository;
         this.userProfileRepository = userProfileRepository;
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
                .orElseThrow(() -> new RuntimeException("User not found"));
     }

    public User getUserByUsername(String userName) {
        return userRepository.findByUsername(userName);
    }
}
