package com.example.findlocal.services;

import com.example.findlocal.entity.User;
import com.example.findlocal.exception.UserNotFoundException;
import com.example.findlocal.exception.UserValidationException;
import com.example.findlocal.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    public void testCreateUser() {
        User user = new User();
        user.setUsername("Matt");
        user.setEmail("test@gmail.com");
        User createdUser = new User();
        createdUser.setUsername("Matt");
        createdUser.setEmail("test@gmail.com");

        when(userRepository.save(user)).thenReturn(createdUser);
        User result = userService.createUser(user);

        verify(userRepository, times(1)).save(user);
        assertEquals("Matt", result.getUsername());
        assertEquals("test@gmail.com", result.getEmail());
    }

    @Test
    public void testCreateUserThrowsValidationExceptionWhenMissingRequiredAttributes() {
        assertThrows(UserValidationException.class, () -> userService.createUser(null));
    }

    @Test
    public void testGetUserByIdThrowsExceptionWhenNotFound() {
        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.getUser(userId));
    }

    @Test
    public void testUpdateUserThrowsExceptionWhenUserIsNotFound() {
        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.updateUser(userId, "Name", "email"));
    }

    @Test
    public void testGetUserByUsernameThrowsNotFoundExceptionWhenUserDoesNotExist() {
        String username = "Matt";

        when(userRepository.findByUsername(username)).thenReturn(null);

        assertThrows(UserNotFoundException.class, () -> userService.getUserByUsername(username));
    }

    @Test
    public void testGetUserByUsernameReturnsUser() {
        String username = "Matt";
        User user = new User();
        user.setUsername("Matt");
        user.setEmail("mail@gmail.com");

        when(userRepository.findByUsername(username)).thenReturn(user);

        User result = userService.getUserByUsername(username);

        assertEquals("Matt", result.getUsername());
        assertEquals("mail@gmail.com", result.getEmail());
    }

    //todo: need to understand required fields for updating a user
//    @Test
//    public void testUpdateUserSuccessfully() {
//        User user = new User();
//        user.setUsername("Matt");
//        user.setEmail("mail@gmail.com");
//
//        User createdUser = new User();
//        createdUser.setUsername("Matt");
//        createdUser.setEmail("mail@gmail.com");
//
//        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
//        when(userRepository.save(user)).thenReturn(createdUser);
//
//        User result = userService.updateUser(1L, "Matt", "mail@gmail.com");
//
//        assertEquals("Matt", result.getUsername());
//        assertEquals("mail@gmail.com", result.getEmail());
//    }

}
