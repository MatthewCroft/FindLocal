package com.example.findlocal.controllers;

import com.example.findlocal.entity.UserProjectImage;
import com.example.findlocal.services.UserProjectImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Base64;
import java.util.List;

@RestController
@RequestMapping("/api/user/{userId}/profile/{profileId}/project/{projectId}/image")
public class UserProjectImageController {

    private UserProjectImageService userProjectImageService;

    @Autowired
    public UserProjectImageController(UserProjectImageService userProjectImageService) {
        this.userProjectImageService = userProjectImageService;
    }

    @GetMapping
    public ResponseEntity<List<UserProjectImage>> getUserProjectImages(Long projectId) {
        if (projectId == null) {
            return ResponseEntity
                    .badRequest()
                    .build();
        }

        List<UserProjectImage> userProjectImages = userProjectImageService.getUserProjectImagesByUserProjectId(projectId);

        return ResponseEntity
                .ok(userProjectImages);
    }

    @PostMapping
    public ResponseEntity<UserProjectImage> addUserProjectImage(@PathVariable Long projectId, @RequestPart("file") MultipartFile file) {
        if (projectId == null) {
            return ResponseEntity
                    .badRequest()
                    .build();
        }

        UserProjectImage userProjectImage = new UserProjectImage();

        try {
            userProjectImage.setData(Base64.getEncoder().encodeToString(file.getBytes()));
        } catch (Exception e) {
            return ResponseEntity
                    .badRequest()
                    .build();
        }

        UserProjectImage createdUserProjectImage = userProjectImageService.addUserProjectImage(projectId, userProjectImage);

        return ResponseEntity
                .ok(createdUserProjectImage);
    }

    @PostMapping("/featured")
    public ResponseEntity<UserProjectImage> addFeaturedProjectImage(@PathVariable Long projectId, @RequestPart("file") MultipartFile file) {
        if (projectId == null) {
            return ResponseEntity
                    .badRequest()
                    .build();
        }

        UserProjectImage projectImage = new UserProjectImage();

        try {
            projectImage.setData(Base64.getEncoder().encodeToString(file.getBytes()));
        } catch (Exception e) {
            return ResponseEntity
                    .badRequest()
                    .build();
        }

        UserProjectImage createdImage = userProjectImageService.addUserProjectFeaturedImage(projectId, projectImage);

        return ResponseEntity
                .ok(createdImage);
    }

    @DeleteMapping("{imageId}")
    public ResponseEntity<String> deleteUserProjectImage(Long imageId) {
        if (imageId == null) {
            return ResponseEntity
                    .badRequest()
                    .build();
        }

        userProjectImageService.removeUserProjectImage(imageId);

        return ResponseEntity
                .ok("Delete Success");
    }

}
