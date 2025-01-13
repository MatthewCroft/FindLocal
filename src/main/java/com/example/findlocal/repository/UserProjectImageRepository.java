package com.example.findlocal.repository;

import com.example.findlocal.entity.UserProjectImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserProjectImageRepository extends JpaRepository<UserProjectImage, Long> {
    List<UserProjectImage> findAllByUserProjectId(Long userProjectId);
    UserProjectImage findByFeaturedImageId(Long featuredImageId);
}
