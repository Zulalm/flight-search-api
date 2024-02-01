package com.example.flightsearchapi.controllers;


import com.example.flightsearchapi.config.JwtAuthFilter;
import com.example.flightsearchapi.dtos.AuthenticateRequestDto;
import com.example.flightsearchapi.dtos.RegisterUserRequestDto;
import com.example.flightsearchapi.models.User;
import com.example.flightsearchapi.services.UserDetailsServiceImplementation;
import com.example.flightsearchapi.services.UserService;
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
    private UserDetailsServiceImplementation userDetailsServiceImplementation;
    @Autowired
    private JwtAuthFilter jwtAuthFilter;
    @Autowired
    private UserService userService;
    @PostMapping("/authenticate")
    public ResponseEntity<String> authenticate(@RequestBody AuthenticateRequestDto requestDto){
        try{
            if(requestDto.getUsername() == null){
                return new ResponseEntity<>("A username should be provided.", HttpStatus.BAD_REQUEST);
            }
            if(requestDto.getPassword() == null){
                return new ResponseEntity<>("A password should be provided.", HttpStatus.BAD_REQUEST);
            }
            UserDetails user = userDetailsServiceImplementation.loadUserByUsername(requestDto.getUsername());
            if(user != null){
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(requestDto.getUsername(), requestDto.getPassword()));
                return new ResponseEntity<>(jwtAuthFilter.createToken(user), HttpStatus.OK);
            }
            return new ResponseEntity<>("Invalid credentials.", HttpStatus.FORBIDDEN);
        }catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody RegisterUserRequestDto requestDto){
        if(userService.checkUsernameIsAvailable(requestDto.getUsername())){
            return new ResponseEntity<>("The username is already in use. Please choose a different username.", HttpStatus.CONFLICT);
        }
        if(userService.checkEmailIsAvailable(requestDto.getEmail())){
            return new ResponseEntity<>("The email is already in use. Please choose a different email.", HttpStatus.CONFLICT);
        }
        BCryptPasswordEncoder bCryptPasswordEncoder=new BCryptPasswordEncoder();
        String password = bCryptPasswordEncoder.encode(requestDto.getPassword());
        User user = userService.registerUser(requestDto.getUsername(), password, requestDto.getEmail());
        return new ResponseEntity<>(jwtAuthFilter.createToken(user),HttpStatus.OK);
    }

}

