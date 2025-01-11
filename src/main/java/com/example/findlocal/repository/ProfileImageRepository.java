package com.example.findlocal.repository;

import com.example.findlocal.entity.ProfileImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProfileImageRepository extends JpaRepository<ProfileImage, Long> {
   List<ProfileImage> findAllByProfileId(Long userProfileId);
}
