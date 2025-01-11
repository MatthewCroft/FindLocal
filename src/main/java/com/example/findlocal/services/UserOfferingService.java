package com.example.findlocal.services;

import com.example.findlocal.entity.UserOffering;
import com.example.findlocal.entity.UserProfile;
import com.example.findlocal.repository.UserOfferingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserOfferingService {
   private UserProfileService userProfileService;
   private UserOfferingRepository userOfferingRepository;

   @Autowired
   public UserOfferingService(UserProfileService userProfileService, UserOfferingRepository userOfferingRepository) {
      this.userProfileService = userProfileService;
      this.userOfferingRepository = userOfferingRepository;
   }

   public UserOffering createOffering(UserOffering offering, Long profileId) {
      UserProfile userProfile = userProfileService.getUserProfileById(profileId);

      if (userProfile == null || userProfile.getId() == null) {
         throw new RuntimeException("user profile does not exist to add offering");
      }

      offering.setUserProfile(userProfile);
      return userOfferingRepository.save(offering);
   }

   public List<UserOffering> getOfferingsByProfileId(Long profileId) {
      return userOfferingRepository.findAllByProfileId(profileId);
   }

}
