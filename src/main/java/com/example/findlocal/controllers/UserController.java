package com.example.findlocal.controllers;

import com.example.findlocal.entity.User;
import com.example.findlocal.services.UserService;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        if (id == null)
            return ResponseEntity
                .badRequest()
                .build();

        User user = userService.getUser(id);

        return ResponseEntity
                .ok(user);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> getUserByUsername(@RequestParam String userName) {
        if (userName == null) {
            return ResponseEntity
                    .badRequest()
                    .build();
        }

        User user = userService.getUserByUsername(userName);

        return ResponseEntity
                .ok(user);
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        if (user.getEmail() == null || user.getUsername() == null) {
            return ResponseEntity
                    .badRequest()
                    .build();
        }

        User createdUser = userService.createUser(user);

        return ResponseEntity.ok(
                createdUser
        );
    }
}
