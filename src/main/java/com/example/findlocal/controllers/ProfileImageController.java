package com.example.findlocal.controllers;

import com.example.findlocal.entity.ProfileImage;
import com.example.findlocal.services.ProfileImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Base64;
import java.util.List;

@RestController
@RequestMapping("/api/user/{userId}/profile/{profileId}/image")
public class ProfileImageController {
    private ProfileImageService profileImageService;

    @Autowired
    public ProfileImageController(ProfileImageService profileImageService) {
        this.profileImageService = profileImageService;
    }

    @PostMapping
    public ResponseEntity<ProfileImage> addProfileImage(@RequestPart("file") MultipartFile file, @PathVariable Long profileId, @RequestPart("profileImage") ProfileImage profileImage) {
        if (profileImage.getName() == null) {
            return ResponseEntity
                    .badRequest()
                    .build();
        }

        try {
            profileImage.setData(Base64.getEncoder().encodeToString(file.getBytes()));
        } catch (Exception e) {
            return ResponseEntity
                    .badRequest()
                    .build();
        }

        ProfileImage imageAdded =null;

        return ResponseEntity
                .ok(imageAdded);
    }

    @PutMapping("/{imageId}")
    public ResponseEntity<ProfileImage> updateImage(@PathVariable Long imageId,
                                                    @RequestPart("profileImage") ProfileImage profileImage, @RequestPart("file") MultipartFile file) {
        if (imageId == null) {
            return ResponseEntity
                    .badRequest()
                    .build();
        }

        try {
           profileImage.setData(Base64.getEncoder().encodeToString(file.getBytes()));
        } catch(Exception e) {
            return ResponseEntity
                    .badRequest()
                    .build();
        }

        ProfileImage updatedImage = profileImageService.updateImage(imageId, profileImage);

        return ResponseEntity
                .ok(updatedImage);
    }

    @GetMapping
    public ResponseEntity<List<ProfileImage>> getProfileImages(@PathVariable Long profileId) {
        if (profileId == null) {
            return ResponseEntity
                    .badRequest()
                    .build();
        }

        List<ProfileImage> profileImages = profileImageService.getProfileImages(profileId);

        return ResponseEntity
                .ok(profileImages);
    }
}
