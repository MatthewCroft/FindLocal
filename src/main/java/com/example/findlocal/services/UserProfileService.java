package com.example.findlocal.services;

import com.example.findlocal.entity.ProfileImage;
import com.example.findlocal.entity.User;
import com.example.findlocal.entity.UserProfile;
import com.example.findlocal.exception.UserProfileNotFoundException;
import com.example.findlocal.repository.UserProfileRepository;
import com.example.findlocal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserProfileService {
    private UserProfileRepository userProfileRepository;
    private UserService userService;
    private ProfileImageService profileImageService;

    @Autowired
    public UserProfileService(UserService userService, UserProfileRepository userProfileRepository, ProfileImageService profileImageService) {
        this.userService = userService;
        this.userProfileRepository = userProfileRepository;
        this.profileImageService = profileImageService;
    }

    public UserProfile createUserProfile(Long userId, UserProfile userProfile)  {
        User user = userService.getUser(userId);
        userProfile.setUser(user);

        UserProfile createdProfile = userProfileRepository.save(userProfile);

        profileImageService.addProfileImage(createdProfile, new ProfileImage("photo1", "img1", "uploadImage.png"));
        profileImageService.addProfileImage(createdProfile, new ProfileImage("photo2", "img2", "uploadImage.png"));
        profileImageService.addProfileImage(createdProfile, new ProfileImage("photo3", "img3", "uploadImage.png"));
        profileImageService.addProfileImage(createdProfile, new ProfileImage("photo4", "img4", "uploadImage.png"));

        return createdProfile;
    }

    public UserProfile getUserProfileById(Long userProfileId) {
        return userProfileRepository.findById(userProfileId)
                .orElseThrow(() -> new UserProfileNotFoundException(String.format("User profile not found getUserProfileById %d", userProfileId)));
    }

    public UserProfile getUserProfileByUserId(Long userId) {
        UserProfile userProfile = userProfileRepository.findByUserId(userId);

        if (userProfile == null) {
            throw new UserProfileNotFoundException(String.format("User profile not found getUserProfileByUserId with userId %d", userId));
        }

        return userProfile;
    }

    public UserProfile updateUserProfile(Long userId, String description, String title) {
        UserProfile userProfile = getUserProfileByUserId(userId);

        if (userProfile == null) {
            throw new UserProfileNotFoundException(String.format("User profile not found updateUserProfile with userId %d", userId));
        }

        userProfile.setTitle(title);
        userProfile.setDescription(description);
        return userProfileRepository.save(userProfile);
    }
}
