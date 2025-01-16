package com.example.findlocal.services;

import com.example.findlocal.entity.ProfileImage;
import com.example.findlocal.entity.User;
import com.example.findlocal.entity.UserProfile;
import com.example.findlocal.exception.UserProfileNotFoundException;
import com.example.findlocal.repository.UserProfileRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserProfileServiceTest {
    @Mock
    UserProfileRepository userProfileRepository;

    @Mock
    UserService userService;

    @Mock
    ProfileImageService profileImageService;

    @InjectMocks
    UserProfileService userProfileService;

    @Test
    public void testCreateUserProfileSuccessfullyCreatesProfileImagesAndProfile() {
        ArgumentCaptor<ProfileImage> profileImageCaptor = ArgumentCaptor.forClass(ProfileImage.class);
        ArgumentCaptor<UserProfile>  userProfileCaptor = ArgumentCaptor.forClass(UserProfile.class);
        UserProfile userProfile = new UserProfile();
        UserProfile createdProfile = new UserProfile();
        User user = new User();

        when(userService.getUser(1L)).thenReturn(user);
        when(userProfileRepository.save(userProfile)).thenReturn(createdProfile);
        userProfileService.createUserProfile(1L, userProfile);

        verify(profileImageService, times(4))
                .addProfileImage(userProfileCaptor.capture(), profileImageCaptor.capture());
        List<ProfileImage> profileImages = profileImageCaptor.getAllValues();
        List<UserProfile> profiles = userProfileCaptor.getAllValues();
        assertEquals(4, profileImages.size());
        assertEquals(4, profiles.size());
        assertEquals("photo1", profileImages.get(0).getName());
        assertEquals("photo2", profileImages.get(1).getName());
        assertEquals("photo3", profileImages.get(2).getName());
        assertEquals("photo4", profileImages.get(3).getName());
        for (UserProfile profile : profiles) {
            assertEquals(createdProfile, profile);
        }
    }

    @Test
    public void testGetUserProfileByUserIdThrowsProfileNotFoundExceptionWhenNoResultIsReturned() {
        Long userId = 1L;
        UserProfile userProfile = new UserProfile();
        when(userProfileRepository.findByUserId(userId)).thenReturn(null);

        assertThrows(UserProfileNotFoundException.class, () -> userProfileService.getUserProfileByUserId(userId));
    }

    @Test
    public void testUpdateUserProfile() {
        Long userId = 1L;
        UserProfile originalProfile = new UserProfile();
        UserProfile updatedProfile = new UserProfile();
        updatedProfile.setDescription("Description");
        updatedProfile.setTitle("title");
        when(userProfileRepository.findByUserId(userId)).thenReturn(originalProfile);
        when(userProfileRepository.save(originalProfile)).thenReturn(updatedProfile);

        UserProfile result = userProfileService.updateUserProfile(userId, "Description", "title");
        assertEquals(updatedProfile.getDescription(), result.getDescription());
        assertEquals(updatedProfile.getTitle(), result.getTitle());
    }

    @Test
    public void testUserProfileByIdThrowsNotFoundWhenProfileDoesNotExist() {
        Long profileId = 1L;
        when(userProfileRepository.findById(profileId)).thenReturn(Optional.empty());
        assertThrows(UserProfileNotFoundException.class, () -> userProfileService.getUserProfileById(profileId));
    }
}
