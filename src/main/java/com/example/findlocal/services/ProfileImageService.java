package com.example.findlocal.services;

import com.example.findlocal.entity.ProfileImage;
import com.example.findlocal.entity.UserProfile;
import com.example.findlocal.repository.ProfileImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfileImageService {
    private ProfileImageRepository profileImageRepository;

    @Autowired
    public ProfileImageService(ProfileImageRepository profileImageRepository) {
        this.profileImageRepository = profileImageRepository;
    }

    public ProfileImage addProfileImage(UserProfile userProfile, ProfileImage profileImage) {
        profileImage.setProfile(userProfile);
        return profileImageRepository.save(profileImage);
    }

    public List<ProfileImage> getProfileImages(Long userProfileId) {
        return profileImageRepository.findAllByProfileId(userProfileId);
    }

    public ProfileImage updateImage(Long imageId, ProfileImage profileImage) {
        ProfileImage updatedImage = profileImageRepository.findById(imageId)
                .orElseThrow(() -> new RuntimeException(String.format("image not found that needed updated, id %d", imageId)));

        updatedImage.setData(profileImage.getData());
        updatedImage.setElementId(profileImage.getElementId());
        updatedImage.setName(profileImage.getName());

        return profileImageRepository.save(updatedImage);
    }
}
