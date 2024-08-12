package com.example.Apesech.Hotel.Restaurant.service;

import com.example.Apesech.Hotel.Restaurant.exception.OurException;
import com.example.Apesech.Hotel.Restaurant.entity.User;
import com.example.Apesech.Hotel.Restaurant.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username).orElseThrow(() -> new OurException("Username Or Email Not Found"));
    }
}
