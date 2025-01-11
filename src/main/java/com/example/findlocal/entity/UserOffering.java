package com.example.findlocal.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
public class UserOffering {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String offer;

    @ManyToOne
    @JoinColumn(name = "profile_id")
    @JsonIgnore
    UserProfile profile;

    public UserProfile getUserProfile() {
        return profile;
    }

    public void setUserProfile(UserProfile userProfile) {
        this.profile = userProfile;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOffer() {
        return offer;
    }

    public void setOffer(String offer) {
        this.offer = offer;
    }
}
