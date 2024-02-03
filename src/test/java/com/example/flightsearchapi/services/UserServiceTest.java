package com.example.flightsearchapi.services;

import com.example.flightsearchapi.models.User;
import com.example.flightsearchapi.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void checkUsernameIsAvailableReturnTrue() {
        Mockito.when(userRepository.findByUsername(anyString())).thenReturn(null);
        boolean result =  userService.checkUsernameIsAvailable("username");
        assertEquals(true,result);
    }
    @Test
    void checkUsernameIsAvailableReturnFalse() {
        Mockito.when(userRepository.findByUsername(anyString())).thenReturn(new User());
        boolean result =  userService.checkUsernameIsAvailable("username");
        assertEquals(false,result);
    }

    @Test
    void checkEmailIsAvailableReturnTrue() {
        Mockito.when(userRepository.findByEmail(anyString())).thenReturn(null);
        boolean result =  userService.checkEmailIsAvailable("email");
        assertEquals(true,result);
    }
    @Test
    void checkEmailIsAvailableReturnFalse() {
        Mockito.when(userRepository.findByEmail(anyString())).thenReturn(new User());
        boolean result =  userService.checkEmailIsAvailable("email");
        assertEquals(false,result);
    }


    @Test
    void registerUser() {
        String username = "username";
        String password = "password";
        String role = "USER";
        String email = "email";
        User expectedUser = new User(username,password,email,role);

        User user = userService.registerUser(username,password,email);

        assertEquals(expectedUser.getEmail(),user.getEmail());
        assertEquals(expectedUser.getRole(),user.getRole());
        assertEquals(expectedUser.getUsername(),user.getUsername());
        assertEquals(expectedUser.getPassword(),user.getPassword());
    }

    @Test
    void loadUserByUsername() {
        User expectedUser = new User();
        Mockito.when(userRepository.findByUsername(anyString())).thenReturn(expectedUser);

        User user = (User) userService.loadUserByUsername("username");
        assertEquals(expectedUser, user);
    }
}