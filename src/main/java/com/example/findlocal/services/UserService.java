package com.example.findlocal.services;

import com.example.findlocal.entity.User;
import com.example.findlocal.exception.UserNotFoundException;
import com.example.findlocal.exception.UserValidationException;
import com.example.findlocal.repository.UserProfileRepository;
import com.example.findlocal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.example.findlocal.utils.ValidationHelper.isNullOrEmpty;

@Service
public class UserService {
     private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
         this.userRepository = userRepository;
     }

     public User createUser(User user) {
        if (user == null || isNullOrEmpty(user.getUsername())
        || isNullOrEmpty(user.getEmail())) {
            throw new UserValidationException("User missing required attributes");
        }

        return userRepository.save(user);
     }

     //todo: udpate this to understand the required fields or what fields to update
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
        User user = userRepository.findByUsername(userName);

        if (user == null) {
            throw new UserNotFoundException(String.format("User not found with username %s", userName));
        }

        return user;
    }
}
