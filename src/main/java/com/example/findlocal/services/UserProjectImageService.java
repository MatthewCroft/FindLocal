package com.example.findlocal.services;

import com.example.findlocal.entity.UserProject;
import com.example.findlocal.entity.UserProjectImage;
import com.example.findlocal.repository.UserProjectImageRepository;
import com.example.findlocal.repository.UserProjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserProjectImageService {

    private UserProjectService userProjectService;
    private UserProjectImageRepository userProjectImageRepository;

    public UserProjectImageService(UserProjectService userProjectService, UserProjectImageRepository userProjectImageRepository) {
        this.userProjectService = userProjectService;
        this.userProjectImageRepository = userProjectImageRepository;
    }

    public List<UserProjectImage> getUserProjectImagesByUserProjectId(Long userProjectId) {
        return userProjectImageRepository.findAllByUserProjectId(userProjectId);
    }

    public UserProjectImage addUserProjectImage(Long projectId, UserProjectImage userProjectImage) {
        UserProject userProject =  userProjectService.getUserProjectById(projectId);
        userProjectImage.setUserProject(userProject);
        return userProjectImageRepository.save(userProjectImage);
    }

    public void removeUserProjectImage(Long imageId) {
        UserProjectImage userProjectImage = userProjectImageRepository.findById(imageId)
                .orElseThrow(() -> new RuntimeException("UserProjectImage find error"));

        userProjectImageRepository.delete(userProjectImage);
    }
}
