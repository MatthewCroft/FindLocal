package com.example.findlocal.controllers;

import com.example.findlocal.entity.UserProfile;
import com.example.findlocal.services.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user/{userId}/profile")
public class UserProfileController {
    private UserProfileService userProfileService;

    @Autowired
    public UserProfileController(UserProfileService userProfileService) {
        this.userProfileService = userProfileService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserProfile> getUserProfile(@PathVariable Long userId) {
        if (userId == null) {
            return ResponseEntity
                    .badRequest()
                    .build();
        }

        UserProfile userProfile = userProfileService.getUserProfileByUserId(userId);

        if (userProfile == null) {
            return ResponseEntity
                    .ok(new UserProfile());
        }

        return ResponseEntity
                .ok(userProfile);
    }

    @PostMapping
    public ResponseEntity<UserProfile> createUserProfile(@RequestBody UserProfile userProfile, @PathVariable Long userId) {
        if (userProfile.getTitle() == null || userProfile.getDescription() == null) {
            return ResponseEntity
                    .badRequest()
                    .build();
        }

        UserProfile createdProfile = userProfileService.createUserProfile(userId, userProfile);

        return ResponseEntity
                .ok(createdProfile);
    }
}
