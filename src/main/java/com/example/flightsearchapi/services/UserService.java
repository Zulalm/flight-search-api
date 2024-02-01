package com.example.flightsearchapi.services;


import com.example.flightsearchapi.models.User;
import com.example.flightsearchapi.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    public boolean checkUsernameIsAvailable(String username){
        User user = userRepository.findByUsername(username);
        return user != null;
    }
    public boolean checkEmailIsAvailable(String email){
        User user = userRepository.findByEmail(email);
        return user != null;
    }


    public User registerUser(String username, String password, String email){
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setRole("USER");
        user.setEmail(email);
        userRepository.save(user);
        return user;
    }
}
