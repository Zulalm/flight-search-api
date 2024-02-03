package com.example.flightsearchapi.services;


import com.example.flightsearchapi.models.User;
import com.example.flightsearchapi.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService  implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    public boolean checkUsernameIsAvailable(String username){
        User user = userRepository.findByUsername(username);
        return user == null;
    }
    public boolean checkEmailIsAvailable(String email){
        User user = userRepository.findByEmail(email);
        return user == null;
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
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        List<String> roles = new ArrayList<>();
        roles.add("USER");
        return user;
    }
}
