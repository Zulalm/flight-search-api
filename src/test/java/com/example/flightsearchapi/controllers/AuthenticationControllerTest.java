package com.example.flightsearchapi.controllers;

import com.example.flightsearchapi.config.JwtAuthFilter;
import com.example.flightsearchapi.dtos.authenticationRequestDtos.AuthenticateRequestDto;
import com.example.flightsearchapi.dtos.authenticationRequestDtos.RegisterUserRequestDto;
import com.example.flightsearchapi.dtos.authenticationResponseDto.AuthenticationTokenResponseDto;
import com.example.flightsearchapi.models.User;
import com.example.flightsearchapi.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class AuthenticationControllerTest {

    @Mock
    private UserService userService;
    @Mock
    private JwtAuthFilter jwtAuthFilter;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @InjectMocks
    private AuthenticationController authenticationController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAuthenticateSuccess() {
        AuthenticateRequestDto requestDto = new AuthenticateRequestDto();
        requestDto.setUsername("testUser");
        requestDto.setPassword("testPassword");

        UserDetails userDetails = mock(UserDetails.class);
        when(userService.loadUserByUsername("testUser")).thenReturn(userDetails);

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(null);

        when(jwtAuthFilter.createToken(userDetails)).thenReturn("testToken");

        ResponseEntity<Object> responseEntity = authenticationController.authenticate(requestDto);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(AuthenticationTokenResponseDto.class, responseEntity.getBody().getClass());
    }
    @Test
    void testRegisterUserSuccess() {
        RegisterUserRequestDto requestDto = new RegisterUserRequestDto();
        requestDto.setUsername("testUser");
        requestDto.setPassword("testPassword");
        requestDto.setEmail("test@example.com");

        when(userService.checkUsernameIsAvailable("testUser")).thenReturn(true);
        when(userService.checkEmailIsAvailable("test@example.com")).thenReturn(true);

        when(bCryptPasswordEncoder.encode("testPassword")).thenReturn("encodedPassword");

        User registeredUser = new User();
        registeredUser.setUsername("testUser");
        registeredUser.setEmail("test@example.com");

        when(userService.registerUser("testUser", "encodedPassword", "test@example.com")).thenReturn(registeredUser);

        when(jwtAuthFilter.createToken(registeredUser)).thenReturn("testToken");

        ResponseEntity<Object> responseEntity = authenticationController.registerUser(requestDto);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(AuthenticationTokenResponseDto.class, responseEntity.getBody().getClass());
    }


}