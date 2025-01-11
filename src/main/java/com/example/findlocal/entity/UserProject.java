package com.example.findlocal.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.Set;

@Entity
public class UserProject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    private String description;

    @ManyToOne
    @JoinColumn(name = "profile_id")
    @JsonIgnore
    private UserProfile profile;

    @OneToMany(mappedBy = "userProject")
    @JsonIgnore
    private Set<UserProjectImage> projectImages;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public UserProfile getProfile() {
        return profile;
    }

    public void setProfile(UserProfile profile) {
        this.profile = profile;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<UserProjectImage> getProjectImages() {
        return projectImages;
    }

    public void setProjectImages(Set<UserProjectImage> projectImages) {
        this.projectImages = projectImages;
    }
}
