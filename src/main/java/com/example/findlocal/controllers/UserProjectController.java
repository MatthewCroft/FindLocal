package com.example.findlocal.controllers;

import com.example.findlocal.entity.UserProject;
import com.example.findlocal.services.UserProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user/{userId}/profile/{profileId}/project")
public class UserProjectController {

    private UserProjectService userProjectService;

    @Autowired
    public UserProjectController(UserProjectService userProjectService) {
        this.userProjectService = userProjectService;
    }

    @GetMapping
    public ResponseEntity<List<UserProject>> getUserProjectsByProfileId(@PathVariable Long profileId) {
        if (profileId == null) {
            return ResponseEntity
                    .badRequest()
                    .build();
        }

        List<UserProject> userProjects = userProjectService.getUserProjectsByProfileId(profileId);

        return ResponseEntity
                .ok(userProjects);
    }

    @PostMapping
    public ResponseEntity<UserProject> createUserProject(@PathVariable Long profileId, @RequestBody UserProject userProject) {
        if (profileId == null || userProject.getTitle() == null) {
            return ResponseEntity
                    .badRequest()
                    .build();
        }

        UserProject createdProject = userProjectService.addUserProject(profileId, userProject);

        return ResponseEntity
                .ok(createdProject);
    }

    @PutMapping("{projectId}")
    public ResponseEntity<UserProject> updateUserProject(@PathVariable Long projectId, @RequestBody UserProject userProject) {
        if (projectId == null) {
            return ResponseEntity
                    .badRequest()
                    .build();
        }

        UserProject udpatedProject = userProjectService.updateUserProject(projectId, userProject);

        return ResponseEntity
                .ok(udpatedProject);
    }

}
