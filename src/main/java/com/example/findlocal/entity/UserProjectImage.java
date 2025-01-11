package com.example.findlocal.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
public class UserProjectImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "LONGTEXT")
    private String data;

    @ManyToOne
    @JoinColumn(name = "user_project_id")
    @JsonIgnore
    UserProject userProject;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public UserProject getUserProject() {
        return userProject;
    }

    public void setUserProject(UserProject userProject) {
        this.userProject = userProject;
    }
}
