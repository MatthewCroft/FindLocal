package com.example.findlocal.repository;

import com.example.findlocal.entity.UserOffering;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserOfferingRepository extends JpaRepository<UserOffering, Long> {
    List<UserOffering> findAllByProfileId(Long profileId);
}
