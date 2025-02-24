package com.example.findlocal.services;

import com.example.findlocal.entity.UserProfile;
import com.example.findlocal.entity.UserProject;
import com.example.findlocal.exception.UserProfileNotFoundException;
import com.example.findlocal.exception.UserProjectNotFoundException;
import com.example.findlocal.repository.UserProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserProjectService {
    private UserProjectRepository userProjectRepository;
    private UserProfileService userProfileService;

    @Autowired
    public UserProjectService(UserProjectRepository userProjectRepository, UserProfileService userProfileService) {
        this.userProfileService =userProfileService;
        this.userProjectRepository = userProjectRepository;
    }

    public List<UserProject> getUserProjectsByProfileId(Long profileId) {
        return userProjectRepository.findAllByProfileId(profileId);
    }

    public UserProject addUserProject(Long profileId, UserProject userProject) {
        UserProfile userProfile = userProfileService.getUserProfileById(profileId);
        userProject.setProfile(userProfile);
        return userProjectRepository.save(userProject);
    }

    public UserProject getUserProjectById(Long projectId) {
        return userProjectRepository.findById(projectId)
                .orElseThrow(() -> new UserProjectNotFoundException(String.format("UserProject not found with projectId %d", projectId)));
    }

    public UserProject updateUserProject(Long projectId, UserProject userProject) {
        UserProject updateProject = getUserProjectById(projectId);

        updateProject.setDescription(userProject.getDescription());
        updateProject.setTitle(userProject.getTitle());

        return userProjectRepository.save(updateProject);
    }
}
