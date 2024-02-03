package com.example.flightsearchapi.controllers;


import com.example.flightsearchapi.config.JwtAuthFilter;
import com.example.flightsearchapi.dtos.authenticationRequestDtos.AuthenticateRequestDto;
import com.example.flightsearchapi.dtos.authenticationRequestDtos.RegisterUserRequestDto;
import com.example.flightsearchapi.dtos.authenticationResponseDto.AuthenticationTokenResponseDto;
import com.example.flightsearchapi.models.User;
import com.example.flightsearchapi.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;
    @Autowired
    private JwtAuthFilter jwtAuthFilter;
    @Operation(summary = "Authenticate user")
    @PostMapping("/authenticate")
    @ApiResponse(responseCode = "200", description = "Authentication successful",
            content = @Content(mediaType = "application/json"))
    @ApiResponse(responseCode = "400", description = "A username and a password should be provided.",
            content = @Content(mediaType = "text/plain"))
    @ApiResponse(responseCode = "403", description = "Invalid credentials",
            content = @Content(mediaType = "text/plain"))
    @ApiResponse(responseCode = "500", description = "Internal Server Error",
            content = @Content(mediaType = "text/plain"))
    public ResponseEntity<Object> authenticate(@RequestBody AuthenticateRequestDto requestDto){
        try{
            if(requestDto.getUsername() == null){
                return new ResponseEntity<>("A username should be provided.", HttpStatus.BAD_REQUEST);
            }
            if(requestDto.getPassword() == null){
                return new ResponseEntity<>("A password should be provided.", HttpStatus.BAD_REQUEST);
            }
            try{
                UserDetails user = userService.loadUserByUsername(requestDto.getUsername());
                if(user != null){
                    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(requestDto.getUsername(), requestDto.getPassword()));
                    AuthenticationTokenResponseDto responseDto = new AuthenticationTokenResponseDto(jwtAuthFilter.createToken(user));
                    return new ResponseEntity<>(responseDto,HttpStatus.OK);
                }
                return new ResponseEntity<>("Invalid credentials.", HttpStatus.FORBIDDEN);
            }catch(Exception e){
                return new ResponseEntity<>("Invalid credentials.", HttpStatus.FORBIDDEN);
            }

        }catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    @Operation(summary = "Register a new user")
    @PostMapping("/register")
    @ApiResponse(responseCode = "200", description = "Registration successful",
            content = @Content(mediaType = "application/json"))
    @ApiResponse(responseCode = "409", description = "Username or email is already in use.",
            content = @Content(mediaType = "text/plain"))
    @ApiResponse(responseCode = "500", description = "Internal Server Error",
            content = @Content(mediaType = "text/plain"))
    public ResponseEntity<Object> registerUser(@RequestBody RegisterUserRequestDto requestDto){
        if(!userService.checkUsernameIsAvailable(requestDto.getUsername())){
            return new ResponseEntity<>("The username is already in use. Please choose a different username.", HttpStatus.CONFLICT);
        }
        if(!userService.checkEmailIsAvailable(requestDto.getEmail())){
            return new ResponseEntity<>("The email is already in use. Please choose a different email.", HttpStatus.CONFLICT);
        }
        BCryptPasswordEncoder bCryptPasswordEncoder=new BCryptPasswordEncoder();
        String password = bCryptPasswordEncoder.encode(requestDto.getPassword());
        User user = userService.registerUser(requestDto.getUsername(), password, requestDto.getEmail());
        AuthenticationTokenResponseDto responseDto = new AuthenticationTokenResponseDto(jwtAuthFilter.createToken(user));
        return new ResponseEntity<>(responseDto,HttpStatus.OK);
    }

}

